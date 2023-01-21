package es.pablouser1.umacraft.commands;

import es.pablouser1.umacraft.helpers.Database;
import es.pablouser1.umacraft.helpers.Mail;
import es.pablouser1.umacraft.helpers.Misc;

public class RegisterCommand implements ICommand {
    final private Database db;
    final private Mail mail;
    public RegisterCommand(Database db, Mail mail) {
        this.db = db;
        this.mail = mail;
    }
    public String run(String username, String[] args) {
        if (args.length == 0) {
            return "¡Tienes que escribir tu NIU!";
        }

        String niu = args[0];
        String code = Misc.randomString(12);
        boolean sent = mail.sendCode(niu, code);
        if (!sent) {
            return "Hubo un problema al enviar el correo electrónico";
        }

        String res;

        boolean valid = this.db.addVerify(niu, code);
        if (valid) {
            res = "Comprueba tu correo";
        } else {
            res = "Error al registrar";
        }

        return res;
    }
}
