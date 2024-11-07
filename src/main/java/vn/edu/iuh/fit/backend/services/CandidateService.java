package vn.edu.iuh.fit.backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.iuh.fit.backend.models.entities.Candidate;

import java.util.List;
import java.util.Optional;

public interface CandidateService {
    List<Candidate> getAllCandidates();
    Optional<Candidate> getCandidateById(Long id);
    Candidate createCandidate(Candidate candidate);
    Candidate updateCandidate(Long id, Candidate candidateDetails);
    void deleteCandidate(Long id);

    Page<Candidate> getAllCandidates(Pageable pageable);
}