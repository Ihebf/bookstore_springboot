package com.vermeg.bookstore;

import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.entities.User;
import com.vermeg.bookstore.repository.BookRepository;
import com.vermeg.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        for(int i=0;i<50;i++)
            bookRepository.save(new Book(null,"title"+i,"author"+i,i,"release date"+i));
        User user = new User();
        user.setUsername("iheb");
        user.setRoles("USER");
        user.setPassword(passwordEncoder.encode("123"));

        userRepository.save(user);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }
}
