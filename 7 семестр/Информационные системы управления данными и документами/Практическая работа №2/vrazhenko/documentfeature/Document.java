package com.vrazhenko.documentfeature;

import java.time.LocalDate;

public class Document {
    private String title;
    private String regNumber;
    private LocalDate regDate;
    private String type;
    private boolean urgent;

    public Document() {
    }

    public Document(String title, String regNumber, LocalDate regDate, String type, boolean urgent) {
        this.title = title;
        this.regNumber = regNumber;
        this.regDate = regDate;
        this.type = type;
        this.urgent = urgent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", regDate=" + regDate +
                ", type='" + type + '\'' +
                ", urgent=" + urgent +
                '}';
    }
}
