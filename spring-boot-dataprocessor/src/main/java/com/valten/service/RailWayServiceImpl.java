package com.valten.service;

import com.valten.model.RailWayDocument;
import com.valten.repository.RailWayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("railWayService")
public class RailWayServiceImpl implements RailWayService {

    @Autowired
    private RailWayRepository railWayRepository;

    @Override
    public void save(RailWayDocument railWayDocument) {
        railWayRepository.save(railWayDocument);
    }

    @Override
    public void saveAll(List<RailWayDocument> list) {
        railWayRepository.saveAll(list);
    }
}
