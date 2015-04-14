package controllers.user;

import forms.Login;
import forms.Register;
import models.Setting;
import models.UserType;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.user.login;
import views.html.user.register;
/**
 * Created by thanachote on 14/4/2558.
 */
public class Account extends Controller {


    public static Result login() {
        if (request().method().equals("GET")) {
            if (!controllers.admin.Setup.checkSystem())
                return badRequest();
            return ok(login.render(Form.form(Login.class),
                    Setting.find.byId(Setting.CREATE_ACCOUNT).isActivated,
                    Setting.find.byId(Setting.CREATE_TEAM).isActivated));
        }
        else if (request().method().equals("POST")) {
            Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
            if (loginForm.hasErrors()) {
                return badRequest(login.render(loginForm,
                        Setting.find.byId(Setting.CREATE_ACCOUNT).isActivated,
                        Setting.find.byId(Setting.CREATE_TEAM).isActivated));
            } else {
                session().clear();
                models.Account account = models.Account.findEmail(loginForm.get().email);
                session("email", account.email);
                session("team", account.team.name);
                return redirect(routes.Menu.mainMenu());
            }
        }
        return badRequest();
    }

    public static Result regis() {
        if (Setting.find.byId(Setting.CREATE_ACCOUNT).isActivated) {
            if (request().method().equals("GET")) {
                return ok(register.render(Form.form(Register.class), models.Team.getAll(), UserType.getAll()));
            } else if (request().method().equals("POST")) {
                Form<Register> registerForm = Form.form(Register.class).bindFromRequest();
                if (registerForm.hasErrors()) {
                    return badRequest(register.render(registerForm, models.Team.getAll(), UserType.getAll()));
                } else {
                    models.Account account = new models.Account(registerForm.get().name, registerForm.get().lastname, registerForm.get().email,
                            registerForm.get().password,
                            models.Team.find.byId(registerForm.get().team), UserType.findType(registerForm.get().type));
                    account.save();
                    return redirect(routes.Account.login());
                }
            }
        }
        return badRequest("Disable this function by admin");
    }

    public static Result logout() {
        session().clear();
        return redirect(routes.Account.login());
    }
}
