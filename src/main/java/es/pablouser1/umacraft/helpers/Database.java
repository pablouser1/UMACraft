package es.pablouser1.umacraft.helpers;

import es.pablouser1.umacraft.models.Verify;

import java.sql.*;

public class Database {
    final private Connection conn;
    final private Hashing hashing;
    public Database(String uri) {
        this.hashing = new Hashing();
        try {
            this.conn = DriverManager.getConnection(uri);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getUser(String username, String plainPassword) {
        boolean valid = true;
        try {
            PreparedStatement st = this.conn.prepareStatement("SELECT id, username, password FROM users WHERE username=? LIMIT 1");
            st.setString(1, username);
            ResultSet res = st.executeQuery();
            if (res.next()) {
                String checkPassword = res.getString("password");
                valid = this.hashing.authenticate(plainPassword.toCharArray(), checkPassword);
            }
        } catch (SQLException e) {
            valid = false;
        }
        return valid;
    }

    public boolean addVerify(String niu, String code) {
        boolean success = true;
        try {
            PreparedStatement st = this.conn.prepareStatement("INSERT INTO `verification` (niu, code) VALUES (?, ?)");
            st.setString(1, niu);
            st.setString(2, code);
            st.execute();
        } catch (SQLException e) {
            success = false;
        }
        return success;
    }

    public Verify getVerify(String code) {
        Verify data;
        try {
            PreparedStatement st = this.conn.prepareStatement("SELECT id, niu FROM `verification` WHERE code=? LIMIT 1");
            st.setString(1, code);
            ResultSet res = st.executeQuery();
            if (res.next()) {
                int id = res.getInt("id");
                String niu = res.getString("niu");
                data = new Verify(id, niu, code);
            } else {
                data = null;
            }
        } catch (SQLException e) {
            data = null;
        }

        return data;
    }

    public boolean deleteVerify(int id) {
        boolean success = true;
        try {
            PreparedStatement st = this.conn.prepareStatement("DELETE FROM `verification` WHERE id=?");
            st.setInt(1, id);
            st.execute();
        } catch (SQLException e) {
            success = false;
        }
        return success;
    }

    public boolean addUser(String username, String password, String niu) {
        boolean success = true;
        try {
            PreparedStatement st = this.conn.prepareStatement("INSERT INTO `users` (username, password, niu) VALUES (?, ?, ?)");
            st.setString(1, username);
            st.setString(2, this.hashing.hash(password.toCharArray()));
            st.setString(3, niu);
            st.execute();
        } catch (SQLException e) {
            success = false;
        }
        return success;
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
