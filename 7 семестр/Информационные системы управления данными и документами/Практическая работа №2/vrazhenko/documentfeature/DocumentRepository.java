package com.vrazhenko.documentfeature;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DocumentRepository {
    private final List<Document> documents = new ArrayList<>();

    public Document save(Document document) {
        documents.add(document);
        return document;
    }

    public List<Document> findAll() {
        return documents;
    }

    public Optional<Document> findById(Long id) {
        return Optional.empty();
    }
}
