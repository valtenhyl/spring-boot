package com.valten.service;

import com.valten.model.RailWayDocument;

import java.util.List;

public interface RailWayService {

    void save(RailWayDocument railWayDocument);

    void saveAll(List<RailWayDocument> list);
}
