package com.valten.repository;

import com.valten.model.RailWayDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RailWayRepository extends ElasticsearchRepository<RailWayDocument, String> {

}
