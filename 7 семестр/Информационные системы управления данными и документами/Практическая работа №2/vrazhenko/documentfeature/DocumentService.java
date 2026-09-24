package com.vrazhenko.documentfeature;

import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }
}
