package com.stdio.esm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "image", length = 255, nullable = false)
    private String image;

    @CreationTimestamp
    @Column(name = "start_time", nullable = false, updatable = false)
    private Instant startTime;

    @UpdateTimestamp
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;

    @PrePersist
    public void prePersist() {
        deleteFlag = false;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @JsonIgnore
    private ProjectStatus projectStatus;


    @ManyToMany
    @JoinTable(name = "employees_projects",
    joinColumns = @JoinColumn(name = "project_id"),
    inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @JsonIgnore
    private List<Employee> employees;


    @ManyToMany
    @JoinTable(name = "project_skills",
    joinColumns = @JoinColumn(name = "project_id"),
    inverseJoinColumns = @JoinColumn(name = "skills_id"))
    @JsonIgnore
    private List<Skill> skills;
}
