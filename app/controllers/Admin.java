package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import forms.AddCriteria;
import models.Criteria;
import models.Screenshot;
import models.Setting;
import models.Team;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

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
            (new Setting(new Long(1), "upload logo", true)).save();
            (new Setting(new Long(2), "upload screenshot", true)).save();
            (new Setting(new Long(3), "edit description", true)).save();
            (new Setting(new Long(4), "team list", true)).save();
            (new Setting(new Long(5), "team description", true)).save();
            (new Setting(new Long(6), "rating", true)).save();
            (new Setting(new Long(7), "create team", true)).save();
            (new Setting(new Long(8), "create account", true)).save();
            return ok();
        }
        return badRequest();
    }

//    public static Result criteria(){
//        if (request().method().equals("GET")){
//            return ok(admin_criteria.render());
//        }
//        else if (request().method().equals("POST")){
//
//            return ok();
//        }
//        return badRequest();
//    }

    public static Result addCriteria(){
        if (request().method().equals("GET")){
            return ok(admin_add_criteria.render(Form.form(AddCriteria.class)));
        }
        else if (request().method().equals("POST")){
            Form<AddCriteria> form = Form.form(AddCriteria.class).bindFromRequest();
            if (form.hasErrors()){
                return ok(form.globalError().message());
            }
            Criteria criteria = new Criteria(form.get().name.toLowerCase());
            criteria.save();
            return ok("Success");
        }
        return badRequest();
    }

    public static Result listCriteria(){
        if (request().method().equals("GET")){
            return ok(admin_list_criteria.render(Criteria.getall()));
        }
        return badRequest();
    }

    public static Result deleteCriteria(){
        if (request().method().equals("GET")){
            return ok(admin_add_criteria.render(Form.form(AddCriteria.class)));
        }
        else if (request().method().equals("POST")){
            Form<AddCriteria> form = Form.form(AddCriteria.class).bindFromRequest();
            if (form.hasErrors()){
                return ok(form.globalError().message());
            }
            Criteria criteria = new Criteria(form.get().name.toLowerCase());
            criteria.save();
            return ok("Success");
        }
        return badRequest();
    }

    public static Result listTeam() {
        if (request().method().equals("GET")){
            return ok(admin_list_team.render(Team.getAll()));
        }
        return badRequest();
    }

    public static Result deleteTeam(){

        if (request().method().equals("GET")){
            return ok(admin_delete_team.render(Team.getAll()));
        }
        else if(request().method().equals("POST")) {
            String ID = request().body().asFormUrlEncoded().get("id")[0];
            Team team = Team.find.byId(Long.parseLong(ID));
            if(team != null){
                team.delete();
                ObjectNode result = Json.newObject();
                result.put("type", "success");
                result.put("text", "Deleted");
                return ok(result);
            }
            ObjectNode result = Json.newObject();
            result.put("type", "danger");
            result.put("text", "Delete Fail");
            return ok(result);
        }
        return badRequest();
    }
}
