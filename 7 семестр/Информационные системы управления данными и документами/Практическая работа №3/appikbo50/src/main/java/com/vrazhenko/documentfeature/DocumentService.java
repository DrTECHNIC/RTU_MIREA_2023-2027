package com.vrazhenko.documentfeature;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public List<Document> findAllDocuments() {
        return documentRepository.findAll();
    }

    public List<Document> findDocumentsByTitle(String title) {
        if (title == null || title.isBlank()) {
            return findAllDocuments();
        }
        return documentRepository.findByTitleContainingIgnoreCase(title);
    }

    // Комбинированный поиск по названию + типу
    public List<Document> findDocuments(String title, String type) {
        boolean noTitle = (title == null || title.isBlank());
        boolean noType = (type == null || type.isBlank() || type.equals("Все"));

        if (noTitle && noType) {
            return documentRepository.findAll();
        } else if (noTitle) {
            return documentRepository.findByType(type);
        } else if (noType) {
            return documentRepository.findByTitleContainingIgnoreCase(title);
        } else {
            return documentRepository.findByTitleContainingIgnoreCaseAndType(title, type);
        }
    }
}
