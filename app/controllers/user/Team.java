package controllers.user;


import com.fasterxml.jackson.databind.node.ObjectNode;
import forms.*;
import models.*;
import models.Account;
import play.Logger;
import play.core.Router;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Html;
import views.html.user.create_team;
import views.html.user.edit_description;
import views.html.user.team;
import views.html.user.team_list;
import play.data.*;
import play.mvc.*;

import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLWriter;
import javax.xml.ws.spi.http.HttpContext;

/**
 * Created by thanachote on 14/4/2558.
 */
public class Team extends Controller{
    public static synchronized Result createTeam() {
        if (Setting.find.byId(Setting.CREATE_TEAM).isActivated){
            if (request().method().equals("GET")) {
                Logger.info("ACCESS TO CREATE TEAM PAGE");
                return ok(create_team.render(Form.form(CreateTeam.class)));
            } else if (request().method().equals("POST")){
                Form<CreateTeam> form = Form.form(CreateTeam.class).bindFromRequest();
                if (form.hasErrors()) {
                    Logger.info("CREATE TEAM FAIL");
                    return badRequest(create_team.render(form));
                } else {
                    Logger.info("CREATE TEAM: "+form.get().name);
                    models.Team team = new models.Team(form.get().name, "/assets/images/logo.png");
                    team.save();
                    return redirect(routes.Account.login());
                }
            }
        }
        return badRequest("Disable this function by admin");
    }

    @Security.Authenticated(Secured.class)
    public static Result teamList() {
        if (Setting.find.byId(Setting.TEAM_LIST).isActivated) {
            Logger.info(session("email") + " ACCESS TO TEAM LIST PAGE");
            return ok(team_list.render(models.Team.getAll(),
                    models.Rating.findRatedTeam(Account.findEmail(session().get("email")).id),
                    Setting.find.byId(Setting.TEAM_DESCRIPTION).isActivated));
        }
        return badRequest("Disable this function by admin");
    }

    @Security.Authenticated(Secured.class)
    public static synchronized Result editDescription() {
        if (Setting.find.byId(Setting.EDIT_DESCRIPTION).isActivated) {
            if (request().method().equals("GET")) {
                models.Team team = models.Team.findTeam(session("email"));
                if (team == null){
                    Logger.error(session("email") + " DON'T HAVE TEAM BUT TRY TO ACCESS TO EDIT DESCRIPTION PAGE");
                    return redirect(controllers.user.routes.Menu.mainMenu());
                }
                Logger.error(session("email") + " ACCESS TO EDIT DESCRIPTION PAGE OF " + team.name);
                return ok(edit_description.render(Form.form(EditDescription.class), team.name,
                        Setting.find.byId(Setting.UPLOAD_LOGO).isActivated,
                        Setting.find.byId(Setting.UPLOAD_SCREENSHOT).isActivated,
                        team));
            } else if (request().method().equals("POST")) {
                Form<EditDescription> form = Form.form(EditDescription.class).bindFromRequest();
                if (form.hasErrors()) {
                    Logger.error(session("email") + " SUBMIT VALUE TO EDIT DESCRIPTION PAGE: FAIL");
                    ObjectNode result = Json.newObject();
                    result.put("type", "danger");
                    result.put("text", "Fail");
                    return ok(result);
                } else {
                    Logger.error(session("email") + " SUBMIT VALUE TO EDIT DESCRIPTION PAGE: SUCCESS");
                    models.Team team = models.Team.findTeam(session("email"));
                    try {
                        team.setDescription(form.get().content);
                    }catch (Exception e){
                        ObjectNode result = Json.newObject();
                        result.put("type", "danger");
                        result.put("text", "Fail");
                        return ok(result);
                    }
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
    public static synchronized Result team(Long teamID) {
        if (Setting.find.byId(Setting.TEAM_DESCRIPTION).isActivated) {
            Logger.error(session("email") + " ACCESS TO EDIT DESCRIPTION PAGE OF TEAM_ID="+ teamID);
            models.Team team1 = models.Team.find.byId(teamID);
            String[] description = team1.description.replaceAll("\\r", "").split("\n");
            return ok(team.render(team1, models.Team.getSortedAllMember(teamID), Screenshot.getURL(teamID),
                    Setting.find.byId(Setting.RATING).isActivated, description));
        }
        return badRequest("Disable this function by admin");
    }
}
