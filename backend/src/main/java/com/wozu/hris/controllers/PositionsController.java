package com.wozu.hris.controllers;

import com.wozu.hris.models.Position;
import com.wozu.hris.repositories.PositionRepository;
import com.wozu.hris.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api/positions")
public class PositionsController {

    @Autowired
    PositionService positionService;
    @Autowired
    PositionRepository eRepo;

    // get all positions
    @PreAuthorize("hasRole('HR') or hasRole('MANAGER')")
    @GetMapping("/positions")
    public ResponseEntity<List<Position>> getAllPositions(){
        try {
            List<Position> positions = new ArrayList<Position>();

            eRepo.findAll().forEach(positions::add);

            if(positions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(positions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create position rest api
    @PostMapping("/positions")
    public Position createPosition(@RequestBody Position position){
        return positionService.createPosition(position);
    }

    // get position by id rest api
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
    @GetMapping("/positions/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id){
        Position position = positionService.findAccountById(id);
        return ResponseEntity.ok(position);
    }

    // update position rest api
    @PutMapping("/positions/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long id, @RequestBody Position positionDetails){
        Position position = positionService.findAccountById(id);


        position.setName(positionDetails.getName());


        Position updatedPosition = positionService.updateDepartments(id, position);

        return  ResponseEntity.ok(updatedPosition);
    }

    // delete position rest api
    @DeleteMapping("/positions/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePosition(@PathVariable Long id){
        Position position = positionService.findAccountById(id);
        positionService.deletePosition(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }
}
