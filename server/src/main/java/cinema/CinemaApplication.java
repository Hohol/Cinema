package cinema;

import cinema.auth.*;
import cinema.movie.*;
import cinema.seance.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.*;
import java.time.temporal.*;

@SpringBootApplication
public class CinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Bean
    ApplicationRunner init(MovieRepository movieRepo, SeanceRepository seanceRepo, UserRepository userRepo) {
        return args -> {
            Movie movie1 = movieRepo.save(new Movie("Побег из Шоушенка", 120, 300));
            Movie movie2 = movieRepo.save(new Movie("Зеленая миля", 135, 260));
            Movie movie3 = movieRepo.save(new Movie("Матрица", 105, 350));

            Seance seance1 = seanceRepo.save(new Seance(movie1, Instant.now().plus(3, ChronoUnit.DAYS)));
            Seance seance2 = seanceRepo.save(new Seance(movie2, Instant.now().plus(3, ChronoUnit.HOURS)));

            User user = userRepo.save(new User(
                    "nikita",
                    "admin",
                    LocalDate.of(1991, 1, 2),
                    User.Role.USER
            ));
        };
    }
}
