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
@Table(name = "level_of_skill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillLevel {
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

//    @OneToMany(mappedBy = "levelOfSkill", cascade = CascadeType.ALL)
//    List<EmployeeSkills> employeeSkills;

    @ManyToMany(mappedBy = "skillLevels")
    @JsonIgnore
    private List<Employee> employees;

    @ManyToMany(mappedBy = "skillLevels")
    @JsonIgnore
    private List<Skill> skills;

}
