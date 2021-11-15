package com.wozu.hris.services;

import com.wozu.hris.models.Department;
import com.wozu.hris.models.DepartmentEmployee;
import com.wozu.hris.models.Employee;
import com.wozu.hris.repositories.DepartmentEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentEmployeeService {

    @Autowired
    DepartmentEmployeeRepository dERepo;

    public DepartmentEmployee createDepartmentEmployee(DepartmentEmployee departmentEmployee){return dERepo.save(departmentEmployee);}

    public boolean existsByEmployeeAndDepartment(Employee employee, Department department){return dERepo.existsByEmployeeAndDepartment(employee, department);}

    public DepartmentEmployee updateDepartmentEmployee(Long id, DepartmentEmployee departmentEmployee){
        Optional<DepartmentEmployee> de = dERepo.findById(id);

        if(de.isPresent()){
            return dERepo.save(departmentEmployee);
        }else{
            return null;
        }
    }

    public List<DepartmentEmployee> findAllByDepartment(Department department){
        return dERepo.findAllByDepartment(department);
    }

    public List<DepartmentEmployee> findAllByEmployee(Employee employee){
        return dERepo.findAllByEmployee(employee);
    }

    public void deleteDepartmentEmployee(Long id){
        dERepo.deleteById(id);
    }

    public DepartmentEmployee findByEmployeeAndDepartment(Employee employee, Department department){
        Optional<DepartmentEmployee> optionalDE = dERepo.findByEmployeeAndDepartment(employee, department);
        if(optionalDE.isPresent()){
            return optionalDE.get();
        }else{
            return null;
        }
    }
}
