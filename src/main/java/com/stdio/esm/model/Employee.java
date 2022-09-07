package com.stdio.esm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stdio.esm.utilities.EsmEnum;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name ="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "address",length = 255)
    private String address;

    @Column(name = "avatar_img")
    private String avatar;

    @Column(name = "start_date")
    private Instant startDate;

    @CreationTimestamp
    @Column(name = "create_at",nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "modify_at",nullable = false)
    private Instant modifyAt;

    @Column(name ="delete_flag")
    private Boolean deleteFlag;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 10, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "place_of_birth", nullable = false)
    private String placeOfBirth;

    @Column(name = "nationality", length = 30)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private EsmEnum.Gender gender;

    @Column(name = "website")
    private String website;

    @Column(name = "professional_summary ")
    private String professionalSummary ;

    @PrePersist
    public void prePersist() {
        deleteFlag = false;
    }


    @OneToOne(mappedBy = "employee")
    private Account account;

    @ManyToMany
    @JoinTable(name = "employee_skills",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id"))
    @JsonIgnore
    private List<Skill> skills;

    @ManyToMany
    @JoinTable(name = "employee_skills",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "level_of_skill_id"))
    @JsonIgnore
    private List<SkillLevel> skillLevels;

    @ManyToMany(mappedBy = "employees")
    @JsonIgnore
    private List<Project> employees_projects;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    List<Education> educations;


    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY)
    private EmploymentHistory employmentHistories;

    @Transient
    private List<Map<String, Object>> existingSkills;

    @Transient
    private List<Map<String, Object>> proficientSkills;

    @Transient
    private List<Map<String, Object>> projects;
}
