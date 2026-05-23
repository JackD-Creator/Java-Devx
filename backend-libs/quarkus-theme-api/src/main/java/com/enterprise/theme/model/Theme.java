package com.enterprise.theme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "themes")
public class Theme extends PanacheEntity {

    @Column(nullable = false, length = 100)
    public String name;

    @Column(nullable = false, unique = true, length = 100)
    public String slug;

    @Column(nullable = false)
    public boolean active;

    @Column(nullable = false, length = 10)
    public String mode = "light";

    @Column(name = "tokens_json", columnDefinition = "TEXT")
    public String tokensJson;

    @Column(name = "tenant_id", length = 100)
    public String tenantId;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static Theme findActive() {
        return find("active", true).firstResult();
    }

    public static Theme findBySlug(String slug) {
        return find("slug", slug).firstResult();
    }

    public static List<Theme> findByTenant(String tenantId) {
        return list("tenantId", tenantId);
    }
}
