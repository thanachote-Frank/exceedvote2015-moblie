package controllers.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import models.Account;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user.vote;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Frank on 4/18/2015 AD.
 */
public class Vote extends Controller{

    @Security.Authenticated(Secured.class)
    public static Result vote(){
        if (Setting.find.byId(Setting.VOTE).isActivated) {
            if (request().method().equals("GET")) {
                return ok(vote.render(Catalog.getAllAndOrder(), models.Team.getAllAndOrder()));
            } else if (request().method().equals("POST")) {
                List<Catalog> catalogs = Catalog.getall();
                List<models.Team> teams = new LinkedList<>();
                catalogs.forEach(catalog -> {
                    String teamID = request().body().asFormUrlEncoded().get("catalog_" + catalog.Id)[0];
                    if (!teamID.equals("-1")) {
                        teams.add(models.Team.getByID(Long.parseLong(teamID)));
                    } else {
                        teams.add(null);
                    }
                });
                if (teams.size() != catalogs.size()) {
                    ObjectNode result = Json.newObject();
                    result.put("type", "danger");
                    result.put("text", "Fail");
                    return ok(result);
                }
                Iterator<Catalog> catalogIterator = catalogs.iterator();
                Account account = Account.findEmail(session("email"));
                teams.forEach(team -> {
                    (new models.Vote(account, catalogIterator.next(), team)).save();
                });
                ObjectNode result = Json.newObject();
                result.put("type", "success");
                result.put("text", "Saved");
                return ok(result);

            }
        }
        return badRequest("Disable this function by admin");
    }
}
