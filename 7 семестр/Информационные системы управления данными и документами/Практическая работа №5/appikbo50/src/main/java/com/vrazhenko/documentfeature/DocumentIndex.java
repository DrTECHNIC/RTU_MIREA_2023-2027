package com.vrazhenko.documentfeature;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "documents_index")
public class DocumentIndex {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "russian")
    private String title;

    @Field(type = FieldType.Keyword)
    private String regNumber;

    @Field(type = FieldType.Text, analyzer = "russian")
    private String content;

    public DocumentIndex() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getRegNumber() { return regNumber; }
    public void setRegNumber(String regNumber) { this.regNumber = regNumber; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
