package com.vrazhenko.documentfeature;

import org.opensearch.data.client.orhlc.NativeSearchQueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentIndexRepository documentIndexRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public DocumentService(DocumentRepository documentRepository,
                           DocumentIndexRepository documentIndexRepository,
                           ElasticsearchOperations elasticsearchOperations) {
        this.documentRepository = documentRepository;
        this.documentIndexRepository = documentIndexRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public Document saveDocument(Document document) {
        Document savedDoc = documentRepository.save(document);

        DocumentIndex indexDto = new DocumentIndex();
        indexDto.setId(savedDoc.getId());
        indexDto.setTitle(savedDoc.getTitle());
        indexDto.setRegNumber(savedDoc.getRegNumber());
        indexDto.setContent(savedDoc.getContent());

        documentIndexRepository.save(indexDto);

        return savedDoc;
    }

    public List<DocumentIndex> search(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }

        var multiMatch = QueryBuilders.multiMatchQuery(query)
                .field("title", 3.0f)
                .field("content", 1.0f);

        var searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatch)
                .withMaxResults(50)
                .build();

        SearchHits<DocumentIndex> searchHits =
                elasticsearchOperations.search(searchQuery, DocumentIndex.class);

        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
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
