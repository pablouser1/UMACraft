package es.pablouser1.umacraft.helpers;

import es.pablouser1.umacraft.Umacraft;
import es.pablouser1.umacraft.models.EmailConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private File dbFile;
    private FileConfiguration client;
    public Config(Umacraft plugin) {
        plugin.saveDefaultConfig();
        this.dbFile = new File(plugin.getDataFolder(), "users.db");
        if (!this.dbFile.exists()) {
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

    public EmailConfig getEmailConfig() {
        String host = this.client.getString("email.host", "localhost");
        String username = this.client.getString("email.username", "wikiuma@example.com");
        String password = this.client.getString("email.password", "");
        return new EmailConfig(host, username, password);
    }
}
