package cinema;

import cinema.auth.*;
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
    ApplicationRunner init(MovieRepository movieRepo, SeanceRepository seanceRepo, UserRepository userRepo) {
        return args -> {
            Movie movie1 = movieRepo.save(new Movie("Побег из Шоушенка", 120, 300));
            Movie movie2 = movieRepo.save(new Movie("Зеленая миля", 135, 260));
            Movie movie3 = movieRepo.save(new Movie("Матрица", 105, 350));

            Seance seance1 = seanceRepo.save(new Seance(movie1));
            Seance seance2 = seanceRepo.save(new Seance(movie2));

            User user = userRepo.save(new User("nikita", "admin", User.Role.USER));
        };
    }
}
