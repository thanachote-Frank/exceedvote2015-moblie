package forms;

import models.Account;

/**
 * Created by thanachote on 28/2/2558.
 */
public class Register {
    public String name;
    public String lastname;
    public String email;
    public String password;
    public String repassword;
    public Long team;

    public String validate() {
        String temp = "";
        if (Account.findEmail(email) != null) {
            temp += "This email was used already. ";
        }
        if (email == null) {
            temp += "Fill email. ";
        }
        if (name == null) {
            temp += "Fill name. ";
        }
        if (lastname == null) {
            temp += "Fill email. ";
        }
        if (password == null) {
            temp += "Fill password. ";
        }
        else if (password == repassword) {
            temp += "The passwords aren't same.";
        }
        if (temp.equals("")) {
            return null;
        }
        return temp;
    }
}
