package controllers.admin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.delete_team;
import views.html.admin.list_team;


/**
 * Created by thanachote on 14/4/2558.
 */
public class Team extends Controller{


    public static Result listTeam() {
        if (request().method().equals("GET")){
            return ok(list_team.render(models.Team.getAll()));
        }
        return badRequest();
    }

    public static Result deleteTeam(){

        if (request().method().equals("GET")){
            return ok(delete_team.render(models.Team.getAll()));
        }
        else if(request().method().equals("POST")) {
            String ID = request().body().asFormUrlEncoded().get("id")[0];
            models.Team team = models.Team.find.byId(Long.parseLong(ID));
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
