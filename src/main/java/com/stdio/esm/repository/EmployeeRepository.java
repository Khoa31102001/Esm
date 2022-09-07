package com.stdio.esm.repository;

import com.stdio.esm.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAll();
    Optional<Employee> findByIdAndDeleteFlagIsFalse(Long id);

    Optional<Employee> findByNameAndDeleteFlagIsFalse(String name);

    boolean existsEmployeeById(Long employeeId);
}
