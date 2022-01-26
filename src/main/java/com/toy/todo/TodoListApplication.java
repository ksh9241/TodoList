package com.toy.todo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}
	
	// 스프링 부트 애플리케이션 구동 시점에 특정 코드 실행시키기 위한 2가지 인터페이스
//	@Bean
//	public CommandLineRunner commandLineRunner (ApplicationContext context) {
//		return args -> {
//			System.out.println("Let's inspect the beans provided by Spring boot");
//			
//			String[] beanNames = context.getBeanDefinitionNames();
//			Arrays.parallelSort(beanNames);
//			
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//		};
//	}
}
