package controllers;

import forms.Login;
import forms.Register;
import models.Account;
import models.Team;
import play.data.*;
import play.mvc.*;
import views.html.*;

import java.util.HashMap;
import java.util.Map;


public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Index"));
    }

    public static Result mainMenu() {
        return ok(main_menu.render(session().get("email")));
    }

    public static Result teamList() {
        return ok(team_list.render(Team.getAll()));
    }

    public static Result team(Long teamID) {
        return ok(team.render(Team.getDescription(teamID), Team.getAllMember(teamID)));
    }

    public static Result login() {
        return ok(login.render(Form.form(Login.class)));
//        return ok();
    }

    public static Result regis() {
        return ok(register.render(Form.form(Register.class), Team.getAll()));
//        return ok();
    }

//    public static Result checkLogin() {
//        // map data from HTTP request to an object.
//        DynamicForm form = Form.form().bindFromRequest();
//        System.out.println(Account.find.where().eq("username", form.get("username")).eq("password", form.get("password")).findUnique());
//        if (Account.find.where().eq("username", form.get("username")).eq("password", form.get("password")).findUnique() != null) {
//            return redirect("/main_menu");
//        }
//
//        return redirect("/login");
//    }

    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                    routes.Application.mainMenu()
            );
        }
    }

    public static Result enroll() {
        Form<Register> registerForm = Form.form(Register.class).bindFromRequest();
        if (registerForm.hasGlobalErrors()) {
            return badRequest(register.render(registerForm, Team.getAll()));
        } else {
            Account account = new Account(registerForm.get().name, registerForm.get().lastname, registerForm.get().email,
                    registerForm.get().password,
                    Team.find.byId(registerForm.get().team));
            account.save();
            return redirect(
                    routes.Application.login()
            );
        }
    }
//    public static class Login {
//        public String username;
//        public String password;
//
//        public String validate() {
//            if (Account.authenticate(username, password) == null) {
//                return "Invalid user or password";
//            }
//            return null;
//        }
//    }
}

