package forms;

import models.Account;
import models.Team;
import play.mvc.Controller;

/**
 * Created by thanachote on 1/3/2558.
 */
public class EditDescription {
    public String content;

    public String validate() {
        if (Account.findEmail(Controller.session("email")).team.name.equals("No Team")) {
            return "You are no team";
        }
        return null;
    }

}
