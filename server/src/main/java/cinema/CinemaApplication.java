package cinema;

import cinema.movie.*;
import cinema.seance.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Bean
    ApplicationRunner init(MovieRepository repository, SeanceRepository seanceRepository) {
        return args -> {
            Movie movie1 = repository.save(new Movie("Побег из Шоушенка", 120, 300));
            Movie movie2 = repository.save(new Movie("Зеленая миля", 135, 260));
            Movie movie3 = repository.save(new Movie("Матрица", 105, 350));

            Seance seance1 = seanceRepository.save(new Seance(movie1));
            Seance seance2 = seanceRepository.save(new Seance(movie2));
        };
    }
}
