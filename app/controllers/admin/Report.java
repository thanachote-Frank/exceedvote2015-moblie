package controllers.admin;

import controllers.user.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.report;
import views.html.user.team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by RookieMo on 18/4/2558.
 */
public class Report extends Controller {
    @Security.Authenticated(controllers.user.Secured.class)
    public static Result report(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("GET")){
            return ok(report.render(models.Account.getAll(), models.Team.getAllAndOrder(),
                    models.UserType.getAllAndOrder(), models.Criteria.getall().size(),
                    call()));
        }
        return badRequest();
    }
    public static  Map<models.Team,Integer> call(){
        List<models.Team> tmp = models.Team.getAllAndOrder();
        Map<models.Team,Integer> map = new HashMap<>();
        tmp.forEach(team->{
             int tmp2 = models.Rating.getRateList(team).size();
            map.put(team,tmp2);
        });
        return map;
    }
}
