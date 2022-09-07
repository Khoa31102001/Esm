package com.stdio.esm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "project_status")
@Data
public class ProjectStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private Instant created_at;

    @UpdateTimestamp
    @Column(name = "modify_at", nullable = false)
    private Instant modify_at;

    @OneToMany(mappedBy = "projectStatus", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Project> projects;

}
