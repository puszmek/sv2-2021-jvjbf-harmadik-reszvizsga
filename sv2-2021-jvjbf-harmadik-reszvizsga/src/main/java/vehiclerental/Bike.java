package vehiclerental;

import java.time.LocalTime;

public class Bike implements Rentable {

    private String idBike;
    private LocalTime rentingTimeBike;
    private static final int PRICE_PER_MINUTE = 15;

    public Bike(String idBike) {
        this.idBike = idBike;
    }

    @Override
    public int calculateSumPrice(long minutes) {
        return (int) (PRICE_PER_MINUTE * minutes);
    }

    @Override
    public LocalTime getRentingTime() {
        return rentingTimeBike;
    }

    @Override
    public void rent(LocalTime time) {
        rentingTimeBike = time;
    }

    @Override
    public void closeRent() {
        rentingTimeBike = null;
    }

    public String getIdBike() {
        return idBike;
    }

    public LocalTime getRentingTimeBike() {
        return rentingTimeBike;
    }
}
