package com.valten.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.valten.mapper.YpbgMapper;
import com.valten.support.YpbgModel;
import com.valten.service.inf.IYpbgService;
import org.springframework.stereotype.Service;

@Service("ypbgService")
public class YpbgServiceImpl extends ServiceImpl<YpbgMapper, YpbgModel> implements IYpbgService {
}
