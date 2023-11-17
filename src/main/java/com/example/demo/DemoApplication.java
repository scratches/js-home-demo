package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@Controller
class HomeController {
	@GetMapping(value="{path:[^\\.]*}", produces="text/html")
	public String home() {
		return "forward:/";
	}
	
}

@RestController
class DataController {
	@GetMapping(value="/foo", produces="application/json")
	public Foo foo() {
		return new Foo("bar");
	}
	
}

class Foo {
	private String value;
	public Foo(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Foo{" +
			"value='" + value + "'" +
			"}";
	}
}