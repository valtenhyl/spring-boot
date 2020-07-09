package com.valten.controller;

import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PageOfficeController {

    @Value("${pageoffice.posyspath}")
    private String poSysPath;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView showIndex() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "hello world";
    }

    @RequestMapping(value = "/word", method = RequestMethod.GET)
    public ModelAndView showWord(HttpServletRequest request) {

        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
        // 设置服务页面
        poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
        // 添加自定义保存按钮
        poCtrl.addCustomToolButton("保存", "Save", 1);
        // 设置处理文件保存的请求方法
        poCtrl.setSaveFilePage("/save");
        WordDocument doc = new WordDocument();
        //创建数据区域，createDataRegion 方法中的三个参数分别代表“新建的数据区域名称”，“数据区域将要插入的位置”，
        //“与新建的数据区域相关联的数据区域名称”，若当前Word文档中尚无数据区域（书签）或者想在文档的最开头创建时，那么第三个参数为“[home]”
        //若想在文档的结尾处创建数据区域则第三个参数为“[end]”
        DataRegion dataRegion1 = doc.createDataRegion("reg1", DataRegionInsertType.After, "[home]");
        //设置创建的数据区域的可编辑性
        dataRegion1.setEditing(true);
        //给数据区域赋值
        dataRegion1.setValue("第一个数据区域\r\n");

        DataRegion dataRegion2 = doc.createDataRegion("reg2", DataRegionInsertType.After, "reg1");
        dataRegion2.setEditing(true);
        dataRegion2.setValue("第二个数据区域");

        DataRegion dataRegion3 = doc.createDataRegion("PO_table1", DataRegionInsertType.After, "reg2");
        dataRegion3.setEditing(true);

        //在word中指定的"PO_table1"的数据区域内动态创建一个3行5列的表格
        Table table1 = doc.openDataRegion("PO_table1").createTable(3, 5, WdAutoFitBehavior.wdAutoFitWindow);
        //合并(1,1)到(3,1)的单元格并赋值
        table1.openCellRC(1, 1).mergeTo(3, 1);
        table1.openCellRC(1, 1).setValue("合并后的单元格");
        //给表格table1中剩余的单元格赋值
        for (int i = 1; i < 4; i++) {
            table1.openCellRC(i, 2).setValue("AA" + String.valueOf(i));
            table1.openCellRC(i, 3).setValue("BB" + String.valueOf(i));
            table1.openCellRC(i, 4).setValue("CC" + String.valueOf(i));
            table1.openCellRC(i, 5).setValue("DD" + String.valueOf(i));
        }

        poCtrl.setWriter(doc);//此行必须

        poCtrl.setWriter(doc);
        //隐藏菜单栏
        poCtrl.setMenubar(false);
        //隐藏工具栏
        poCtrl.setCustomToolbar(false);

        //打开word文件
        poCtrl.webOpen("C:\\Users\\huang\\Desktop\\test.docx", OpenModeType.docAdmin, "张三");

        ModelAndView mv = new ModelAndView("Word");
        mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
        return mv;
    }

    @RequestMapping("/save")
    public void saveFile(HttpServletRequest request, HttpServletResponse response) {
        FileSaver fs = new FileSaver(request, response);
        fs.saveToFile("C:\\Users\\huang\\Desktop\\在线编辑\\" + fs.getFileName());
        fs.close();
    }

    /**
     * 添加PageOffice的服务器端授权程序Servlet（必须）
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean<com.zhuozhengsoft.pageoffice.poserver.Server> servletRegistrationBean() {
        com.zhuozhengsoft.pageoffice.poserver.Server poserver = new com.zhuozhengsoft.pageoffice.poserver.Server();
        // 设置PageOffice注册成功后,license.lic文件存放的目录
        poserver.setSysPath(poSysPath);
        ServletRegistrationBean<com.zhuozhengsoft.pageoffice.poserver.Server> srb = new ServletRegistrationBean<>(poserver);
        srb.addUrlMappings("/poserver.zz");
        srb.addUrlMappings("/posetup.exe");
        srb.addUrlMappings("/pageoffice.js");
        srb.addUrlMappings("/jquery.min.js");
        srb.addUrlMappings("/pobstyle.css");
        srb.addUrlMappings("/sealsetup.exe");
        return srb;
    }
}