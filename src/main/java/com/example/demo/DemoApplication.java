package com.example.demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class DemoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		HeaderContentNegotiationStrategy strategy = new HeaderContentNegotiationStrategy() {
			@Override
			public List<MediaType> resolveMediaTypes(NativeWebRequest request) throws HttpMediaTypeNotAcceptableException {
				List<MediaType> mediaTypes = super.resolveMediaTypes(request);
				if (mediaTypes.contains(MediaType.TEXT_HTML)) {
					mediaTypes.remove(MediaType.ALL);
				}
				return mediaTypes;
			}
		};
		configurer.strategies(List.of(strategy));
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