package vn.edu.iuh.fit.backend.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JobForm {

    private String jobName;
    private String jobDesc;
    private List<JobSkillForm> jobSkills = new ArrayList<>();
}
