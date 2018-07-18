package cinema.seance;

import cinema.auth.User;
import cinema.hall.*;
import cinema.movie.Movie;
import com.google.common.collect.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Test
public class PriceCalculatorTest {

    LocalDateTime now = LocalDateTime.of(2018, 7, 18, 14, 0); // wednesday
    LocalDate today = now.toLocalDate();
    LocalDate sunday = LocalDate.of(2018, 7, 22);
    PriceCalculator priceCalculator = new PriceCalculator(Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneId.of("UTC")));

    @Test
    void birthday() {
        int price = priceCalculator.calculateFixedPrice(
                seance(today, 100),
                user(today.minus(20, ChronoUnit.YEARS))
        );
        Assert.assertEquals(price, 50);
    }

    @Test
    void child() {
        int price = priceCalculator.calculateFixedPrice(
                seance(today, 200),
                user(today.minusYears(12).minusDays(3))
        );
        Assert.assertEquals(price, 150);
    }

    @Test
    void childAndBirthday() {
        int price = priceCalculator.calculateFixedPrice(
                seance(today, 100),
                user(today.minusYears(12))
        );
        // discounts are applied additively
        Assert.assertEquals(price, 25);
    }

    @Test
    void weekend() {
        int price = priceCalculator.calculateFixedPrice(
                seance(sunday, 100),
                null
        );
        Assert.assertEquals(price, 130);
    }

    @Test
    void birthdayAndWeekend() {
        int price = priceCalculator.calculateFixedPrice(
                seance(sunday, 100),
                user(today.minusYears(20))
        );
        // birthday discount is applied AFTER weekend factor
        Assert.assertEquals(price, 65);
    }

    @Test
    void vip() {
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
        Assert.assertEquals(prices, ImmutableList.of(150, 100));
    }

    @Test
    void vipAndWeekend() {
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
        Assert.assertEquals(prices, ImmutableList.of(180, 130));
    }

    @Test
    void vipAndBirthday() {
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
        Assert.assertEquals(prices, ImmutableList.of(75));
    }

    @Test
    void sixthTicketIsFree() {
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
        Assert.assertEquals(prices, ImmutableList.of(100, 100, 100, 100, 100, 0));
    }

    @Test
    void every10thTicketIsFree() {
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
        Assert.assertEquals(prices, ImmutableList.of(100, 0, 100));
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