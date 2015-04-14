package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Frank on 3/22/15 AD.
 */
@Entity
public class Screenshot extends Model{

    @Id
    public Long id;

    @ManyToOne
    public Team team;

    public String url;

    public static Finder<Long, Screenshot> find =
            new Finder<Long, Screenshot>(Long.class, Screenshot.class);

    public Screenshot(Team team, String url) {
        this.team = team;
        this.url = url;
    }

    public static List<Screenshot> getURL(Long teamID){
        return Screenshot.find.where().eq("team_id", teamID).orderBy().asc("id").setMaxRows(4).findList();
    }

    public static List<Screenshot> getAll(Long teamID){
        return Screenshot.find.where().eq("team_id", teamID).findList();
    }

    public static void deleteAllByTeamID(Long ID){
        List<Screenshot> temp = Screenshot.getAll(ID);
        for (Screenshot screenshot : temp) {
            screenshot.delete();
        }
    }


}
