package controllers.admin;

import models.*;
import models.Setting;
import models.TimeOut;
import org.joda.time.DateTime;
import play.mvc.Controller;
import play.mvc.Result;
import models.UserType;
import models.Setting;
/**
 * Created by thanachote on 14/4/2558.
 */
public class Setup extends Controller{
    private static boolean checkingSystem = false;

    public static Result setupFeatures(){
        if (request().method().equals("POST")){
            for(models.Setting setting: models.Setting.find.all()){
                setting.delete();
            }
            addFeatures();
            return ok();
        }
        return badRequest();
    }

    public static boolean checkSystem(){
        if (checkingSystem){
            return checkingSystem;
        }
        if (UserType.getAll().isEmpty()){
            addUserType();
        }
        if (Setting.find.all().isEmpty()){
            addFeatures();
        }
        if (TimeOut.getAll().isEmpty()){
            addTimeOut();
        }
        checkingSystem = true;
        return true;
    }


    private static void addFeatures(){
        (new models.Setting(new Long(1), "upload logo", true)).save();
        (new models.Setting(new Long(2), "upload screenshot", true)).save();
        (new models.Setting(new Long(3), "edit description", true)).save();
        (new models.Setting(new Long(4), "team list", true)).save();
        (new models.Setting(new Long(5), "team description", true)).save();
        (new models.Setting(new Long(6), "rating", true)).save();
        (new models.Setting(new Long(7), "create team", true)).save();
        (new Setting(new Long(8), "create account", true)).save();
    }

    private static void addUserType(){
        (new UserType("guest", 1)).save();
        (new UserType("participant", 1)).save();
        (new UserType("organizer", 1)).save();
    }

    private static void addTimeOut(){
        (new TimeOut(new DateTime())).save();
    }
}
