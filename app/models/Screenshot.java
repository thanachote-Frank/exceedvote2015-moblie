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
        Team team = Team.getByID(teamID);
        return Screenshot.find.where().eq("team", team).orderBy().desc("id").setMaxRows(4).findList();
    }

}
