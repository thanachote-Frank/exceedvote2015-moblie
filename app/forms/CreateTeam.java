package forms;

import models.Account;
import play.mvc.Controller;
import models.Team;

/**
 * Created by Frank on 3/4/15 AD.
 */
public class CreateTeam {
    public String name;

    public String validate() {
        if (name.equals("")) {
            return "Fill team name.";
        }
        if (Team.find.where().eq("name", name.toLowerCase()).findRowCount() > 0) {
            return "Have this team in the system.";
        }
        return null;
    }
}
