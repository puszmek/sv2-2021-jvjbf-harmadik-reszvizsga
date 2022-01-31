package vehiclerental;

import java.time.LocalTime;

public class Car implements Rentable {

    private String idCar;
    private LocalTime rentingTimeCar;
    private int pricePerMinute;

    public Car(String idCar, int pricePerMinute) {
        this.idCar = idCar;
        this.pricePerMinute = pricePerMinute;
    }

    @Override
    public int calculateSumPrice(long minutes) {
        return (int) (pricePerMinute * minutes);
    }

    @Override
    public LocalTime getRentingTime() {
        return rentingTimeCar;
    }

    @Override
    public void rent(LocalTime time) {
        rentingTimeCar = time;
    }

    @Override
    public void closeRent() {
        rentingTimeCar = null;
    }

    public String getIdCar() {
        return idCar;
    }

    public LocalTime getRentingTimeCar() {
        return rentingTimeCar;
    }

    public int getPricePerMinute() {
        return pricePerMinute;
    }
}
