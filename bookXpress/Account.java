package bookXpress;
import java.util.ArrayList;
import java.util.List;

public class Account {
    String username;
    String password;
    double balance;
    int points;
    List<String> inventory;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.points = 0;
        this.inventory = new ArrayList<>();
    }
}
