package vehiclerental;

import java.time.LocalTime;

public class Bike implements Rentable {

    private String id;
    private LocalTime rentingTime;
    private static final int PRICE_PER_MINUTE = 15;

    public Bike(String id) {
        this.id = id;
    }

    @Override
    public int calculateSumPrice(long minutes) {
        return (int) (PRICE_PER_MINUTE * minutes);
    }

    @Override
    public LocalTime getRentingTime() {
        return rentingTime;
    }

    @Override
    public void rent(LocalTime time) {
        rentingTime = time;
    }

    @Override
    public void closeRent() {
        rentingTime = null;
    }
}
