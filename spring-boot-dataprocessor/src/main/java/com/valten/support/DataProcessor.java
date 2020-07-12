package com.valten.support;

import com.google.common.collect.HashBiMap;
import com.valten.model.RailWayDocument;
import com.valten.service.RailWayService;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * camel-ftp数据处理器
 *
 * @author huangyuanli
 * @className DataProcessor
 * @package com.valten.support
 * @date 2020/7/11 23:09
 **/
@Component
public class DataProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);
    private static HashBiMap<String, String> cancelHashBiMap = HashBiMap.create();
    private static HashBiMap<String, String> resignHashBiMap = HashBiMap.create();
    private static HashBiMap<String, String> returnHashBiMap = HashBiMap.create();

    @Value("${ftp.local-dir}")
    private String fileDir;

    @Autowired
    private RailWayService railWayService;

    @Override
    public void process(Exchange exchange) throws Exception {
        // 处理开始时间
        long startTime = System.currentTimeMillis();
        Message message = exchange.getIn();
        GenericFile<?> genericFile = (GenericFile<?>) message.getBody();
        String fileName = genericFile.getFileName();
        String filePath = fileDir + '/' + fileName;
        readZip(filePath);

        // 清除下载到本地的文件
        deleteFile(filePath);
        // 处理结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));
    }

    /**
     * 批量删除文件
     *
     * @author huangyuanli
     * @date 2020/7/11 23:10
     */
    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }


    /**
     * 读取zip file中的文件内容
     *
     * @author huangyuanli
     * @date 2020/7/11 23:10
     */
    private void readZip(String filePath) throws Exception {
        ZipFile zipFile = new ZipFile(filePath);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        // 获取数据
        ZipEntry ze;
        // (ze=zipFile.getNextEntry())!=null 这样判断会导致ze.getSzie=-1
        while (entries.hasMoreElements()) {
            ze = entries.nextElement();
            if (ze.isDirectory()) {
                // 如果需要递归处理子目录，在此处实现
                File f = new File(fileDir + ze.getName());
                f.mkdir();
                continue;
            }
            logger.info("DetectorDataProcessor.readZip: Start reading {} in {}", ze.getName(), filePath);
            if (ze.getSize() > 0) {
                // 解压后文件名字
                String fileName = ze.getName();
                // 数据合并处理
                handlerData(filePath, fileName);
            }
        }
        zipInputStream.closeEntry();
        inputStream.close();
        zipFile.close();
    }

    /**
     * 数据合并处理
     *
     * @author huangyuanli
     * @date 2020/7/12 21:40
     */
    private void handlerData(String filePath, String fileName) {
        List<String> userInfos = new ArrayList<>();
        List<String> saleInfos = new ArrayList<>();
        if (fileName.indexOf("cancel") > 0 || fileName.indexOf("resign") > 0 || fileName.indexOf("return") > 0) {
            getRelationInfos(filePath, fileName);
        } else if (fileName.indexOf("info_appendix") > 0) {
            userInfos = getUserInfos(filePath, fileName);
        } else if (fileName.indexOf("sale") > 0) {
            saleInfos = getSaleInfos(filePath, fileName);
        }

        List<String> finalUserInfos = userInfos;
        List<RailWayDocument> railWayDocuments = saleInfos.parallelStream().collect(ArrayList::new, (list, item) -> {
            RailWayDocument railWayDocument = new RailWayDocument();
            String[] saleItems = item.split(";");
            // 乘车日期
            String trainDate = saleItems[0];
            // 车次
            String boardTrainCode = saleItems[1];
            // 发站
            String fromStationName = saleItems[2];
            // 到站
            String toStationName = saleItems[3];
            // 车厢
            String coachNo = saleItems[4];
            // 座位号
            String seatNo = saleItems[5];
            // 主键
            String key = saleItems[6];

            List<String> users = finalUserInfos.parallelStream().filter(p -> p.indexOf(key) > 0).collect(Collectors.toList());
            if (!users.isEmpty()) {
                String userInfoStr = users.get(0);
                String[] userItems = userInfoStr.split(";");
                railWayDocument.setIdKind(userItems[0]);
                railWayDocument.setIdNo(userItems[1]);
                railWayDocument.setName(userItems[2]);
            }

            boolean cancelFlag = cancelHashBiMap.containsKey(key);
            if (cancelFlag) {
                railWayDocument.setIsCancel("1");
            } else {
                railWayDocument.setIsCancel("0");
            }

            boolean resignFlag = resignHashBiMap.containsKey(key);
            if (resignFlag) {
                railWayDocument.setIsResign("1");
            } else {
                railWayDocument.setIsResign("0");
            }

            boolean returnFlag = returnHashBiMap.containsKey(key);
            if (returnFlag) {
                railWayDocument.setIsReturn("1");
            } else {
                railWayDocument.setIsReturn("0");
            }

            // 证件号码
            String idNo = railWayDocument.getIdNo();
            railWayDocument.setId(idNo + boardTrainCode + trainDate);
            railWayDocument.setTrainDate(trainDate);
            railWayDocument.setBoardTrainCode(boardTrainCode);
            railWayDocument.setFromStationName(fromStationName);
            railWayDocument.setToStationName(toStationName);
            railWayDocument.setCoachNo(coachNo);
            railWayDocument.setSeatNo(seatNo);

            list.add(railWayDocument);
        }, List::addAll);

        railWayService.saveAll(railWayDocuments);
    }

    /**
     * 获取人员身份信息列表
     *
     * @return 人员身份信息列表
     * @author huangyuanli
     * @date 2020/7/12 13:31
     */
    public static List<String> getUserInfos(String filePath, String fileName) {
        List<String> userInfos = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath + File.separator + fileName));
             BufferedInputStream bis = new BufferedInputStream(fis);
             InputStreamReader isr = new InputStreamReader(bis);
             BufferedReader br = new BufferedReader(isr, 100 * 1024 * 1024)) {

            String line;
            while ((line = br.readLine()) != null) {
                userInfos.add(line);
            }
            userInfos.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfos;
    }

    /**
     * 获取购票和取票信息列表
     *
     * @return 购票和取票信息
     * @author huangyuanli
     * @date 2020/7/12 13:31
     */
    public static List<String> getSaleInfos(String filePath, String fileName) {
        List<String> saleInfos = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath + File.separator + fileName));
             BufferedInputStream bis = new BufferedInputStream(fis);
             InputStreamReader isr = new InputStreamReader(bis);
             BufferedReader br = new BufferedReader(isr, 100 * 1024 * 1024)) {

            String line;
            while ((line = br.readLine()) != null) {
                saleInfos.add(line);
            }
            saleInfos.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saleInfos;
    }

    /**
     * 获取取消、改签、退票信息列表
     *
     * @author huangyuanli
     * @date 2020/7/12 13:31
     */
    public static void getRelationInfos(String filePath, String fileName) {
        try (FileInputStream fis = new FileInputStream(new File(filePath + File.separator + fileName));
             BufferedInputStream bis = new BufferedInputStream(fis);
             InputStreamReader isr = new InputStreamReader(bis);
             BufferedReader br = new BufferedReader(isr, 100 * 1024 * 1024)) {

            String line;
            while ((line = br.readLine()) != null) {
                returnHashBiMap.put(line, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}