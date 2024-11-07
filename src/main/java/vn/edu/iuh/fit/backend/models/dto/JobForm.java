package vn.edu.iuh.fit.backend.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobForm {
    private String jobName;
    private String jobDesc;
    private Long companyId;
    private List<Long> skillIds; // Danh sách ID của các kỹ năng yêu cầu
    private List<Byte> skillLevels; // Mức độ kỹ năng tương ứng
}
