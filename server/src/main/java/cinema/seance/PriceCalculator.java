package cinema.seance;

import cinema.auth.User;
import cinema.hall.*;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PriceCalculator {

    // factors meaning: newPrice = oldPrice * (1 + factor)
    private static final double WEEKEND_FACTOR = 0.3;
    private static final double BIRTHDAY_FACTOR = -0.5;
    private static final double CHILD_FACTOR = -0.25;

    private final Random rnd = new Random();
    private final Clock clock;

    public PriceCalculator() {
        clock = Clock.systemDefaultZone();
    }

    public PriceCalculator(Clock clock) {
        this.clock = clock;
    }

    /**
     * The price that is displayed in seance list.
     * Only fixed discounts are applied
     */
    public int calculateFixedPrice(Seance seance, User user) {
        double price = seance.getMovie().getBaseTicketPrice();
        if (seanceAtWeekend(seance)) {
            price += price * WEEKEND_FACTOR;
        }
        price = applyFixedDiscounts(price, seance, user);
        return (int) Math.round(price);
    }

    private double applyFixedDiscounts(double basePrice, Seance seance, User user) {
        if (user == null) {
            return basePrice;
        }
        double factor = 1;
        LocalDate birthday = user.getBirthday();
        LocalDate today = LocalDate.now(clock);
        if (today.getDayOfYear() == birthday.getDayOfYear()) {
            factor += BIRTHDAY_FACTOR;
        }
        if (ChronoUnit.YEARS.between(birthday, today) < 14) {
            factor += CHILD_FACTOR;
        }
        return basePrice * factor;
    }

    /**
     * The price that is displayed when user selects positions.
     * All discounts except "10% chance of free ticket" are applied.
     *
     * @param boughtTicketsCnt number of tickets given user has bought earlier
     * @return list of ticket prices, order of items corresponds to {@code selectedPositions}
     */
    public List<Integer> calculateSelectedPositionsPrice(Seance seance, User user, int boughtTicketsCnt, List<Position> selectedPositions) {
        Hall hall = seance.getHall();
        boolean weekend = seanceAtWeekend(seance);
        List<Integer> prices = selectedPositions.stream()
                .map(p -> {
                    double factor = 1;
                    if (hall.vipPositions.contains(p)) {
                        factor += hall.getVipFactor();
                    }
                    if (weekend) {
                        factor += WEEKEND_FACTOR;
                    }
                    double price = seance.getMovie().getBaseTicketPrice() * factor;
                    price = applyFixedDiscounts(price, seance, user);
                    return (int) Math.round(price);
                })
                .collect(Collectors.toList());

        if (prices.size() >= 6) {
            prices.set(5, 0);
        }
        if (user != null) {
            for (int index = 0; index < prices.size(); index++) {
                if ((boughtTicketsCnt + index) % 10 == 9) {
                    prices.set(index, 0);
                }
            }
        }
        return prices;
    }

    /**
     * Same as {@code calculateSelectedPositionsPrice()}, but "10% chance of free ticket" discount is applied
     */
    public List<Integer> calculateFinalPrice(Seance seance, User user, int boughtTicketsCnt, List<Position> selectedPositions) {
        List<Integer> prices = calculateSelectedPositionsPrice(seance, user, boughtTicketsCnt, selectedPositions);
        for (int i = 0; i < prices.size(); i++) {
            if (rnd.nextDouble() < 0.1) {
                prices.set(i, 0);
            }
        }
        return prices;
    }

    private boolean seanceAtWeekend(Seance seance) {
        DayOfWeek seanceDay = seance.getStartTime()
                .atZone(clock.getZone())
                .toLocalDate()
                .getDayOfWeek();
        return seanceDay == DayOfWeek.SATURDAY || seanceDay == DayOfWeek.SUNDAY;
    }
}
