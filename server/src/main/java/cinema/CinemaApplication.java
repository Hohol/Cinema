package cinema;

import cinema.auth.*;
import cinema.hall.*;
import cinema.movie.*;
import cinema.seance.*;
import com.google.common.collect.ImmutableList;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.*;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class CinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Bean
    ApplicationRunner init(
            MovieRepository movieRepo,
            SeanceRepository seanceRepo,
            UserRepository userRepo,
            HallRepository hallRepo
    ) {
        return args -> {
            Movie movie1 = movieRepo.save(new Movie("Побег из Шоушенка", 120, 300));
            Movie movie2 = movieRepo.save(new Movie("Зеленая миля", 135, 260));
            Movie movie3 = movieRepo.save(new Movie("Матрица", 105, 350));

            Hall hall1 = hallRepo.save(new Hall(
                    "hall1", 1.5,
                    5, 10,
                    ImmutableList.of(
                            new Position(3, 3),
                            new Position(3, 4)
                    )
            ));

            Hall hall2 = hallRepo.save(new Hall(
                    "hall2", 1.2,
                    6, 15,
                    ImmutableList.of(
                            new Position(3, 3),
                            new Position(3, 4),
                            new Position(3, 5),
                            new Position(3, 6)
                    )
            ));

            Seance seance1 = seanceRepo.save(new Seance(movie1, hall1, Instant.now().plus(3, ChronoUnit.DAYS)));
            Seance seance2 = seanceRepo.save(new Seance(movie2, hall2, Instant.now().plus(3, ChronoUnit.HOURS)));

            User user = userRepo.save(new User(
                    "nikita",
                    "admin",
                    LocalDate.of(1991, 1, 2),
                    User.Role.ADMIN
            ));
            User old = userRepo.save(new User(
                    "old",
                    "oldpassword",
                    LocalDate.of(1980, 5, 5),
                    User.Role.USER
            ));
            User young = userRepo.save(new User(
                    "young",
                    "youngpassword",
                    LocalDate.of(2010, 6, 6),
                    User.Role.USER
            ));
            LocalDate today = LocalDate.now();
            User bDay = userRepo.save(new User(
                    "bday",
                    "bdaypassword",
                    LocalDate.of(1990, today.getMonth(), today.getDayOfMonth()),
                    User.Role.USER
            ));
        };
    }
}
