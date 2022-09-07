package com.stdio.esm.model.mapper;

import com.stdio.esm.dto.AccountDto;
import com.stdio.esm.dto.EmployeeDto;
import com.stdio.esm.model.Account;
import com.stdio.esm.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee employeeDtoToEmployee(EmployeeDto employeeDto);


    EmployeeDto employeeToEmployeeDto(Employee employee);

    List<EmployeeDto> employeesListToEmployeeDtoList(List<Employee> employees);
}
