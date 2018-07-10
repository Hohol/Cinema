package cinema;

import cinema.movie.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class CinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Bean
    ApplicationRunner init(MovieRepository repository) {
        return args -> {
            Stream.of(
                    new Movie("Побег из Шоушенка", 120, 300),
                    new Movie("Зеленая миля", 135, 260),
                    new Movie("Матрица", 105, 350)
            ).forEach(repository::save);
            repository.findAll().forEach(System.out::println);
        };
    }
}
