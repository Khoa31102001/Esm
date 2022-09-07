package com.stdio.esm.service;

import com.stdio.esm.dto.EmployeeDto;
import com.stdio.esm.exception.EmployeeNotFound;
import com.stdio.esm.model.Employee;
import com.stdio.esm.model.mapper.EmployeeMapper;
import com.stdio.esm.repository.EmployeeRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AnhKhoa
 * @since 11/07/2022 - 11:11
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);


    /**
     * <b>Get all Employee</b>
     *
     * @return {@link Map<String, List<EmployeeDto>>}
     */
    @Transactional(readOnly = true)
    public Map<String, List<EmployeeDto>> getAllEmployee() {
        LOGGER.info("Started get all employees in service layer");
        Map<String, List<EmployeeDto>> responseData = new HashMap<>();
        responseData.put("employees", employeeMapper.employeesListToEmployeeDtoList(employeeRepository.findAll()));
        LOGGER.info("Finished get all employees in service layer");
        return responseData;
    }

    /**
     * Get employee by id
     *
     * @param employeeId
     * @return {@link EmployeeDto }
     * @throws EmployeeNotFound
     */
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(@NotNull Long employeeId) throws EmployeeNotFound {
        LOGGER.info("Started get a employee by id in service layer");
        Employee employee = employeeRepository.findByIdAndDeleteFlagIsFalse(employeeId).orElseThrow(() -> new EmployeeNotFound("Account is not existed"));
        LOGGER.info("Finished get a employee by id in service layer");
        return employeeMapper.employeeToEmployeeDto(employee);
    }

    /**
     * Get employee by name
     *
     * @param name
     * @return {@link EmployeeDto }
     * @throws EmployeeNotFound
     */
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeByName(@NotBlank String name) throws EmployeeNotFound {
        LOGGER.info("Started get an employee by name in service layer");
        Employee employee = employeeRepository.findByNameAndDeleteFlagIsFalse(name).orElseThrow(() -> new UsernameNotFoundException("Account is not existed"));
        LOGGER.info("Finished get an employee by name in service layer");
        return employeeMapper.employeeToEmployeeDto(employee);
    }

    /**
     * Create new employee
     *
     * @param employeeDto
     * @return {@link EmployeeDto}
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public EmployeeDto addNewEmployee(@NotNull EmployeeDto employeeDto) {
        LOGGER.info("Started create a employee in service layer");

        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDto);

        Employee employeeSaved = employeeRepository.save(employee);

        LOGGER.info("Finished create a employee in service layer");

        return employeeMapper.employeeToEmployeeDto(employeeSaved);
    }

    /**
     * Update employee
     *
     * @param employeeDto
     * @return {@link EmployeeDto}
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public EmployeeDto updateEmployee(@NotNull EmployeeDto employeeDto) throws EmployeeNotFound {
        LOGGER.info("Started update an employee in service layer");
        if (employeeDto.getId() == 0 || !employeeRepository.existsEmployeeById(employeeDto.getId())) {
            throw new EmployeeNotFound("Employee is not existed");
        }

        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDto);


        Employee employeeSaved = employeeRepository.save(employee);

        LOGGER.info("Finished update a employee in service layer");

        return employeeMapper.employeeToEmployeeDto(employeeSaved);
    }

    /**
     * Delete employee by id
     *
     * @param employeeId
     * @throws EmployeeNotFound
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteEmployeeById(@NotNull Long employeeId) throws EmployeeNotFound {
        LOGGER.info("Started delete an employee by id in service layer");
        employeeRepository.findByIdAndDeleteFlagIsFalse(employeeId).ifPresentOrElse((employee) -> {
            employee.setDeleteFlag(true);
        }, () -> {
            throw new EmployeeNotFound("Employee is not existed");
        });
        LOGGER.info("Finished delete an employee by id in service layer");
    }

    /**
     * Delete employee by name
     *
     * @param name
     * @throws UsernameNotFoundException
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteEmployeeByName(@NotBlank String name) throws EmployeeNotFound {
        LOGGER.info("Started delete an employee by name in service layer");
        employeeRepository.findByNameAndDeleteFlagIsFalse(name).ifPresentOrElse((employee) -> {
            employee.setDeleteFlag(true);
        }, () -> {
            throw new EmployeeNotFound("Employee is not existed");
        });
        LOGGER.info("Finished delete an employee by name in service layer");
    }
}
