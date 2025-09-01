package es.pablouser1.umacraft;

import es.pablouser1.umacraft.helpers.Auth;
import es.pablouser1.umacraft.helpers.Config;
import es.pablouser1.umacraft.helpers.Database;
import es.pablouser1.umacraft.helpers.Mail;
import es.pablouser1.umacraft.listeners.AuthListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Umacraft extends JavaPlugin {
    private Auth auth;
    private Database db;

    @Override
    public void onEnable() {
        Config config = new Config(this);
        this.db = new Database(config.getDbUri(), config.dbNeedsPopulate());
        Mail mail = new Mail(config.getEmailConfig());
        this.auth = new Auth();
        // Listeners
        new AuthListener(this, this.auth);
        // Commands
        AuthCommands authCommands = new AuthCommands(this.db, mail, this.auth);
        Objects.requireNonNull(getCommand("login")).setExecutor(authCommands);
        Objects.requireNonNull(getCommand("register")).setExecutor(authCommands);
        Objects.requireNonNull(getCommand("verify")).setExecutor(authCommands);
        getLogger().info("UMACraft iniciado con éxito");
    }

    @Override
    public void onDisable() {
        this.auth.clear();
        this.db.close();
        getLogger().info("UMACraft detenido con éxito");
    }
}