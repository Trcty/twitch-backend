package com.example.twitch.hello;

import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public Person sayHello(@RequestParam(required = false) String locale) {
// if required =true, then locale has to be provided
        if (locale == null) {
            locale = "en_US";
        }
        // generate fake info
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName();
        String company = faker.company().name();
        String street = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String bookTitle = faker.book().title();
        String bookAuthor = faker.book().author();
        return new Person(name, company, new Address(street, city, state, null), new Book(bookTitle, bookAuthor));

    }
@GetMapping("/yes")
    public String sayyes(){
        return "say yes";
    }
}
