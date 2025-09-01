package es.pablouser1.umacraft.helpers;

import es.pablouser1.umacraft.Umacraft;
import es.pablouser1.umacraft.models.EmailConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private final File dbFile;
    private final FileConfiguration client;
    private final boolean dbExists;

    public Config(Umacraft plugin) {
        plugin.saveDefaultConfig();
        this.dbFile = new File(plugin.getDataFolder(), "users.db");
        this.dbExists = this.dbFile.exists();
        if (!this.dbExists) {
            this.dbFile.getParentFile().mkdirs();
            try {
                this.dbFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.client = plugin.getConfig();
    }

    public String getDbUri() {
        return this.client.getString("db", "jdbc:sqlite:" + this.dbFile.getAbsolutePath());
    }

    public boolean dbNeedsPopulate() {
        return !this.dbExists;
    }

    public EmailConfig getEmailConfig() {
        String host = this.client.getString("email.host", "localhost");
        int port = this.client.getInt("email.port", 1025);
        String username = this.client.getString("email.username", "wikiuma@example.com");
        String password = this.client.getString("email.password", "");
        String encryption = this.client.getString("email.encryption", "none");
        return new EmailConfig(host, port, username, password, encryption);
    }
}
