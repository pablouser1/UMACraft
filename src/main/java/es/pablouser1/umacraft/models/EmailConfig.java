package es.pablouser1.umacraft.models;

public class EmailConfig {
    public String host;
    public int port;
    public String username;
    public String password;
    public String encryption;

    public EmailConfig(String host, int port, String username, String password, String encryption) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.encryption = encryption;
    }
}
