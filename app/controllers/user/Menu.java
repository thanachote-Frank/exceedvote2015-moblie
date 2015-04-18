package controllers.user;

import models.Setting;
import models.TimeOut;
import org.joda.time.DateTime;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user.main_menu;

/**
 * Created by thanachote on 14/4/2558.
 */
public class Menu extends Controller{
    @Security.Authenticated(Secured.class)
    public static Result mainMenu() {
        DateTime dateTime = TimeOut.getAll().get(0).dateTime;
        String temp = dateTime.getYearOfEra()+"/"+dateTime.getMonthOfYear()+"/"+dateTime.getDayOfMonth();
        temp += " " + dateTime.getHourOfDay() + ":" + dateTime.getMinuteOfHour() + ":" + dateTime.getMillisOfSecond();
        return ok(main_menu.render(session().get("email"), models.Team.findTeam(session().get("email")),
                Setting.find.byId(Setting.TEAM_LIST).isActivated,
                Setting.find.byId(Setting.EDIT_DESCRIPTION).isActivated,
                Setting.find.byId(Setting.RATING_RESULT).isActivated,
                Setting.find.byId(Setting.VOTE).isActivated,
                Setting.find.byId(Setting.VOTE_RESULT).isActivated,
                temp));
    }
}
