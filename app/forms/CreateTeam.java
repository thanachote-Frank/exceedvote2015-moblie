package forms;

import models.Account;
import play.mvc.Controller;

/**
 * Created by Frank on 3/4/15 AD.
 */
public class CreateTeam {
    public String name;

    public String validate() {
        if (name.equals("")) {
            return "Fill team name";
        }
        return null;
    }
}
