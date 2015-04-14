package models;

import org.joda.time.DateTime;
import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;


/**
 * Created by thanachote on 14/4/2558.
 */

@Entity
public class TimeOut extends Model {

    @Id
    public Long id;

    public DateTime dateTime;

    public static Finder<Long, TimeOut> find =
            new Finder<Long, TimeOut>(Long.class, TimeOut.class);

    public TimeOut(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static List<TimeOut> getAll() {
        return TimeOut.find.orderBy("dateTime").findList();
    }

    public static List<TimeOut> getAllAndOrder(){
        return TimeOut.find.order("name").findList();
    }

    public static void deleteAll() {
        for(TimeOut timeOut: getAll()){
            timeOut.delete();
        }
    }
}
