package com.wozu.hris.services;

import com.wozu.hris.models.Department;
import com.wozu.hris.models.DepartmentEmployee;
import com.wozu.hris.models.Employee;
import com.wozu.hris.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//Department services
//created: 10/25/21
//updated: 10/25/21 AF

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository deptRepo;

    @Autowired
    DepartmentEmployeeService deService;

    //return all departments
    public List<Department> allDepts() {
        return deptRepo.findAll();
    }
    // create department
    public Department createDepartment(Department deptItem) {
        return deptRepo.save(deptItem);
    }

    // Find department by name
    public Department findByDeptName(String dn) {
        return deptRepo.findByName(dn);
    }

    // Find department by id
    public Department findDepartmentById(Long did) {
        Optional<Department> d = deptRepo.findById(did);
        return d.orElse(null);
    }

    // update department
    public Department updateDepartemnts(Long did, Department d) {
        Optional<Department> opd = deptRepo.findById(did);
        if(opd.isPresent()) {
            return deptRepo.save(d);
        } else {
            return null;
        }
    }

    //delete department
    public void deleteDepartment(Long did) {
        Department d = deptRepo.findById(did).get();
        List<DepartmentEmployee> de = d.getEmployees();
        de.forEach((e)-> e.setEmployee(null));
        deService.saveAll(de);

        this.deptRepo.deleteById(did);
    }

    public Department findByManagerId(Employee e){
        return deptRepo.findByManagerId(e.getId());
    }

    public Boolean existsByName(String name){
        return deptRepo.existsByName(name);
    }

    public Boolean existsById(Long Id){
        return deptRepo.existsById(Id);
    }
}
