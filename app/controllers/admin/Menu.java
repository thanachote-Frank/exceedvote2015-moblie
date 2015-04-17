package controllers.admin;

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
        if (request().method().equals("GET"))
            return ok(menu.render());
        return badRequest();

    }
}
