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
import java.util.Map;

@Entity
@Table(name = "skill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "modify_at", nullable = false)
    private Instant modifyAt;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;

    @PrePersist
    public void prePersist() {
        deleteFlag = false;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_type_id", referencedColumnName = "id")
    private SkillTypes skillTypes;

    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private List<Employee> employees_skills;

    @ManyToMany
    @JoinTable(name = "employee_skills",
            joinColumns = @JoinColumn(name = "skills_id"),
            inverseJoinColumns = @JoinColumn(name = "level_of_skill_id"))
    @JsonIgnore
    private List<SkillLevel> skillLevels;

    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private List<Project> projects_skills;

    @Transient
    private List<Map<String, Object>> projects;

    @Transient
    private List<Map<String, Object>> employees;
}
