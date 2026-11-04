package com.vrazhenko.documentfeature;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DocumentIndexRepository extends ElasticsearchRepository<DocumentIndex, Long> {}
