package controllers.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import forms.UploadLogo;
import forms.UploadScreenshot;
import models.Screenshot;
import models.Setting;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user.upload_logo;
import views.html.user.upload_screenshot;

import java.util.List;

/**
 * Created by thanachote on 14/4/2558.
 */
public class Upload extends Controller{
    @Security.Authenticated(Secured.class)
    public static synchronized Result uploadLogo() {
        if (Setting.find.byId(Setting.UPLOAD_LOGO).isActivated) {
            if (request().method().equals("GET")) {
                Logger.info(session("email") + " ACCESS TO UPLOAD LOGO PAGE");
                return ok(upload_logo.render(Form.form(UploadLogo.class), models.Team.findTeam(session("email")).name));
            } else if (request().method().equals("POST")) {
                try {
                    models.Team team = models.Team.findTeam(session("email"));
                    Form<UploadLogo> registerForm = Form.form(UploadLogo.class).bindFromRequest();
                    team.setLogo(registerForm.get().url);
                } catch (Exception e) {
                    Logger.error(session("email") + " SUBMIT VALUE TO UPLOAD LOGO PAGE: FAIL");
                    ObjectNode result = Json.newObject();
                    result.put("type", "danger");
                    result.put("text", "Fail");
                    return badRequest(result);
                }
                Logger.info(session("email") + " SUBMIT VALUE TO UPLOAD LOGO PAGE: SUCCESS");
                ObjectNode result = Json.newObject();
                result.put("type", "success");
                result.put("text", "Uploaded");
                return ok(result);
            }
        }
        return badRequest("Disable this function by admin");
    }

    @Security.Authenticated(Secured.class)
    public static synchronized Result uploadScreenshot() {
        if (Setting.find.byId(Setting.UPLOAD_SCREENSHOT).isActivated) {
            if (request().method().equals("GET")) {
                Logger.info(session("email") + " ACCESS TO UPLOAD SCREENSHOT PAGE");
                return ok(upload_screenshot.render(Form.form(UploadLogo.class), models.Team.findTeam(session("email")).name));
            } else if (request().method().equals("POST")) {
                try {
                    models.Team team = models.Team.findTeam(session("email"));
                    Form<UploadScreenshot> form = Form.form(UploadScreenshot.class).bindFromRequest();
                    Screenshot screenshot = new Screenshot(team, form.get().url);
                    screenshot.save();
                }catch (Exception e) {
                    Logger.error(session("email") + " SUBMIT VALUE TO UPLOAD LOGO PAGE: FAIL");
                    ObjectNode result = Json.newObject();
                    result.put("type", "danger");
                    result.put("text", "Fail");
                    return badRequest(result);
            }
                Logger.info(session("email") + " SUBMIT VALUE TO UPLOAD LOGO PAGE: SUCCESS");
                ObjectNode result = Json.newObject();
                result.put("type", "success");
                result.put("text", "Uploaded");
                return ok(result);
            }
        }
        return badRequest("Disable this function by admin");
    }

    @Security.Authenticated(Secured.class)
    public static synchronized Result deleteAllScreenshot(){
        if (Setting.find.byId(Setting.UPLOAD_SCREENSHOT).isActivated) {
            if (request().method().equals("POST")) {
                try {
                    models.Team team = models.Team.findTeam(session("email"));
                    models.Screenshot.deleteAllByTeamID(team.id);
                } catch (Exception e){
                    ObjectNode result = Json.newObject();
                    result.put("type", "danger");
                    result.put("text", "Fail");
                    Logger.error(session("email") + " DELETE ALL SCREENSHOTS: FAIL");
                    return badRequest(result);
                }
                Logger.error(session("email") + " DELETE ALL SCREENSHOTS: SUCCESS");
                ObjectNode result = Json.newObject();
                result.put("type", "success");
                result.put("text", "Deleted");
                return ok(result);
            }

        }
        return badRequest("Disable this function by admin");
    }
}
