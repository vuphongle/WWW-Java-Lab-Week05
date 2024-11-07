package vn.edu.iuh.fit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import vn.edu.iuh.fit.backend.enums.CountryCode;
import vn.edu.iuh.fit.backend.models.entities.Address;
import vn.edu.iuh.fit.backend.models.entities.Candidate;
import vn.edu.iuh.fit.backend.repositories.AddressRepository;
import vn.edu.iuh.fit.backend.repositories.CandidateRepository;

import java.time.LocalDate;
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

//	@Bean
//	CommandLineRunner initData() {
//		return args -> {
//			Random rnd = new Random();
//			for (int i = 1; i <= 1000; i++) {
//				// Tạo Address
//				Address address = new Address();
//				address.setNumber(String.valueOf(rnd.nextInt(1, 1000))); // Số nhà từ 1 đến 999
//				address.setStreet("Quang Trung"); // Đường cố định
//				address.setCity("HCM"); // Thành phố cố định
//				address.setZipcode(String.valueOf(rnd.nextInt(70000, 80000))); // Mã bưu điện từ 70000 đến 79999
//				address.setCountry(CountryCode.VIETNAM); // Quốc gia cố định (Việt Nam)
//
//				addressRepository.save(address);
//
//				// Tạo Candidate
//				Candidate candidate = new Candidate();
//				candidate.setFullName("Nguyen Van " + i); // Tên đầy đủ
//				candidate.setDob(generateRandomDate(1950, 2000)); // Ngày sinh ngẫu nhiên từ năm 1950 đến 2000
//				candidate.setAddress(address);
//				candidate.setPhone(generateRandomPhoneNumber()); // Số điện thoại ngẫu nhiên
//				candidate.setEmail("email_" + i + "@gmail.com"); // Email duy nhất
//
//				candidateRepository.save(candidate);
//
//				// Hiển thị thông tin đã thêm (có thể loại bỏ để tăng tốc độ)
//				if (i % 100 == 0) { // Hiển thị mỗi 100 bản ghi
//					System.out.println("Đã thêm " + i + " ứng viên.");
//				}
//			}
//			System.out.println("Hoàn thành thêm 1000 ứng viên.");
//		};
//	}
//
//	/**
//	 * Hàm tạo ngày sinh ngẫu nhiên trong khoảng từ năm startYear đến endYear.
//	 */
//	private LocalDate generateRandomDate(int startYear, int endYear) {
//		long minDay = LocalDate.of(startYear, 1, 1).toEpochDay();
//		long maxDay = LocalDate.of(endYear, 12, 31).toEpochDay();
//		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
//		return LocalDate.ofEpochDay(randomDay);
//	}
//
//	private String generateRandomPhoneNumber() {
//		Random rnd = new Random();
//		String[] prefixes = {"090", "091", "092", "093", "094", "095", "096", "097", "098", "099", "080", "081", "082", "083", "084", "085", "086", "087", "088", "089"};
//		String prefix = prefixes[rnd.nextInt(prefixes.length)];
//		StringBuilder phoneNumber = new StringBuilder(prefix);
//		for (int i = 0; i < 7; i++) {
//			phoneNumber.append(rnd.nextInt(10));
//		}
//		return phoneNumber.toString();
//	}
}
