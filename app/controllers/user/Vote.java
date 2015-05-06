package controllers.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.Stuff;
import models.*;
import models.Account;
import models.Team;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user.vote;
import views.html.user.vote_result;

import java.util.*;

/**
 * Created by Frank on 4/18/2015 AD.
 */
public class Vote extends Controller{

    @Security.Authenticated(Secured.class)
    public static synchronized Result vote(){
        if (Setting.find.byId(Setting.VOTE).isActivated) {
            if (request().method().equals("GET")) {
                return ok(vote.render(Catalog.getAllAndOrder(), models.Team.getAllAndOrder()));
            } else if (request().method().equals("POST")) {
                List<Catalog> catalogs = Catalog.getAll();
                List<models.Team> teams = new LinkedList<>();
                try {
                    catalogs.forEach(catalog -> {
                        String teamID = request().body().asFormUrlEncoded().get("catalog_" + catalog.Id)[0];
                        if (!teamID.equals("-1")) {
                            teams.add(models.Team.getByID(Long.parseLong(teamID)));
                        } else {
                            teams.add(null);
                        }
                    });
                } catch (Exception e){
                    ObjectNode result = Json.newObject();
                    result.put("type", "danger");
                    result.put("text", "Fail");
                    return ok(result);
                }

                if (teams.size() != catalogs.size()) {
                    ObjectNode result = Json.newObject();
                    result.put("type", "danger");
                    result.put("text", "Fail");
                    return ok(result);
                }
                Iterator<Catalog> catalogIterator = catalogs.iterator();
                Account account = Account.findEmail(session("email"));
                models.Vote.findByAccount(account).forEach(vote -> {
                    vote.delete();
                });
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

    @Security.Authenticated(Secured.class)
    public static Result result() {
        if (Setting.find.byId(Setting.VOTE_RESULT).isActivated) {
            if (request().method().equals("GET")) {
                List<Stuff<Catalog,Team,Integer>> stuffs =new ArrayList<>();
                List<Catalog> catalogs = Catalog.getAll();
                List<Team> teams = Team.getAll();

                catalogs.forEach(catalog -> {
                    Map<Team,Integer> teamIntegerMap = new HashMap<Team, Integer>();
                    teams.forEach(team -> {
                        int amount = models.Vote.findByCatalogAndTeam(catalog,team).size();
                        teamIntegerMap.put(team, amount);
                    });
                    Set<Map.Entry<Team, Integer>> set = teamIntegerMap.entrySet();
                    List<Map.Entry<Team, Integer>> list = new ArrayList<Map.Entry<Team, Integer>>(set);
                    Collections.sort(list, new Comparator<Map.Entry<Team, Integer>>() {
                        public int compare(Map.Entry<Team, Integer> o1, Map.Entry<Team, Integer> o2) {
                            return (o2.getValue()).compareTo(o1.getValue());
                        }
                    });
                    stuffs.add(new Stuff<Catalog,Team,Integer>(list,catalog));
                });

                return ok(vote_result.render(stuffs));

            }
        }
        return badRequest("Disable this function by admin");
    }
}
