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
        if (email.equals("")) {
            temp += "Fill email. ";
        }
        if (Account.findEmail(email) != null) {
            temp += "This email was used already. ";
        }
        if (name.equals("")) {
            temp += "Fill name. ";
        }
        if (lastname.equals("")) {
            temp += "Fill email. ";
        }
        if (password.equals("")) {
            temp += "Fill password. ";
        }
        else if (password.equals(repassword)) {
            temp += "The passwords aren't same.";
        }
        if (temp.equals("")) {
            return null;
        }
        return temp;
    }
}
