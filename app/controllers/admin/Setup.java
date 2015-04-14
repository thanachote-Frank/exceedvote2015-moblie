package controllers.admin;

import models.*;
import models.Setting;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by thanachote on 14/4/2558.
 */
public class Setup extends Controller{

    public static Result setupFeatures(){
        if (request().method().equals("POST")){
            for(models.Setting setting: models.Setting.find.all()){
                setting.delete();
            }
            (new models.Setting(new Long(1), "upload logo", true)).save();
            (new models.Setting(new Long(2), "upload screenshot", true)).save();
            (new models.Setting(new Long(3), "edit description", true)).save();
            (new models.Setting(new Long(4), "team list", true)).save();
            (new models.Setting(new Long(5), "team description", true)).save();
            (new models.Setting(new Long(6), "rating", true)).save();
            (new models.Setting(new Long(7), "create team", true)).save();
            (new Setting(new Long(8), "create account", true)).save();
            return ok();
        }
        return badRequest();
    }
}
