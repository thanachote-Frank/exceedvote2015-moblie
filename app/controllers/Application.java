package controllers;

//import forms.EditDescription;
import forms.*;
//import forms.Register;
import models.Account;
import models.Team;
import play.api.mvc.Session$;
import play.data.*;
import play.mvc.*;
import views.html.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Index"));
    }

    public static Result mainMenu() {
        return ok(main_menu.render(session().get("email"), session().get("team")));
    }

    public static Result teamList() {
        return ok(team_list.render(Team.getAll()));
    }

    public static Result voting() {
        return ok(Vote.render());
    }

    public static Result team(Long teamID) {
        return ok(team.render(Team.getDescription(teamID), Team.getAllMember(teamID)));
    }

    public static Result login() {
        return ok(login.render(Form.form(Login.class)));
    }

    public static Result regis() {
        return ok(register.render(Form.form(Register.class), Team.getAll()));
    }

    public static Result editDescription() {
        if (request().method().equals("GET")) {
            return ok(edit_description.render(Form.form(EditDescription.class), Team.findTeam(session("email")).name));
        } else if (request().method().equals("POST")) {
            Form<EditDescription> form = Form.form(EditDescription.class).bindFromRequest();
            if (form.hasErrors()) {
                return badRequest(edit_description.render(form, Team.findTeam(session("email")).name));
            } else {
                Team.findTeam(session("email")).setDescription(form.get().content);
                return redirect(
                        routes.Application.mainMenu()
                );
            }
        }
        return null;
    }

    public static Result createTeam() {
        if (request().method().equals("GET")) {
            return ok(create_team.render(Form.form(CreateTeam.class)));
        } else if (request().method().equals("POST")){
            Form<CreateTeam> form = Form.form(CreateTeam.class).bindFromRequest();
            if (form.hasErrors()) {
                return badRequest(create_team.render(form));
            } else {
                Team team = new Team(form.get().name);
                team.save();
                return redirect(
                        routes.Application.login()
                );
            }
        }
        return null;
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
            Account account = Account.findEmail(loginForm.get().email);
            session("email", account.email);
            session("team", account.team.name);
            return redirect(
                    routes.Application.mainMenu()
            );
        }
    }

    public static Result logout() {
        session().clear();
        return redirect(
                routes.Application.login());

    }

    public static Result enroll() {
        Form<Register> registerForm = Form.form(Register.class).bindFromRequest();
        if (registerForm.hasErrors()) {
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

    public static Result uploadLogo() {
        if (request().method().equals("GET")){
            return ok(upload_logo.render(Form.form(UploadLogo.class), Team.findTeam(session("email")).name));
        }
        else if (request().method().equals("POST")){
            /app/public/pic-cloud
//            File file = new File("/home/dokku/exceedvote2015-moblie/pic-cloud/taest.txt");
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart picture = body.getFile("file");
            if (picture != null) {
                Team team = Team.findTeam(session("email"));
                if (!team.logo.equals("")){
                    new File("public/" + team.logo).delete();
                }
                String fileName = picture.getFilename();
                String contentType = picture.getContentType();
                File file = picture.getFile();
                System.out.print(file.renameTo(new File("/app/public/pic-cloud/" + fileName)));
                Team.findTeam(session("email")).setLogo("pic-cloud/" + fileName);
                return redirect(routes.Application.mainMenu());
            } else {
                flash("error", "Missing file");
                return redirect(routes.Application.uploadLogo());
            }
        }
        return ok();
    }

}

