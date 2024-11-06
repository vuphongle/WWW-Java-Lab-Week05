package vn.edu.iuh.fit.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.entities.Candidate;
import vn.edu.iuh.fit.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.backend.services.CandidateService;
import vn.edu.iuh.fit.backend.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Optional<Candidate> getCandidateById(Long id) {
        return candidateRepository.findById(id);
    }

    @Override
    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate updateCandidate(Long id, Candidate candidateDetails) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found for this id :: " + id));

        candidate.setDob(candidateDetails.getDob());
        candidate.setEmail(candidateDetails.getEmail());
        candidate.setFullName(candidateDetails.getFullName());
        candidate.setPhone(candidateDetails.getPhone());
        candidate.setAddress(candidateDetails.getAddress());

        return candidateRepository.save(candidate);
    }

    @Override
    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found for this id :: " + id));
        candidateRepository.delete(candidate);
    }
}
