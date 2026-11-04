package com.vrazhenko.documentfeature.ui;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vrazhenko.base.ui.MainLayout;
import com.vrazhenko.documentfeature.Document;
import com.vrazhenko.documentfeature.DocumentIndex;
import com.vrazhenko.documentfeature.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.regex.Pattern;

@Route(value = "documents", layout = MainLayout.class)
public class DocumentListView extends VerticalLayout {

    private final DocumentService documentService;

    private final Grid<Document> mainGrid = new Grid<>(Document.class);

    private final Grid<DocumentIndex> searchGrid = new Grid<>(DocumentIndex.class);

    private final TextField searchField = new TextField("Поиск по названию (JPA)");

    private final ComboBox<String> typeFilter = new ComboBox<>("Тип документа");

    private final TextField fullTextSearchField = new TextField("Полнотекстовый поиск (OpenSearch)");

    public DocumentListView(@Autowired DocumentService documentService) {
        this.documentService = documentService;

        searchField.setPlaceholder("Введите часть названия...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(e -> updateMainList());

        typeFilter.setItems("Все", "Приказ", "Распоряжение", "Служебная записка");
        typeFilter.setValue("Все");
        typeFilter.setClearButtonVisible(false);
        typeFilter.addValueChangeListener(e -> updateMainList());

        fullTextSearchField.setPlaceholder("Введите запрос...");
        fullTextSearchField.setClearButtonVisible(true);
        fullTextSearchField.setValueChangeMode(ValueChangeMode.LAZY);
        fullTextSearchField.setWidth("400px");
        fullTextSearchField.addValueChangeListener(e -> updateSearchResults());

        HorizontalLayout filters = new HorizontalLayout(searchField, typeFilter, fullTextSearchField);
        filters.setAlignItems(FlexComponent.Alignment.END);
        filters.setWidthFull();

        configureMainGrid();
        configureSearchGrid();

        searchGrid.setVisible(false);

        add(filters, mainGrid, searchGrid);

        updateMainList();
    }

    private void configureMainGrid() {
        mainGrid.removeAllColumns();

        mainGrid.addColumn(Document::getTitle)
                .setHeader("Название")
                .setSortable(true)
                .setKey("title");

        mainGrid.addColumn(Document::getRegNumber)
                .setHeader("Рег. номер");

        mainGrid.addColumn(Document::getRegDate)
                .setHeader("Дата")
                .setSortable(true)
                .setKey("regDate");

        mainGrid.addColumn(Document::getType)
                .setHeader("Тип");

        mainGrid.addColumn(Document::isUrgent)
                .setHeader("Срочный");

        mainGrid.addColumn(doc -> doc.getCounterparty() != null ? doc.getCounterparty().getName() : "")
                .setHeader("Контрагент");

        mainGrid.addColumn(Document::getContent)
                .setHeader("Содержание")
                .setWidth("400px")
                .setFlexGrow(1);

        mainGrid.setSizeFull();

        mainGrid.sort(List.of(
                new GridSortOrder<>(mainGrid.getColumnByKey("regDate"), SortDirection.DESCENDING)
        ));
    }

    private void configureSearchGrid() {
        searchGrid.removeAllColumns();

        searchGrid.addColumn(DocumentIndex::getTitle)
                .setHeader("Название")
                .setWidth("220px");

        searchGrid.addColumn(DocumentIndex::getRegNumber)
                .setHeader("Рег. номер")
                .setWidth("120px");

        searchGrid.addComponentColumn(doc -> {
                    Div wrapper = new Div();
                    String content = doc.getContent() != null ? doc.getContent() : "";
                    String query = fullTextSearchField.getValue();

                    if (query != null && !query.isBlank()) {
                        String highlighted = content.replaceAll(
                                "(?i)(" + Pattern.quote(query) + ")",
                                "<b style='background-color: yellow; padding: 1px 2px; border-radius: 2px;'>$1</b>"
                        );
                        wrapper.getElement().setProperty("innerHTML", highlighted);
                    } else {
                        wrapper.setText(content);
                    }
                    return wrapper;
                })
                .setHeader("Содержание")
                .setWidth("500px")
                .setFlexGrow(1);

        searchGrid.setSizeFull();
    }

    private void updateMainList() {
        String searchTerm = searchField.getValue();
        String selectedType = typeFilter.getValue();
        mainGrid.setItems(documentService.findDocuments(searchTerm, selectedType));
    }

    private void updateSearchResults() {
        String query = fullTextSearchField.getValue();

        if (query == null || query.isBlank()) {
            searchGrid.setVisible(false);
            mainGrid.setVisible(true);
        } else {
            mainGrid.setVisible(false);
            searchGrid.setVisible(true);

            List<DocumentIndex> results = documentService.search(query);
            searchGrid.setItems(results);
        }
    }
}
