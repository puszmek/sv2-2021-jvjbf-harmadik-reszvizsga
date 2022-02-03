package vehiclerental;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class RentService {

    Set<User> users = new HashSet<>();
    Set<Rentable> rentables = new HashSet<>();
    Map<Rentable, User> actualRenting = new TreeMap<>();    // az aktuális rendelések mindig időrendben legyenek tárolva

    public void registerUser(User user) {
        if (isUserNameTaken(user.getUserName())) {     // Ha egy felhasználnév már foglalt
            throw new UserNameIsAlreadyTakenException("Username is taken!");
        }
        users.add(user);
    }

    private boolean isUserNameTaken(String name) {
        return users.stream()
                .map(user -> user.getUserName())
                .anyMatch(s -> s.equals(name));
    }

    public void addRentable(Rentable rentable) {
        rentables.add(rentable);
    }

    public void rent(User user, Rentable rentable, LocalTime time) {
        if (rentable.getRentingTime() != null || user.getBalance() < rentable.calculateSumPrice(3*60)) {
            throw new IllegalStateException("Rentable is taken or low money!");
        }
        rentable.rent(time);
        actualRenting.put(rentable, user);
    }

    public void closeRent(Rentable rentable, int minutes) {
        if (!actualRenting.containsKey(rentable)) {
            throw new IllegalArgumentException("Rentable is not taken!");
        }
        User user = actualRenting.get(rentable);
        user.minusBalance(rentable.calculateSumPrice(minutes));
        actualRenting.remove(rentable);
        rentable.closeRent();
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Rentable> getRentables() {
        return rentables;
    }

    public Map<Rentable, User> getActualRenting() {
        return actualRenting;
    }
}
