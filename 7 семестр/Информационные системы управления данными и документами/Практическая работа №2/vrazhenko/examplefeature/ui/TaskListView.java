package com.vrazhenko.examplefeature.ui;

import com.vrazhenko.base.ui.ViewTitle;
import com.vrazhenko.examplefeature.Task;
import com.vrazhenko.examplefeature.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route(value = "")
@PageTitle("Task List")
@Menu(order = 0, icon = "icons/clipboard-check.svg", title = "Task List")
class TaskListView extends VerticalLayout {

    private final TaskService taskService;

    final TextField description;
    final DatePicker dueDate;
    final Button createBtn;
    final Grid<Task> taskGrid;

    TaskListView(TaskService taskService) {
        this.taskService = taskService;

        description = new TextField();
        description.setPlaceholder("What do you want to do?");
        description.setAriaLabel("Task description");
        description.setMaxLength(Task.DESCRIPTION_MAX_LENGTH);
        description.setMinWidth("15em");

        dueDate = new DatePicker();
        dueDate.setPlaceholder("Due date");
        dueDate.setAriaLabel("Due date");

        createBtn = new Button("Create", event -> createTask());
        createBtn.addThemeVariants(ButtonVariant.PRIMARY);

        var toolbar = new HorizontalLayout();
        toolbar.add(new ViewTitle("Task List"), description, dueDate, createBtn);
        toolbar.setFlexGrow(1, description, dueDate);
        toolbar.setWrap(true);
        toolbar.setWidthFull();

        var dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(getLocale())
                .withZone(ZoneId.systemDefault());
        var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());

        taskGrid = new Grid<>();
        taskGrid.setItems(query -> taskService.list(toSpringPageRequest(query)).stream());
        taskGrid.addColumn(Task::getDescription).setHeader("Description");
        taskGrid.addColumn(task -> Optional.ofNullable(task.getDueDate()).map(dateFormatter::format).orElse("Never"))
                .setHeader("Due Date");
        taskGrid.addColumn(task -> dateTimeFormatter.format(task.getCreationDate())).setHeader("Creation Date");
        taskGrid.setEmptyStateText("You have no tasks to complete");
        taskGrid.setSizeFull();

        setSizeFull();
        add(toolbar, taskGrid);
    }

    private void createTask() {
        if (description.getValue().isBlank()) {
            description.setInvalid(true);
            description.setErrorMessage("Description is required");
            return;
        }
        taskService.createTask(description.getValue(), dueDate.getValue());
        taskGrid.getDataProvider().refreshAll();
        description.clear();
        dueDate.clear();
        Notification.show("Task added", 3000, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.SUCCESS);
    }
}
