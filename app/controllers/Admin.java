package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
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
import scala.util.parsing.json.JSONObject$;
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
                ObjectNode result = Json.newObject();
                result.put("type", "danger");
                result.put("text", form.globalError().message());
                return ok(result);
            }
            Criteria criteria = new Criteria(form.get().name.toLowerCase());
            criteria.save();
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Success");
            return ok(result);
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
                ObjectNode result = Json.newObject();
                result.put("type", "danger");
                result.put("text", form.globalError().message());
                return ok(result);
            }
            Criteria criteria = new Criteria(form.get().name.toLowerCase());
            criteria.save();
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Success");
            return ok(result);
        }
        return badRequest();
    }
}
