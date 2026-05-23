package com.enterprise.db.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", allocationSize = 50)
    public Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt;

    @Column(name = "created_by", length = 100)
    public String createdBy;

    @Column(name = "updated_by", length = 100)
    public String updatedBy;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
