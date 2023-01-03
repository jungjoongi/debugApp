package com.weolbu.admin;

import com.weolbu.admin.domain.auth.Role;
import com.weolbu.admin.domain.auth.User;
import com.weolbu.admin.domain.auth.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest(properties = "jasypt.encryptor.password:")
class DebugAppApplicationTests {

    @Autowired
    UserRepository userRepository;
    @Test
    public void login() {

        BCryptPasswordEncoder pw = new BCryptPasswordEncoder();
        userRepository.save(
                User.builder()
                        .email("")
                        .name("")
                        .password(pw.encode(""))
                        .role(Role.ADMIN)
                        .build()
        );


    }

/*

	@Autowired
	PhoneRentRepository phoneRentRepository;

	@Test
	public void insert() {

		PhoneRent p1 = PhoneRent.builder()
				.employeeNumber(1111)
				.modelName("model1")
				.employeeName("테스트").build();
		PhoneRent p2 = PhoneRent.builder()
				.employeeNumber(2222)
				.modelName("model1")
				.employeeName("테스트2").build();

		phoneRentRepository.save(p1);
		phoneRentRepository.save(p2);

		List<PhoneRent> phoneRentList1 = phoneRentRepository.findAllByEmployeeNameAndEmployeeNumber(p1.getEmployeeName(), p1.getEmployeeNumber());
		List<PhoneRent> phoneRentList2 = phoneRentRepository.findAllByEmployeeNameAndEmployeeNumber(p2.getEmployeeName(), p2.getEmployeeNumber());
		//Assert.notEmpty(phoneRentList);
		phoneRentList1.forEach(e -> System.out.println(e));
		System.out.println("@@");
		phoneRentList2.forEach(e -> System.out.println(e));
	}

	@Test
	public void remove() {
		PhoneRent p1 = PhoneRent.builder()
				.employeeNumber(1111)
				.modelName("model1")
				.employeeName("테스트").build();
		PhoneRent p2 = PhoneRent.builder()
				.employeeNumber(2222)
				.modelName("model1")
				.employeeName("테스트2").build();

		List<PhoneRent> phoneRentList1 = phoneRentRepository.findAllByEmployeeNameAndEmployeeNumber(p1.getEmployeeName(), p1.getEmployeeNumber());
		List<PhoneRent> phoneRentList2 = phoneRentRepository.findAllByEmployeeNameAndEmployeeNumber(p2.getEmployeeName(), p2.getEmployeeNumber());

		phoneRentList1.forEach(e -> phoneRentRepository.delete(e));
		phoneRentList2.forEach(e -> phoneRentRepository.delete(e));

		System.out.println("##");
		phoneRentRepository.findAll().forEach(e -> System.out.println(e));

	}
*/



}
