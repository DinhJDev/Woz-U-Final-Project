package com.wozu.hris.services;

import com.wozu.hris.models.Positions;
import com.wozu.hris.repositories.PositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//positions services
//created: 10/25/21
//updated: 10/25/21 AF

@Service
public class PositionsService {
    @Autowired
    PositionsRepository posRepo;

    //return all positions
    public List<Positions> allPos() {
        return posRepo.findAll();
    }
    // create positions
    public Positions createPosition(Positions posItem) {
        return posRepo.save(posItem);
    }

    // Find position by name
    public Positions findByDeptName(String pn) {
        return posRepo.findByPosName(pn);
    }

    // Find position by id
    public Positions findAccountById(Long pid) {
        Optional<Positions> p = posRepo.findById(pid);
        return p.orElse(null);
    }

    // update position
    public Positions updateDepartemnts(Long pid, Positions p) {
        Optional<Positions> opp = posRepo.findById(pid);
        if(opp.isPresent()) {
            return posRepo.save(p);
        } else {
            return null;
        }
    }

    //delete position
    public void deletePosition(Long pid) {
        this.posRepo.deleteById(pid);
    }
}
