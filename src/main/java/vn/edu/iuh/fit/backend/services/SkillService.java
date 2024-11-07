// SkillService.java
package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.models.entities.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillService {
    List<Skill> getAllSkills();
    Optional<Skill> getSkillById(Long id);
    Skill createSkill(Skill skill);
    Skill updateSkill(Skill skill);
    void deleteSkill(Long id);
}
