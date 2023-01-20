package es.pablouser1.umacraft.models;

public class EmailConfig {
    public String host;
    public String username;
    public String password;

    public EmailConfig(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }
}
