package controllers.admin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.edit_user_type;
import views.html.admin.set_time_out;

/**
 * Created by thanachote on 14/4/2558.
 */
public class TimeOut extends Controller {

    public static Result set(){
        if (request().method().equals("GET")){
            DateTime dateTime = new DateTime();
            dateTime.withSecondOfMinute(0);
            dateTime.withMillisOfSecond(0);
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
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Saved");
            return ok(result);
        }
        return badRequest();
    }
}
