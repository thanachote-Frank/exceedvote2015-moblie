package controllers.admin;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.menu;


/**
 * Created by thanachote on 14/4/2558.
 */
public class Menu extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result menu(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("GET"))
            Logger.info(session("email") + " ADMIN MENU PAGE");
            return ok(menu.render());
        return badRequest();

    }
}
