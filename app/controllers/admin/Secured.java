package controllers.admin;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import controllers.user.Account;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(controllers.user.routes.Account.login());
    }


}