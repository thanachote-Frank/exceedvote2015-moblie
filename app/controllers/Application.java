package controllers;

import forms.Login;
import models.Team;
import play.data.*;
import play.mvc.*;
import views.html.*;


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

