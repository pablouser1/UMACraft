package es.pablouser1.umacraft;

import es.pablouser1.umacraft.commands.ICommand;
import es.pablouser1.umacraft.commands.LoginCommand;
import es.pablouser1.umacraft.commands.RegisterCommand;
import es.pablouser1.umacraft.commands.VerifyCommand;
import es.pablouser1.umacraft.helpers.Auth;
import es.pablouser1.umacraft.helpers.Database;
import es.pablouser1.umacraft.helpers.Mail;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AuthCommands implements CommandExecutor {
    final private Database db;
    final private Mail mail;
    final private Auth auth;

    public AuthCommands(Database db, Mail mail, Auth auth) {
        super();
        this.db = db;
        this.mail = mail;
        this.auth = auth;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        String name = command.getName();

        ICommand cmd;

        if (name.equalsIgnoreCase("login")) {
            // Login
            cmd = new LoginCommand(this.db, this.auth);
        } else if (name.equalsIgnoreCase("register")) {
            // Register
            cmd = new RegisterCommand(this.db, this.mail);
        } else {
            // Verify
            cmd = new VerifyCommand(this.db, this.auth);
        }

        String msg = cmd.run(sender.getName(), args);
        sender.sendMessage(msg);
        return true;
    }
}
