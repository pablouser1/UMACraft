package es.pablouser1.umacraft.commands;

import es.pablouser1.umacraft.helpers.Database;
import es.pablouser1.umacraft.helpers.Mail;
import es.pablouser1.umacraft.helpers.Misc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AuthCommands implements CommandExecutor {
    private Database db;
    private Mail mail;
    public AuthCommands(Database db, Mail mail) {
        super();
        this.db = db;
        this.mail = mail;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        String name = command.getName();

        if (name.equalsIgnoreCase("login")) {
            // Login
            if (args.length > 0) {
                String password = args[0];

                boolean valid = this.db.getUser(sender.getName(), password);
                if (valid) {
                    sender.sendMessage("Sesión iniciada con éxito");
                } else {
                    sender.sendMessage("No se ha podido iniciar sesión");
                }
            }
        } else if (name.equalsIgnoreCase("register")) {
            // Register
            String niu = args[0];
            String pin = Misc.randomString(6);
            boolean sent = mail.sendPin(niu, pin);
            if (!sent) {
                sender.sendMessage("Hubo un problema al enviar el correo electrónico");
                return true;
            }

            boolean valid = this.db.register(sender.getName(), niu);
            if (valid) {
                sender.sendMessage("Registrado");
            } else {
                sender.sendMessage("Error al registrar");
            }
        } else if (name.equalsIgnoreCase("verify")) {
            // Verificar
            // TODO
        }

        return true;
    }
}
