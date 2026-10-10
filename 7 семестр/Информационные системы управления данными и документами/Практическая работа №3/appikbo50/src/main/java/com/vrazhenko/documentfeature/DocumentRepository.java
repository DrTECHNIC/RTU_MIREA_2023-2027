package com.vrazhenko.documentfeature;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    // Поиск документов по части названия (без учета регистра)
    List<Document> findByTitleContainingIgnoreCase(String title);

    // Поиск по типу документа
    List<Document> findByType(String type);

    // Комбинированный поиск по названию и типу
    List<Document> findByTitleContainingIgnoreCaseAndType(String title, String type);
}
