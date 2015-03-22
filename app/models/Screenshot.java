package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Created by Frank on 3/22/15 AD.
 */
@Entity
public class Screenshot extends Model{

    @Id
    public Long id;

    @OneToOne
    public Team team;

    public String url;

    public static Finder<Long, Screenshot> find =
            new Finder<Long, Screenshot>(Long.class, Screenshot.class);

    public Screenshot(Team team, String url) {
        this.team = team;
        this.url = url;
    }
}
