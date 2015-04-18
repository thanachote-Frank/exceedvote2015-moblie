package controllers.admin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.edit_user_type;
import views.html.admin.set_time_out;

/**
 * Created by thanachote on 14/4/2558.
 */
public class TimeOut extends Controller {
    @Security.Authenticated(Secured.class)
    public static Result set(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("GET")){
            Logger.info(session("email") + " SET TIME PAGE");
            DateTime dateTime = new DateTime();
            dateTime = dateTime.withSecondOfMinute(0);
            dateTime = dateTime.withMillisOfSecond(0);
            DateTime endTime = models.TimeOut.getAll().get(0).dateTime;
            String timeLeft = endTime.getYearOfEra()+"/"+endTime.getMonthOfYear()+"/"+endTime.getDayOfMonth();
            timeLeft += " " + endTime.getHourOfDay() + ":" + endTime.getMinuteOfHour() + ":" + endTime.getMillisOfSecond();
            return ok(set_time_out.render(dateTime.toLocalDateTime(), endTime.toLocalDateTime(), timeLeft));
        }
        else if (request().method().equals("POST")) {
            String date_time = request().body().asFormUrlEncoded().get("date_time")[0];
            models.TimeOut.deleteAll();
            models.TimeOut timeOut = new models.TimeOut(new DateTime(date_time));
            timeOut.save();
            Logger.info(session("email") + " SET TIME TO "+(new DateTime(date_time)).toString());
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Saved");
            return ok(result);
        }
        return badRequest();
    }
}
