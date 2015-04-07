package controllers;

import models.Screenshot;
import models.Setting;
import models.Team;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin_setting;

import java.util.List;

/**
 * Created by Frank on 3/28/15 AD.
 */
public class Admin extends Controller {

    public static Result setting(){
        if (request().method().equals("GET"))
            return ok(admin_setting.render(Setting.find.order("name").findList()));
        else if (request().method().equals("POST")){
            DynamicForm form = Form.form().bindFromRequest();
            form.data().forEach((key, value)->
            {
               Setting setting = Setting.find.byId(Long.parseLong(key));
               setting.setIsActivated(Boolean.parseBoolean(value));
            }
            );

            return ok();
        }
        return ok();
    }



}
