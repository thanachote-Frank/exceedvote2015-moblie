package controllers.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.user.Secured;
import forms.AddCriteria;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.add_criteria;
import views.html.admin.delete_criteria;
import views.html.admin.list_criteria;


/**
 * Created by thanachote on 14/4/2558.
 */
public class Criteria extends Controller{
    @Security.Authenticated(Secured.class)
    public static Result addCriteria(){
        if (request().method().equals("GET")){
            return ok(add_criteria.render(Form.form(AddCriteria.class)));
        }
        else if (request().method().equals("POST")){
            Form<AddCriteria> form = Form.form(AddCriteria.class).bindFromRequest();
            if (form.hasErrors()){
                ObjectNode result = Json.newObject();
                result.put("type", "danger");
                result.put("text", form.globalError().message());
                return ok(result);
            }
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
        if (request().method().equals("GET")){
            return ok(list_criteria.render(models.Criteria.getAllAndOrder()));
        }
        return badRequest();
    }
    @Security.Authenticated(Secured.class)
    public static Result deleteCriteria(){
        if (request().method().equals("GET")){
            return ok(delete_criteria.render(models.Criteria.getAllAndOrder()));
        }
        else if (request().method().equals("POST")){
//            Form<AddCriteria> form = Form.form(AddCriteria.class).bindFromRequest();
//            if (form.hasErrors()){
//                return ok(form.globalError().message());
//            }
//            Criteria criteria = new Criteria(form.get().name.toLowerCase());
//            criteria.save();
            String ID = request().body().asFormUrlEncoded().get("id")[0];
            models.Criteria criteria = models.Criteria.find.byId(Long.parseLong(ID));
            criteria.delete();
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Deleted");
            return ok(result);
        }
        return badRequest();
    }
    @Security.Authenticated(Secured.class)
    public static Result searchCriteria(){
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
}
