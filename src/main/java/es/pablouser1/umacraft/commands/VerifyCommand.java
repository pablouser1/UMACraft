package es.pablouser1.umacraft.commands;

import es.pablouser1.umacraft.helpers.Auth;
import es.pablouser1.umacraft.helpers.Database;
import es.pablouser1.umacraft.models.Verify;

public class VerifyCommand implements ICommand {
    final private Database db;
    final private Auth auth;

    public VerifyCommand(Database db, Auth auth) {
        this.db = db;
        this.auth = auth;
    }
    public String run(String username, String[] args) {
        if (args.length < 2) {
            return "Tienes que enviar tu código y contraseña";
        }

        String code = args[0];
        Verify verify = this.db.getVerify(code);

        if (verify == null) {
            return "PIN inválido";
        }

        String res;
        String password = args[1];
        boolean created = this.db.addUser(username, password, verify.niu);
        if (created) {
            boolean deleted = this.db.deleteVerify(verify.id);
            if (!deleted) {
                res = "Usuario agregado con éxito, aunque no se ha podido eliminar el código";
            } else {
                res = "Usuario agregado con éxito";
            }
            this.auth.add(username);
        } else {
            res = "Error al enviar usuario";
        }
        return res;
    }
}
