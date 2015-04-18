package controllers.admin;

import models.*;
import models.Account;
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
        if (UserType.find.all().size() != 4){
            models.UserType.deleteAll();
            addUserType();
        }
        if (Setting.find.all().size() != 10){
            models.Setting.deleteAll();
            addFeatures();
        }
        if (TimeOut.getAll().isEmpty()){
            addTimeOut();
        }
        if (Account.find.where().eq("type",UserType.findType("Admin")).findList().isEmpty()){
            defaultAdmin();
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
        (new models.Setting(new Long(9), "rating result", false)).save();
        (new models.Setting(new Long(10), "vote", true)).save();
    }
    private static void defaultAdmin(){
        (new Account("Admin","Midwars","admin@exceed.cf","1234",null,UserType.findType("Admin"))).save();
    }

    private static void addUserType(){
        (new UserType("Admin", 0)).save();
        (new UserType("guest", 1)).save();
        (new UserType("participant", 1)).save();
        (new UserType("organizer", 1)).save();
    }

    private static void addTimeOut(){
        (new TimeOut((new DateTime()).withSecondOfMinute(0).withMillisOfSecond(0))).save();
    }
}
