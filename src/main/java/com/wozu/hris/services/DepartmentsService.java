package com.wozu.hris.services;

import com.wozu.hris.models.Departments;
import com.wozu.hris.repositories.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//Departments services
//created: 10/25/21
//updated: 10/25/21 AF

@Service
public class DepartmentsService {
    @Autowired DepartmentsRepository deptRepo;

    //return all departments
    public List<Departments> allDepts() {
        return deptRepo.findAll();
    }
    // create department
    public Departments createDepartment(Departments deptItem) {
        return deptRepo.save(deptItem);
    }

    // Find department by name
    public Departments findByDeptName(String dn) {
        return deptRepo.findByDeptName(dn);
    }

    // Find department by id
    public Departments findDepartmentById(Long did) {
        Optional<Departments> d = deptRepo.findById(did);
        return d.orElse(null);
    }

    // update department
    public Departments updateDepartemnts(Long did, Departments d) {
        Optional<Departments> opd = deptRepo.findById(did);
        if(opd.isPresent()) {
            return deptRepo.save(d);
        } else {
            return null;
        }
    }

    //delete department
    public void deleteDepartment(Long did) {
        this.deptRepo.deleteById(did);
    }
}
