// SkillServiceImpl.java
package vn.edu.iuh.fit.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.entities.Skill;
import vn.edu.iuh.fit.backend.repositories.SkillRepository;
import vn.edu.iuh.fit.backend.services.SkillService;
import vn.edu.iuh.fit.backend.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public Optional<Skill> findById(Long id) {
        return skillRepository.findById(id);
    }

    @Override
    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public Skill updateSkill(Skill skill) {
        if (skill.getId() == null) {
            throw new IllegalArgumentException("Skill ID cannot be null");
        }
        Skill existingSkill = skillRepository.findById(skill.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skill.getId()));
        existingSkill.setSkillName(skill.getSkillName());
        existingSkill.setSkillDescription(skill.getSkillDescription());
        existingSkill.setType(skill.getType());
        return skillRepository.save(existingSkill);
    }

    @Override
    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));
        skillRepository.delete(skill);
    }
}
