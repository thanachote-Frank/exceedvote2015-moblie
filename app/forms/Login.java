package forms;

import models.Account;
import play.data.*;
import play.mvc.*;
/**
 * Created by Frank on 2/26/15 AD.
 */
public class Login {
    public String username;
    public String password;

    public String validate() {
        if (Account.authenticate(username, password) == null) {
            return "Invalid user or password";
        }
        return null;
    }
}
