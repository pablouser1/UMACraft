package es.pablouser1.umacraft.helpers;

import java.sql.*;

public class Database {
    private Connection conn;
    private Hashing hashing;
    public Database(String uri) {
        this.hashing = new Hashing();
        try {
            this.conn = DriverManager.getConnection(uri);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getUser(String username, String plainPassword) {
        boolean valid = false;
        try {
            PreparedStatement st = this.conn.prepareStatement("SELECT id, username, password FROM users WHERE username=? LIMIT 1");
            st.setString(1, username);
            ResultSet res = st.executeQuery();
            if (res.next()) {
                String checkPassword = res.getString("password");
                valid = this.hashing.authenticate(plainPassword.toCharArray(), checkPassword);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valid;
    }

    public boolean register(String username, String plainPassword) {
        try {
            PreparedStatement st = this.conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            st.setString(1, username);
            st.setString(2, this.hashing.hash(plainPassword.toCharArray()));
            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void close() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
