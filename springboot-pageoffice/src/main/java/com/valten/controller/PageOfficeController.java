package com.valten.controller;

import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class PageOfficeController {

    @Value("${pageoffice.posyspath}")
    private String poSysPath;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView showIndex() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    @RequestMapping(value = "/word", method = RequestMethod.GET)
    public ModelAndView showWord(HttpServletRequest request, Map<String, Object> map) {

        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
        // 设置服务页面
        poCtrl.setServerPage("/poserver.zz");
        // 添加自定义保存按钮
        poCtrl.addCustomToolButton("保存", "Save", 1);
        // 设置处理文件保存的请求方法
        poCtrl.setSaveFilePage("/save");
        //打开word
        poCtrl.webOpen("C:\\Users\\huang\\Desktop\\test.docx", OpenModeType.docAdmin, "张三");
        map.put("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));

        ModelAndView mv = new ModelAndView("Word");
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