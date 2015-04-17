package controllers.admin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.edit_user_type;
import views.html.admin.list_criteria;

/**
 * Created by thanachote on 14/4/2558.
 */
public class UserType extends Controller{
    @Security.Authenticated(Secured.class)
    public static Result edit(){
        if (request().method().equals("GET")){
            return ok(edit_user_type.render(models.UserType.getAllAndOrder()));
        }
        else if (request().method().equals("POST")) {
            String ID = request().body().asFormUrlEncoded().get("id")[0];
            String weight = request().body().asFormUrlEncoded().get("weight")[0];
            models.UserType userType = models.UserType.find.byId(Long.parseLong(ID));
            userType.setWeight(Integer.parseInt(weight));
            userType.update();
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Saved");
            return ok(result);
        }
        return badRequest();
    }


}
