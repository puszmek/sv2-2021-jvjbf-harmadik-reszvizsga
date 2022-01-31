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
        if (!users.contains(user.getUserName())) {     // Ha egy felhasználnév már foglalt
            throw new UserNameIsAlreadyTakenException("Username is taken!");
        }
        users.add(user);
    }

    public void addRentable(Rentable rentable) {
        rentables.add(rentable);
    }

    public void rent(User user, Rentable rentable, LocalTime time) {
        if (users.contains(user) || rentables.contains(rentable) || user.getBalance() < rentable.calculateSumPrice(3*60) || rentable.getRentingTime() != null) {
            throw new IllegalStateException("Something is wrong!");
        }
        rentable.rent(time);
        actualRenting.put(rentable, user);
    }

    public void closeRent(Rentable rentable, int minutes) {
        if (!actualRenting.containsKey(rentable)) {
            throw new IllegalArgumentException("Cannot found!");
        }
        User user = actualRenting.remove(rentable);
        rentable.closeRent();
        user.minusBalance(rentable.calculateSumPrice(minutes));
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
