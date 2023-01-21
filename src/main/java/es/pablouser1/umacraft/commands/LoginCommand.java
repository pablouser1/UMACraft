package es.pablouser1.umacraft.commands;

import es.pablouser1.umacraft.helpers.Auth;
import es.pablouser1.umacraft.helpers.Database;

public class LoginCommand implements ICommand {
    final private Database db;
    final private Auth auth;
    public LoginCommand(Database db, Auth auth) {
        this.db = db;
        this.auth = auth;
    }

    public String run(String username, String[] args) {
        if (args.length == 0) {
            return "¡Tienes que poner tu contraseña!";
        }

        String res;
        String password = args[0];

        boolean valid = this.db.getUser(username, password);
        if (valid) {
           res = "Sesión iniciada con éxito";
           auth.add(username);
        } else {
            res = "No se ha podido iniciar sesión";
        }

        return res;
    }
}
