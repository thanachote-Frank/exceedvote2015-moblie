package controllers;

//import forms.EditDescription;
import forms.*;
//import forms.Register;
import models.*;
import play.api.mvc.Session$;
import play.data.*;
import play.mvc.*;
import views.html.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.*;


public class Application extends Controller {
    public final static long UPLOAD_LOGO = 0;
    public final static long UPLOAD_SCREENSHOT = 1;
    public final static long EDIT_DESCRIPTION = 2;
    public final static long TEAM_LIST = 3;
    public final static long TEAM_DESCRIPTION = 4;
    public final static long RATING = 5;


    public final static long CREATE_TEAM = 6;
    public final static long CREATE_ACCOUNT = 7;
//    public static int U = 2;


    public static Result index() {
        return ok(index.render("Index"));
    }

    public static Result mainMenu() {
        return ok(main_menu.render(session().get("email"), session().get("team"),
                Setting.find.byId(TEAM_LIST).isActivated,
                Setting.find.byId(EDIT_DESCRIPTION).isActivated));
    }

    public static Result teamList() {
        if (Setting.find.byId(TEAM_LIST).isActivated)
            return ok(team_list.render(Team.getAll(), Setting.find.byId(TEAM_DESCRIPTION).isActivated));
        return badRequest("Disable this function by admin");
    }

    public static Result ratingResult() {
        List<Stuff> rankAll = new ArrayList();
        List<Criteria> cri = Criteria.getall();
        List<Team> team = Team.getAll();
        for(int i=0; i<cri.size(); i++) {
            HashMap<Team, Double> rankInCri = new HashMap<>();
            for(int j=0; j<team.size(); j++) {
                List<Rating> data = Rating.GetRatingSpecific(cri.get(i), team.get(j));
                double scoreAvg = 0;
                for (int k = 0; k<data.size(); k++) {
                    scoreAvg += data.get(k).rating;
                }
                scoreAvg = scoreAvg / data.size();
                rankInCri.put(team.get(j), scoreAvg);
            }
            Set<Map.Entry<Team, Double>> set = rankInCri.entrySet();
            List<Map.Entry<Team, Double>> list = new ArrayList<Map.Entry<Team, Double>>(set);
            Collections.sort( list, new Comparator<Map.Entry<Team, Double>>()
            {
                public int compare( Map.Entry<Team, Double> o1, Map.Entry<Team, Double> o2 )
                {
                    return (o2.getValue()).compareTo( o1.getValue() );
                }
            } );
            Stuff stuff = new Stuff(list, cri.get(i));
            rankAll.add(stuff);
        }
        return ok(rating_result.render(rankAll));
    }


    public static Result rating(String teamID ) {
            return ok(rating.render(Criteria.getall(),teamID));
    }
    public static Result ratingPost() {
        if (request().method().equals("POST")) {
            Team team = null;
            Account account = Account.findEmail(session().get("email"));
            Map<String, String[]> map = request().body().asFormUrlEncoded();
            Long id = Long.parseLong(map.get("uid")[0]);
            team = Team.find.byId(id);
            for(Rating rating: Rating.findByTeamAndAccount(id, account.id)){
                rating.delete();
            }
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                String key = entry.getKey();
                String[] value = entry.getValue();
                if (key.equals("uid")) break;
                Rating obj = new Rating(account, Criteria.find.byId(Long.parseLong(key)), Integer.parseInt(value[0]), team);
                obj.save();

            }
            return ok();
        }
        else return ok();
    }


    public static Result team(Long teamID) {
        if (Setting.find.byId(TEAM_DESCRIPTION).isActivated)
            return ok(team.render(Team.getDescription(teamID), Team.getAllMember(teamID), Screenshot.getURL(teamID), Setting.find.byId(RATING).isActivated));
        return badRequest("Disable this function by admin");
    }

    public static Result login() {
        if (request().method().equals("GET")) {
            return ok(login.render(Form.form(Login.class),
                    Setting.find.byId(CREATE_ACCOUNT).isActivated,
                    Setting.find.byId(CREATE_TEAM).isActivated));
        }
        else if (request().method().equals("POST")) {
            Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
            if (loginForm.hasErrors()) {
                return badRequest(login.render(loginForm,
                        Setting.find.byId(CREATE_ACCOUNT).isActivated,
                        Setting.find.byId(CREATE_TEAM).isActivated));
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
        return badRequest();
    }

    public static Result regis() {
        if (Setting.find.byId(CREATE_ACCOUNT).isActivated) {
            if (request().method().equals("GET")) {
                return ok(register.render(Form.form(Register.class), Team.getAll(), UserType.getAll()));
            } else if (request().method().equals("POST")) {
                Form<Register> registerForm = Form.form(Register.class).bindFromRequest();
                if (registerForm.hasErrors()) {
                    return badRequest(register.render(registerForm, Team.getAll(), UserType.getAll()));
                } else {
                    Account account = new Account(registerForm.get().name, registerForm.get().lastname, registerForm.get().email,
                            registerForm.get().password,
                            Team.find.byId(registerForm.get().team), UserType.findType(registerForm.get().type));
                    account.save();
                    return redirect(
                            routes.Application.login()
                    );
                }
            }
        }
        return badRequest("Disable this function by admin");
    }

    public static Result editDescription() {
        if (Setting.find.byId(EDIT_DESCRIPTION).isActivated) {
            if (request().method().equals("GET")) {
                return ok(edit_description.render(Form.form(EditDescription.class), Team.findTeam(session("email")).name,
                        Setting.find.byId(UPLOAD_LOGO).isActivated, Setting.find.byId(UPLOAD_SCREENSHOT).isActivated));
            } else if (request().method().equals("POST")) {
                Form<EditDescription> form = Form.form(EditDescription.class).bindFromRequest();
                if (form.hasErrors()) {
                    return badRequest(edit_description.render(form, Team.findTeam(session("email")).name,
                            Setting.find.byId(UPLOAD_LOGO).isActivated, Setting.find.byId(UPLOAD_SCREENSHOT).isActivated));
                } else {
                    Team.findTeam(session("email")).setDescription(form.get().content);
                    return redirect(
                            routes.Application.mainMenu()
                    );
                }
            }
        }
        return badRequest("Disable this function by admin");
    }

    public static Result createTeam() {
        if (Setting.find.byId(CREATE_TEAM).isActivated) {
            if (request().method().equals("GET")) {
                return ok(create_team.render(Form.form(CreateTeam.class)));
            } else if (request().method().equals("POST")) {
                Form<CreateTeam> form = Form.form(CreateTeam.class).bindFromRequest();
                if (form.hasErrors()) {
                    return badRequest(create_team.render(form));
                } else {
                    Team team = new Team(form.get().name, "" + routes.Assets.at("images/logo.png"));
                    team.save();
                    return redirect(
                            routes.Application.login()
                    );
                }
            }
        }
        return badRequest("Disable this function by admin");
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


    public static Result logout() {
        session().clear();
        return redirect(
                routes.Application.login());

    }


    public static Result uploadLogo() {
        if (Setting.find.byId(UPLOAD_LOGO).isActivated) {
            if (request().method().equals("GET")) {
                return ok(upload_logo.render(Form.form(UploadLogo.class), Team.findTeam(session("email")).name));
            } else if (request().method().equals("POST")) {
                Team team = Team.findTeam(session("email"));
                Form<UploadLogo> registerForm = Form.form(UploadLogo.class).bindFromRequest();
                team.setLogo(registerForm.get().url);
            }
            return ok();
        }
        return badRequest("Disable this function by admin");
    }

    public static Result uploadScreenshot() {
        if (Setting.find.byId(UPLOAD_SCREENSHOT).isActivated) {
            if (request().method().equals("GET")) {
                return ok(upload_screenshot.render(Form.form(UploadLogo.class), Team.findTeam(session("email")).name));
            } else if (request().method().equals("POST")) {
                Team team = Team.findTeam(session("email"));
                Form<UploadScreenshot> form = Form.form(UploadScreenshot.class).bindFromRequest();
                Screenshot screenshot = new Screenshot(team, form.get().url);
                screenshot.save();
            }
            return badRequest();
        }
        return badRequest("Disable this function by admin");
    }

    public static Result deleteAllScreenshot(){
        if (request().method().equals("POST")){
            Team team = Team.findTeam(session("email"));
            List<Screenshot> temp = Screenshot.getAll(team.id);
            for (Screenshot screenshot : temp) {
                screenshot.delete();
            }
        }
        return badRequest();
    }

}

