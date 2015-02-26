package forms;

import models.Account;
import play.data.*;
import play.mvc.*;
/**
 * Created by Frank on 2/26/15 AD.
 */
public class Login {
    public String email;
    public String password;

    public String validate() {
        if (Account.authenticate(email, password) == null) {
            return "Invalid email or password";
        }
        return null;
    }
}
