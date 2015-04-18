package controllers.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.user.Secured;
import forms.AddCriteria;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.add_criteria;
import views.html.admin.delete_criteria;
import views.html.admin.edit_criteria;
import views.html.admin.list_criteria;


/**
 * Created by thanachote on 14/4/2558.
 */
public class Criteria extends Controller{
    @Security.Authenticated(Secured.class)
    public static Result addCriteria(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("GET")){
            Logger.info(session("email") + " ADD CRITERIA PAGE");
            return ok(add_criteria.render(Form.form(AddCriteria.class)));
        }
        else if (request().method().equals("POST")){
            Form<AddCriteria> form = Form.form(AddCriteria.class).bindFromRequest();
            if (form.hasErrors()){
                ObjectNode result = Json.newObject();
                result.put("type", "danger");
                result.put("text", form.globalError().message());
                Logger.error(session("email") + " ADD NEW CRITERIA=" + form.get().name + ": FAIL");
                return ok(result);
            }
            Logger.info(session("email") + " ADD NEW CRITERIA="+form.get().name);
            models.Criteria criteria = new models.Criteria(form.get().name.toLowerCase());
            criteria.save();
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Success");
            return ok(result);
        }
        return badRequest();
    }
    @Security.Authenticated(Secured.class)
    public static Result listCriteria(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("GET")){
            Logger.info(session("email") + " LIST CRITERIA PAGE");
            return ok(list_criteria.render(models.Criteria.getAllAndOrder()));
        }
        return badRequest();
    }
    @Security.Authenticated(Secured.class)
    public static Result deleteCriteria(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("GET")){
            Logger.info(session("email") + " DELETE CRITERIA PAGE");
            return ok(delete_criteria.render(models.Criteria.getAllAndOrder()));
        }
        else if (request().method().equals("POST")){
            String ID = request().body().asFormUrlEncoded().get("id")[0];
            models.Criteria criteria = models.Criteria.find.byId(Long.parseLong(ID));
            if(criteria==null){
                ObjectNode result = Json.newObject();
                result.put("type", "danger");
                result.put("text", "Fail");
                Logger.error(session("email") + " DELETE A CRITERIA=" + criteria.name + ": FAIL");
                return ok(result);
            }
            criteria.delete();
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Deleted");
            Logger.info(session("email") + " DELETE A CRITERIA="+criteria.name);
            return ok(result);
        }
        return badRequest();
    }
    @Security.Authenticated(Secured.class)
    public static Result searchCriteria(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("POST")){
            String name = request().body().asFormUrlEncoded().get("input")[0];
            ObjectNode result = Json.newObject();
            ArrayNode node = result.putArray("data");
            models.Criteria.findByName(name).forEach(criteria -> {
                ObjectNode node1 = node.addObject();
                node1.put("id", criteria.Id);
                node1.put("name", criteria.name);
            });
            return ok(result);
        }
        return badRequest();
    }

    @Security.Authenticated(Secured.class)
    public static Result edit(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("GET")){
            Logger.info(session("email") + " EDIT CRITERIA PAGE");
            return ok(edit_criteria.render(models.Criteria.getAllAndOrder()));
        }
        if (request().method().equals("POST")){
            String ID = request().body().asFormUrlEncoded().get("id")[0];
            String name = request().body().asFormUrlEncoded().get("name")[0];
            models.Criteria criteria = models.Criteria.find.byId(Long.parseLong(ID));
            if (criteria == null){
                ObjectNode result = Json.newObject();
                result.put("type", "danger");
                result.put("text", "Fail");
                Logger.error(session("email") + " EDIT CRITERIA=" + criteria.name + ": FAIL");
                return ok(result);
            }
            criteria.name = name;
            criteria.update();
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Edited");
            Logger.info(session("email") + " EDIT CRITERIA=" + criteria.name);
            return ok(result);
        }
        return badRequest();
    }

}
