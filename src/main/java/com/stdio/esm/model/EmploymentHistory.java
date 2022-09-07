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

@Entity
@Table(name = "employment_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "job_title", length = 255, nullable = false, unique = false)
    private String jobTitle;

    @Column(name = "employer", length = 255, nullable = false, unique = false)
    private String employer;

    @Column(name = "description ", nullable = true, unique = false)
    private String description;

    @CreationTimestamp
    @Column(name = "start_date", nullable = false, updatable = false)
    private Instant startDate;

    @UpdateTimestamp
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Employee employee;
}
