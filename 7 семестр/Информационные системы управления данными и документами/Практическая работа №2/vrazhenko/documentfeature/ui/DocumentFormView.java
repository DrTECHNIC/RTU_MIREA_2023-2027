package com.vrazhenko.documentfeature.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.router.Route;
import com.vrazhenko.base.ui.MainLayout;
import com.vrazhenko.documentfeature.Document;
import com.vrazhenko.documentfeature.DocumentService;

import java.time.LocalDate;

@Route(value = "document-form", layout = MainLayout.class)
public class DocumentFormView extends VerticalLayout {

    private final DocumentService documentService;
    private final Binder<Document> binder = new Binder<>(Document.class);

    private final TextField titleField = new TextField("Название");
    private final TextField regNumberField = new TextField("Регистрационный номер");
    private final DatePicker regDatePicker = new DatePicker("Дата регистрации");
    private final ComboBox<String> typeComboBox = new ComboBox<>("Тип документа");
    private final Checkbox urgentCheckbox = new Checkbox("Срочный документ");
    private final Button saveButton = new Button("Сохранить");
    private final Button clearButton = new Button("Очистить");

    public DocumentFormView(DocumentService documentService) {
        this.documentService = documentService;

        typeComboBox.setItems("Приказ", "Распоряжение", "Служебная записка");
        typeComboBox.setPlaceholder("Выберите тип");

        FormLayout formLayout = new FormLayout();
        formLayout.add(titleField, regNumberField, regDatePicker, typeComboBox, urgentCheckbox);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        add(formLayout, saveButton, clearButton);

        binder.forField(titleField)
                .asRequired("Название не может быть пустым")
                .bind(Document::getTitle, Document::setTitle);

        binder.forField(regNumberField)
                .asRequired("Регистрационный номер обязателен")
                .withValidator(new RegexpValidator(
                        "Номер должен соответствовать формату XXX-XX (например, 123-45)",
                        "^\\d{3}-\\d{2}$"))
                .bind(Document::getRegNumber, Document::setRegNumber);

        binder.forField(regDatePicker)
                .withValidator(date -> date != null && !date.isAfter(LocalDate.now()),
                        "Дата регистрации не может быть позже текущей")
                .bind(Document::getRegDate, Document::setRegDate);

        binder.forField(typeComboBox)
                .asRequired("Выберите тип документа")
                .bind(Document::getType, Document::setType);

        binder.forField(urgentCheckbox)
                .bind(Document::isUrgent, Document::setUrgent);

        saveButton.addClickListener(event -> saveDocument());
        clearButton.addClickListener(event -> clearForm());
    }

    private void saveDocument() {
        Document document = new Document();

        if (binder.writeBeanIfValid(document)) {
            System.out.println("Сохранён документ: " + document);

            documentService.saveDocument(document);

            Notification.show("Документ успешно сохранён: " + document.getTitle());
            clearForm();
        } else {
            Notification.show("Пожалуйста, исправьте ошибки в форме",
                    3000, Notification.Position.MIDDLE);
        }
    }

    private void clearForm() {
        binder.readBean(new Document());
    }
}
