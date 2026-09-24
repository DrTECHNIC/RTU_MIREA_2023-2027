package com.vrazhenko.examplefeature;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "task")
public class Task {

    public static final int DESCRIPTION_MAX_LENGTH = 300;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "description", nullable = false, length = DESCRIPTION_MAX_LENGTH)
    private String description = "";

    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "due_date")
    @Nullable
    private LocalDate dueDate;

    protected Task() { // To keep Hibernate happy
    }

    public Task(String description, Instant creationDate) {
        setDescription(description);
        this.creationDate = creationDate;
    }

    public @Nullable Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.length() > DESCRIPTION_MAX_LENGTH) {
            throw new IllegalArgumentException("Description length exceeds " + DESCRIPTION_MAX_LENGTH);
        }
        this.description = description;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public @Nullable LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(@Nullable LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Task other = (Task) obj;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        // Hashcode should never change during the lifetime of an object. Because of
        // this we can't use getId() to calculate the hashcode. Unless you have sets
        // with lots of entities in them, returning the same hashcode should not be a
        // problem.
        return getClass().hashCode();
    }
}
