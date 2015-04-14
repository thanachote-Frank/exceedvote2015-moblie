package controllers.user;

import forms.UploadLogo;
import forms.UploadScreenshot;
import models.Screenshot;
import models.Setting;
import play.data.Form;
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
    public static Result uploadLogo() {
        if (Setting.find.byId(Setting.UPLOAD_LOGO).isActivated) {
            if (request().method().equals("GET")) {
                return ok(upload_logo.render(Form.form(UploadLogo.class), models.Team.findTeam(session("email")).name));
            } else if (request().method().equals("POST")) {
                models.Team team = models.Team.findTeam(session("email"));
                Form<UploadLogo> registerForm = Form.form(UploadLogo.class).bindFromRequest();
                team.setLogo(registerForm.get().url);
                return ok();
            }
        }
        return badRequest("Disable this function by admin");
    }

    @Security.Authenticated(Secured.class)
    public static Result uploadScreenshot() {
        if (Setting.find.byId(Setting.UPLOAD_SCREENSHOT).isActivated) {
            if (request().method().equals("GET")) {
                return ok(upload_screenshot.render(Form.form(UploadLogo.class), models.Team.findTeam(session("email")).name));
            } else if (request().method().equals("POST")) {
                models.Team team = models.Team.findTeam(session("email"));
                Form<UploadScreenshot> form = Form.form(UploadScreenshot.class).bindFromRequest();
                Screenshot screenshot = new Screenshot(team, form.get().url);
                screenshot.save();
            }
        }
        return badRequest("Disable this function by admin");
    }

    @Security.Authenticated(Secured.class)
    public static Result deleteAllScreenshot(){
        if (Setting.find.byId(Setting.UPLOAD_SCREENSHOT).isActivated) {
            if (request().method().equals("POST")) {
                models.Team team = models.Team.findTeam(session("email"));
                models.Screenshot.deleteAllByTeamID(team.id);
                return ok();
            }

        }
        return badRequest("Disable this function by admin");
    }
}
