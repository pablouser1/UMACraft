package es.pablouser1.umacraft;

import es.pablouser1.umacraft.commands.AuthCommands;
import es.pablouser1.umacraft.helpers.Config;
import es.pablouser1.umacraft.helpers.Database;
import es.pablouser1.umacraft.helpers.Mail;
import es.pablouser1.umacraft.listeners.AuthListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Umacraft extends JavaPlugin {
    private AuthListener auth;
    private Config config;

    private Database db;
    private Mail mail;

    @Override
    public void onEnable() {
        this.config = new Config(this);
        this.db = new Database(this.config.getDbUri());
        this.mail = new Mail(this.config.getEmailConfig());

        // Listeners
        this.auth = new AuthListener(this);
        // Commands
        AuthCommands authCommands = new AuthCommands(this.db, this.mail);
        Objects.requireNonNull(getCommand("login")).setExecutor(authCommands);
        Objects.requireNonNull(getCommand("register")).setExecutor(authCommands);
        Objects.requireNonNull(getCommand("verify")).setExecutor(authCommands);
        getLogger().info("UMACraft iniciado con éxito");
    }

    @Override
    public void onDisable() {
        this.db.close();
        getLogger().info("UMACraft detenido con éxito");
    }
}