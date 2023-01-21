package es.pablouser1.umacraft.helpers;

import java.util.ArrayList;

public class Auth {
    final private ArrayList<String> logged;

    public Auth() {
        this.logged = new ArrayList<>();
    }

    public void add(String username) {
        if (!this.logged.contains(username)) {
            this.logged.add(username);
        }
    }

    public void delete(String username) {
        this.logged.remove(username);
    }

    public boolean exists(String username) {
        return this.logged.contains(username);
    }

    public void clear() {
        this.logged.clear();
    }
}
