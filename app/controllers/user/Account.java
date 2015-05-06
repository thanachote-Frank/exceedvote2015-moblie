package controllers.user;

import forms.Login;
import forms.Register;
import models.Setting;
import models.UserType;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.user.login;
import views.html.user.register;


/**
 * Created by thanachote on 14/4/2558.
 */
public class Account extends Controller {


    public static synchronized Result login() {
        if (request().method().equals("GET")) {
            if (!controllers.admin.Setup.checkSystem()) {
                Logger.error("NOT ALLOW ACCESS TO LOGIN PAGE");
                return badRequest();
            }
            Logger.info("ACCESS TO LOGIN PAGE");
            return ok(login.render(Form.form(Login.class),
                    Setting.find.byId(Setting.CREATE_ACCOUNT).isActivated,
                    Setting.find.byId(Setting.CREATE_TEAM).isActivated));
        }
        else if (request().method().equals("POST")) {
            Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
            if (loginForm.hasErrors()) {
                Logger.info("LOGIN FAIL");
                return badRequest(login.render(loginForm,
                        Setting.find.byId(Setting.CREATE_ACCOUNT).isActivated,
                        Setting.find.byId(Setting.CREATE_TEAM).isActivated));
            } else {
                Logger.info(loginForm.name() + "LOGIN SUCCESS");
                session().clear();
                session("email", loginForm.get().email);
                if(models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
                    return redirect(controllers.admin.routes.Menu.menu());
                }else return redirect(routes.Menu.mainMenu());
            }
        }
        return badRequest();
    }

    public static synchronized Result regis() {
        if (Setting.find.byId(Setting.CREATE_ACCOUNT).isActivated) {
            if (request().method().equals("GET")) {
                Logger.info("ACCESS TO REGISTER PAGE");
                return ok(register.render(Form.form(Register.class), models.Team.getAll(), UserType.getAll()));
            } else if (request().method().equals("POST")) {
                Form<Register> registerForm = Form.form(Register.class).bindFromRequest();
                if (registerForm.hasErrors()) {
                    Logger.error("REGISTER FAIL");
                    return badRequest(register.render(registerForm, models.Team.getAll(), UserType.getAll()));
                } else {
                    if (registerForm.get().team == null){
                        models.Account account = new models.Account(registerForm.get().name, registerForm.get().lastname, registerForm.get().email,
                                registerForm.get().password,
                                null, UserType.findType(registerForm.get().type));
                        account.save();
                    }
                    else {
                        models.Account account = new models.Account(registerForm.get().name, registerForm.get().lastname, registerForm.get().email,
                                registerForm.get().password,
                                models.Team.find.byId(registerForm.get().team), UserType.findType(registerForm.get().type));
                        account.save();
                    }
                    Logger.info("REGISTER SUCCESS");
                    return redirect(routes.Account.login());
                }
            }
        }
        return badRequest("Disable this function by admin");
    }

    public static Result logout() {
        Logger.info(session("email") + " LOGOUT");
        session().clear();
        return redirect(routes.Account.login());
    }
}
