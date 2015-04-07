package controllers;

import models.Screenshot;
import models.Setting;
import models.Team;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin_home;
import views.html.admin_setting;

import java.util.List;

/**
 * Created by Frank on 3/28/15 AD.
 */
public class Admin extends Controller {
    public final static long UPLOAD_LOGO = 0;
    public final static long UPLOAD_SCREENSHOT = 1;
    public final static long EDIT_DESCRIPTION = 2;
    public final static long TEAM_LIST = 3;
    public final static long TEAM_DESCRIPTION = 4;
    public final static long RATING = 5;


    public final static long CREATE_TEAM = 6;
    public final static long CREATE_ACCOUNT = 7;

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
        return badRequest();
    }

    public static Result home(){
        if (request().method().equals("GET"))
            return ok(admin_home.render());
        return badRequest();

    }

    public static Result setupFeatures(){
        if (request().method().equals("POST")){
            for(Setting setting:Setting.find.all()){
                setting.delete();
            }
            new Setting(new Long(0), "upload logo", true).save();
            new Setting(new Long(1), "upload screenshot", true).save();
            new Setting(new Long(2), "edit description", true).save();
            new Setting(new Long(3), "team list", true).save();
            new Setting(new Long(4), "team description", true).save();
            new Setting(new Long(5), "rating", true).save();
            new Setting(new Long(6), "create team", true).save();
            new Setting(new Long(7), "create account", true).save();
            return ok();


        }
        return badRequest();
    }



}
