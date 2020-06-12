package com.valten.controller;

import com.valten.model.YpbgModel;
import com.valten.service.inf.IYpbgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ypbg")
public class YpbgController {

    @Autowired
    private IYpbgService ypbgService;

    @RequestMapping("/list")
    public List<YpbgModel> list() {
        return ypbgService.list(null);
    }
}
