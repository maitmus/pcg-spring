package com.github.maitmus.pcgspring.common.entity;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected EntityStatus status;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Column
    private LocalDateTime deletedAt;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            this.status = EntityStatus.ACTIVE;
        }
    }

    public void delete() {
        this.status = EntityStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }
}

