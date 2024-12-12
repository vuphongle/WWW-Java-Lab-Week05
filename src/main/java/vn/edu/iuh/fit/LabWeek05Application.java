package vn.edu.iuh.fit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import vn.edu.iuh.fit.backend.enums.CountryCode;
import vn.edu.iuh.fit.backend.models.entities.Address;
import vn.edu.iuh.fit.backend.models.entities.Candidate;
import vn.edu.iuh.fit.backend.models.entities.Skill;
import vn.edu.iuh.fit.backend.repositories.AddressRepository;
import vn.edu.iuh.fit.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.backend.repositories.SkillRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class LabWeek05Application {

	public static void main(String[] args) {
		SpringApplication.run(LabWeek05Application.class, args);
	}

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Bean
	CommandLineRunner initData() {
		return args -> {
			if(skillRepository.count() == 0) {
				List<Skill> skills = createSkills();
				skillRepository.saveAll(skills);
				System.out.println("Đã thêm " + skills.size() + " kỹ năng vào cơ sở dữ liệu.");
			} else {
				System.out.println("Bảng Skill đã chứa dữ liệu. Bỏ qua bước thêm kỹ năng.");
			}

			if(candidateRepository.count() > 50) {
				return;
			}
			Random rnd = new Random();
			for (int i = 1; i <= 1000; i++) {
				// Tạo Address
				Address address = new Address();
				address.setNumber(String.valueOf(rnd.nextInt(1, 1000))); // Số nhà từ 1 đến 999
				address.setStreet("Quang Trung");
				address.setCity("HCM");
				address.setZipcode(String.valueOf(rnd.nextInt(70000, 80000))); // Mã bưu điện từ 70000 đến 79999
				address.setCountry(CountryCode.VIETNAM);

				addressRepository.save(address);

				// Tạo Candidate
				Candidate candidate = new Candidate();
				candidate.setFullName("Nguyen Van " + i);
				candidate.setDob(generateRandomDate(1950, 2000));
				candidate.setAddress(address);
				candidate.setPhone(generateRandomPhoneNumber());
				candidate.setEmail("email_" + i + "@gmail.com");

				candidateRepository.save(candidate);

				if (i % 100 == 0) {
					System.out.println("Đã thêm " + i + " ứng viên.");
				}
			}
			System.out.println("Hoàn thành thêm 1000 ứng viên.");
		};
	}

	/**
	 * Hàm tạo ngày sinh ngẫu nhiên trong khoảng từ năm startYear đến endYear.
	 */
	private LocalDate generateRandomDate(int startYear, int endYear) {
		long minDay = LocalDate.of(startYear, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(endYear, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		return LocalDate.ofEpochDay(randomDay);
	}

	private String generateRandomPhoneNumber() {
		Random rnd = new Random();
		String[] prefixes = {"090", "091", "092", "093", "094", "095", "096", "097", "098", "099", "080", "081", "082", "083", "084", "085", "086", "087", "088", "089"};
		String prefix = prefixes[rnd.nextInt(prefixes.length)];
		StringBuilder phoneNumber = new StringBuilder(prefix);
		for (int i = 0; i < 7; i++) {
			phoneNumber.append(rnd.nextInt(10));
		}
		return phoneNumber.toString();
	}

	/**
	 * Tạo danh sách 20 kỹ năng.
	 */
	private List<Skill> createSkills() {
		List<Skill> skills = new ArrayList<>();
		skills.add(createSkill("Java", "Programming Language", (byte)1));
		skills.add(createSkill("Spring Boot", "Java Framework", (byte)1));
		skills.add(createSkill("SQL", "Database Management", (byte)1));
		skills.add(createSkill("JavaScript", "Programming Language", (byte)1));
		skills.add(createSkill("React", "Frontend Library", (byte)1));
		skills.add(createSkill("Angular", "Frontend Framework", (byte)1));
		skills.add(createSkill("Python", "Programming Language", (byte)1));
		skills.add(createSkill("Django", "Python Framework", (byte)1));
		skills.add(createSkill("C#", "Programming Language", (byte)1));
		skills.add(createSkill("ASP.NET", "C# Framework", (byte)1));
		skills.add(createSkill("Communication", "Soft Skill", (byte)2));
		skills.add(createSkill("Teamwork", "Soft Skill", (byte)2));
		skills.add(createSkill("Problem Solving", "Soft Skill", (byte)2));
		skills.add(createSkill("Time Management", "Soft Skill", (byte)2));
		skills.add(createSkill("Leadership", "Soft Skill", (byte)2));
		skills.add(createSkill("Project Management", "Management Skill", (byte)2));
		skills.add(createSkill("Git", "Version Control", (byte)1));
		skills.add(createSkill("Docker", "Containerization", (byte)1));
		skills.add(createSkill("Kubernetes", "Orchestration", (byte)1));
		skills.add(createSkill("AWS", "Cloud Services", (byte)1));
		// Bạn có thể thêm nhiều kỹ năng hơn nếu cần
		return skills;
	}

	private Skill createSkill(String name, String description, byte type) {
		Skill skill = new Skill();
		skill.setSkillName(name);
		skill.setSkillDescription(description);
		skill.setType(type);
		return skill;
	}

}
