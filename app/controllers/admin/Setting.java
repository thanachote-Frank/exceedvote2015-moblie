package controllers.admin;

import controllers.user.Secured;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.setting;


/**
 * Created by thanachote on 14/4/2558.
 */
public class Setting extends Controller{
    @Security.Authenticated(Secured.class)
    public static Result setting(){
        if (request().method().equals("GET"))
            return ok(setting.render(models.Setting.find.order("name").findList()));
        else if (request().method().equals("POST")){
            DynamicForm form = Form.form().bindFromRequest();
            form.data().forEach((key, value)->
            {
               models.Setting setting = models.Setting.find.byId(Long.parseLong(key));
               setting.setIsActivated(Boolean.parseBoolean(value));
            }
            );
            return ok();
        }
        return badRequest();
    }
}
