package controllers.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.Stuff;
import models.Account;
import models.Criteria;
import models.Setting;
import models.Team;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.delete_account;
import views.html.user.rating;
import views.html.user.rating_result;

import java.util.*;

/**
 * Created by thanachote on 14/4/2558.
 */
public class Rating extends Controller{
    @Security.Authenticated(Secured.class)
    public static Result ratingResult() {
        if (Setting.find.byId(Setting.RATING_RESULT).isActivated) {
            List<Stuff> rankAll = new ArrayList();
            List<Criteria> cri = Criteria.getall();
            List<Team> team = Team.getAll();
            double[] overAll = new double[team.size()];

            for (int i = 0; i < cri.size(); i++) {
                HashMap<Team, Double> rankInCri = new HashMap<>();
                for (int j = 0; j < team.size(); j++) {
                    List<models.Rating> data = models.Rating.getRatingSpecific(cri.get(i), team.get(j));
                    double scoreAvg = 0;
                    int weight = 0;
                    for (int k = 0; k < data.size(); k++) {
                        scoreAvg += data.get(k).rating*data.get(k).account.type.weight;
                        weight += data.get(k).account.type.weight;
                    }
                    scoreAvg = scoreAvg / weight;
                    if(weight==0)scoreAvg=0;//for non vote team will be not NaN
                    //-----------------------------------------------//
                    overAll[j] += scoreAvg;
                    //-----------------------------------------------//
                    rankInCri.put(team.get(j), scoreAvg);
                }
                Set<Map.Entry<Team, Double>> set = rankInCri.entrySet();
                List<Map.Entry<Team, Double>> list = new ArrayList<Map.Entry<Team, Double>>(set);
                Collections.sort(list, new Comparator<Map.Entry<Team, Double>>() {
                    public int compare(Map.Entry<Team, Double> o1, Map.Entry<Team, Double> o2) {
                        return (o2.getValue()).compareTo(o1.getValue());
                    }
                });
                Stuff stuff = new Stuff(list, cri.get(i));
                rankAll.add(stuff);
            }
            for (int i = 0; i < overAll.length; i++) {
                overAll[i] /= cri.size();
            }
            //-------------------------------------------------//
            HashMap<Team, Double> rankOverAll = new HashMap<>();
            for (int i = 0; i < team.size(); i++) {
                rankOverAll.put(team.get(i), overAll[i]);
            }
            Set<Map.Entry<Team, Double>> set = rankOverAll.entrySet();
            List<Map.Entry<Team, Double>> list = new ArrayList<Map.Entry<Team, Double>>(set);
            Collections.sort(list, new Comparator<Map.Entry<Team, Double>>() {
                public int compare(Map.Entry<Team, Double> o1, Map.Entry<Team, Double> o2) {
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });
            //-------------------------------------------------//
            return ok(rating_result.render(rankAll, list));
        }
        return badRequest("Disable this function by admin");
    }

    @Security.Authenticated(Secured.class)
    public static Result rating(String teamID ) {
            return ok(rating.render(Criteria.getall(), teamID,getRatedScore(teamID)));
    }

    @Security.Authenticated(Secured.class)
    public static List<Integer> getRatedScore(String teamID){
        List<Integer> result = new ArrayList<Integer>();
        List<models.Rating>temp = models.Rating.findByAccountAndTeam(Account.findEmail(session().get("email")),
                models.Team.getByID(Long.parseLong(teamID)));
        result.add(-1);
        int i=0,j=1;
        while(i<temp.size()){
            if(temp.get(i).criteria.Id==j){
                result.add(temp.get(i).rating);
                i++;
            }
            else result.add(-1);
        j++;
        }
        return result;
    }

    @Security.Authenticated(Secured.class)
    public static Result ratingPost() {
        if (Setting.find.byId(Setting.RATING).isActivated) {
            if (request().method().equals("POST")) {
                Team team = null;
                Account account = Account.findEmail(session().get("email"));
                Map<String, String[]> map = request().body().asFormUrlEncoded();
                Long id = Long.parseLong(map.get("uid")[0]);
                team = Team.find.byId(id);
                for (models.Rating rating : models.Rating.findByTeamAndAccount(id, account.id)) {
                    rating.delete();
                }
                for (Map.Entry<String, String[]> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String[] value = entry.getValue();
                    if (key.equals("uid")) break;
                    models.Rating obj = new models.Rating(account, Criteria.find.byId(Long.parseLong(key)), Integer.parseInt(value[0]), team);
                    obj.save();
                }
                ObjectNode result = Json.newObject();
                result.put("type", "success");
                result.put("text", "Submitted");
                return ok(result);
            } else return ok();
        }
        return badRequest("Disable this function by admin");
    }


}
