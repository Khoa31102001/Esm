package com.stdio.esm.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */
@Entity
@Data
@Table(name = "account")
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "create_at",nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "modify_at",nullable = false)
    private Instant modifyAt;

    @Column(name ="delete_flag")
    private Boolean deleteFlag;

    @PrePersist
    public void prePersist() {
        if(deleteFlag==null)
        deleteFlag = false;
    }


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(	name = "accounts_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

}
