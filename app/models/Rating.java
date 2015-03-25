package models;

import play.db.ebean.Model;
import javax.persistence.*;
import java.util.List;


/**
 * Created by USER on 4/3/2558.
 */
@Entity
public class Rating extends Model {

    @Id
    public Long id;

    @ManyToOne
    public Account account;

    @ManyToOne
    public Criteria criteria;

    @ManyToOne
    public Team team;

    public Integer rating;

    public static Finder<Long, Rating> find =
            new Finder<Long, Rating>(Long.class, Rating.class);

    public static List<Rating> GetRatingSpecific(Criteria criteria, Team team){
        return Rating.find.where().eq("criteria_id", criteria.Id).eq("team_id", team.id).findList();
    }
}
