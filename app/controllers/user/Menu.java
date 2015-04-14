package controllers.user;

import models.Setting;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user.main_menu;

/**
 * Created by thanachote on 14/4/2558.
 */
public class Menu extends Controller{
    @Security.Authenticated(Secured.class)
    public static Result mainMenu() {
        return ok(main_menu.render(session().get("email"), session().get("team"),
                Setting.find.byId(Setting.TEAM_LIST).isActivated,
                Setting.find.byId(Setting.EDIT_DESCRIPTION).isActivated));
    }
}
