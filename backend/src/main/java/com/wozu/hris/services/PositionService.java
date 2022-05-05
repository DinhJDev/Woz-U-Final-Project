package com.wozu.hris.services;

import com.wozu.hris.models.DepartmentEmployee;
import com.wozu.hris.models.Position;
import com.wozu.hris.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//positions services
//created: 10/25/21
//updated: 10/25/21 AF

@Service
public class PositionService {
    @Autowired
    PositionRepository posRepo;

    @Autowired
    DepartmentEmployeeService deService;

    //return all positions
    public List<Position> allPos() {
        return posRepo.findAll();
    }
    // create positions
    public Position createPosition(Position posItem) {
        return posRepo.save(posItem);
    }

    // Find position by name
    public Position findByDeptName(String pn) {
        return posRepo.findByName(pn);
    }

    // Find position by id
    public Position findAccountById(Long pid) {
        Optional<Position> p = posRepo.findById(pid);
        return p.orElse(null);
    }

    public Position findByName(String n){
        return posRepo.findByName(n);

    }



    // update position
    public Position updateDepartments(Long pid, Position p) {
        Optional<Position> opp = posRepo.findById(pid);
        if(opp.isPresent()) {
            return posRepo.save(p);
        } else {
            return null;
        }
    }

    //delete position
    public void deletePosition(Long pid) {
        Position p = posRepo.findById(pid).get();
        List<DepartmentEmployee> de = p.getDepartmentEmployee();
        de.forEach((e)->e.setPosition(null));
        deService.saveAll(de);
        this.posRepo.deleteById(pid);

    }

    public Boolean existsByName(String name){
        return posRepo.existsByName(name);
    }

    public Boolean existsById(Long Id){
        return posRepo.existsById(Id);
    }

}
