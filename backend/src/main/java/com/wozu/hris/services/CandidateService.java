package com.wozu.hris.services;

import com.wozu.hris.models.Candidate;
import com.wozu.hris.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CandidateService {
    // Adding the candidate repository as a dependency
    @Autowired
    CandidateRepository candidateRepository;

    // Returns all the candidates
    public List<Candidate> allCandidates() {
        return candidateRepository.findAll();
    }
    // Creates a candidate
    public Candidate createCandidate(Candidate e) {
        return candidateRepository.save(e);
    }
    // Reads a candidate by id
    public Candidate findCandidate(Long id) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);
        if(optionalCandidate.isPresent()) {
            return optionalCandidate.get();
        } else {
            return null;
        }
    }
    // Updates an candidate
    public Candidate updateCandidate(Long id, Candidate candidate) {
        Optional<Candidate> optionalE = candidateRepository.findById(id);
        if(optionalE.isPresent()) {
            return candidateRepository.save(candidate);
        } else {
            return null;
        }
    }
    // Delete an candidate
    public void deleteCandidate(Long id) {
        this.candidateRepository.deleteById(id);
    }
}

