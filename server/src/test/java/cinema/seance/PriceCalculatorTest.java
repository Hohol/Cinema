package cinema.seance;

import cinema.auth.User;
import cinema.hall.*;
import cinema.movie.Movie;
import com.google.common.collect.*;
import org.junit.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PriceCalculatorTest {

    LocalDateTime now = LocalDateTime.of(2018, 7, 18, 14, 0); // wednesday
    LocalDate today = now.toLocalDate();
    LocalDate sunday = LocalDate.of(2018, 7, 22);
    PriceCalculator priceCalculator = new PriceCalculator(Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneId.of("UTC")));

    @Test
    public void birthday() {
        int price = priceCalculator.calculateFixedPrice(
                seance(today, 100),
                user(today.minus(20, ChronoUnit.YEARS))
        );
        int expected = 50;
        Assert.assertEquals(expected, price);
    }

    @Test
    public void child() {
        int price = priceCalculator.calculateFixedPrice(
                seance(today, 200),
                user(today.minusYears(12).minusDays(3))
        );
        Assert.assertEquals(150, price);
    }

    @Test
    public void childAndBirthday() {
        int price = priceCalculator.calculateFixedPrice(
                seance(today, 100),
                user(today.minusYears(12))
        );
        // discounts are applied additively
        Assert.assertEquals(25, price);
    }

    @Test
    public void weekend() {
        int price = priceCalculator.calculateFixedPrice(
                seance(sunday, 100),
                null
        );
        Assert.assertEquals(130, price);
    }

    @Test
    public void birthdayAndWeekend() {
        int price = priceCalculator.calculateFixedPrice(
                seance(sunday, 100),
                user(today.minusYears(20))
        );
        // birthday discount is applied AFTER weekend factor
        Assert.assertEquals(65, price);
    }

    @Test
    public void vip() {
        List<Integer> prices = priceCalculator.calculateSelectedPositionsPrice(
                seance(
                        today,
                        100,
                        hall(0.5, pos(1, 1))
                ),
                null,
                0,
                ImmutableList.of(pos(1, 1), pos(1, 2))
        );
        Assert.assertEquals(ImmutableList.of(150, 100), prices);
    }

    @Test
    public void vipAndWeekend() {
        List<Integer> prices = priceCalculator.calculateSelectedPositionsPrice(
                seance(
                        sunday,
                        100,
                        hall(0.5, pos(1, 1))
                ),
                null,
                0,
                ImmutableList.of(pos(1, 1), pos(1, 2))
        );
        // factors are applied additively
        Assert.assertEquals(ImmutableList.of(180, 130), prices);
    }

    @Test
    public void vipAndBirthday() {
        List<Integer> prices = priceCalculator.calculateSelectedPositionsPrice(
                seance(
                        today,
                        100,
                        hall(0.5, pos(1, 1))
                ),
                user(today.minusYears(20)),
                0,
                ImmutableList.of(pos(1, 1))
        );
        // birthday discount is applied AFTER vip factor
        Assert.assertEquals(ImmutableList.of(75), prices);
    }

    @Test
    public void sixthTicketIsFree() {
        List<Integer> prices = priceCalculator.calculateSelectedPositionsPrice(
                seance(
                        today,
                        100,
                        hall(0.5)
                ),
                null,
                0,
                ImmutableList.of(
                        pos(1, 1),
                        pos(1, 2),
                        pos(1, 3),
                        pos(1, 4),
                        pos(1, 5),
                        pos(1, 6)
                )
        );
        ImmutableList<Integer> expected = ImmutableList.of(100, 100, 100, 100, 100, 0);
        Assert.assertEquals(expected, prices);
    }

    @Test
    public void every10thTicketIsFree() {
        List<Integer> prices = priceCalculator.calculateSelectedPositionsPrice(
                seance(
                        today,
                        100,
                        hall(0.5)
                ),
                user(today.minusYears(20).minusDays(3)),
                8,
                ImmutableList.of(
                        pos(1, 1),
                        pos(1, 2),
                        pos(1, 3)
                )
        );
        Assert.assertEquals(ImmutableList.of(100, 0, 100), prices);
    }

    private Seance seance(LocalDate date, int basePrice) {
        return seance(date, basePrice, null);
    }

    private Seance seance(LocalDate date, int basePrice, Hall hall) {
        return new Seance(
                new Movie("", 0, basePrice),
                hall,
                date.atTime(20, 0).toInstant(ZoneOffset.UTC)
        );
    }

    private User user(LocalDate birthday) {
        return new User(null, null, birthday, User.Role.USER);
    }

    private Hall hall(double vipFactor, Position... vipPositions) {
        return new Hall("", vipFactor, 5, 10, Arrays.asList(vipPositions));
    }

    private Position pos(int row, int col) {
        return new Position(row, col);
    }
}