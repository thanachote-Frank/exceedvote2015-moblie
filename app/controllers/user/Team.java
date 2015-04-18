package controllers.user;


import com.fasterxml.jackson.databind.node.ObjectNode;
import forms.*;
import models.*;
import models.Account;
import play.core.Router;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user.create_team;
import views.html.user.edit_description;
import views.html.user.team;
import views.html.user.team_list;
import play.data.*;
import play.mvc.*;

/**
 * Created by thanachote on 14/4/2558.
 */
public class Team extends Controller{
    public static Result createTeam() {
        if (request().method().equals("GET")) {
            return ok(create_team.render(Form.form(CreateTeam.class)));
        } else if (request().method().equals("POST")){
            Form<CreateTeam> form = Form.form(CreateTeam.class).bindFromRequest();
            if (form.hasErrors()) {
                return badRequest(create_team.render(form));
            } else {
                models.Team team = new models.Team(form.get().name, "/assets/images/logo.png");
                team.save();
                return redirect(routes.Account.login());
            }
        }
        return null;
    }

    @Security.Authenticated(Secured.class)
    public static Result teamList() {
        if (Setting.find.byId(Setting.TEAM_LIST).isActivated)
            return ok(team_list.render(models.Team.getAll(),
                    models.Rating.findRatedTeam(Account.findEmail(session().get("email")).id),
                    Setting.find.byId(Setting.TEAM_DESCRIPTION).isActivated));
        return badRequest("Disable this function by admin");
    }

    @Security.Authenticated(Secured.class)
    public static Result editDescription() {
        if (Setting.find.byId(Setting.EDIT_DESCRIPTION).isActivated) {
            if (request().method().equals("GET")) {
                models.Team team = models.Team.findTeam(session("email"));
                if (team == null){
                    return redirect(controllers.user.routes.Menu.mainMenu());
                }
                return ok(edit_description.render(Form.form(EditDescription.class), team.name,
                        Setting.find.byId(Setting.UPLOAD_LOGO).isActivated,
                        Setting.find.byId(Setting.UPLOAD_SCREENSHOT).isActivated,
                        models.Team.findTeam(session("email"))));
            } else if (request().method().equals("POST")) {
                Form<EditDescription> form = Form.form(EditDescription.class).bindFromRequest();
                if (form.hasErrors()) {
                    ObjectNode result = Json.newObject();
                    result.put("type", "danger");
                    result.put("text", "Fail");
                    return ok(result);
                } else {
                    models.Team team = models.Team.findTeam(session("email"));
                    team.setDescription(form.get().content);
                    ObjectNode result = Json.newObject();
                    result.put("type", "success");
                    result.put("text", "Saved");
                    return ok(result);
                }
            }
        }
        return badRequest("Disable this function by admin");
    }

    @Security.Authenticated(Secured.class)
    public static Result team(Long teamID) {
        if (Setting.find.byId(Setting.TEAM_DESCRIPTION).isActivated)
            return ok(team.render(models.Team.getDescription(teamID), models.Team.getAllMember(teamID), Screenshot.getURL(teamID), Setting.find.byId(Setting.RATING).isActivated));
        return badRequest("Disable this function by admin");
    }
}
