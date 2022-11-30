package com.weolbu.admin;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DebugAppApplicationTests {
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
