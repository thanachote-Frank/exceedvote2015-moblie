package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by USER on 4/3/2558.
 */
@Entity
public class Vote extends Model {

    @Id
    public Long id;

    @OneToOne
    public Account account;

    @ManyToOne
    public Catalog catalog;

    @ManyToOne
    public Team team;

    public Vote(Account acc, Catalog catalog, Team team){
        this.account=acc;
        this.catalog=catalog;
        this.team = team;
    }

    public static List<Vote> findByCatalogAndTeam(Catalog catalog,Team team){
        return Vote.find.where().eq("catalog", catalog).eq("team", team).findList();
    }

    public static Finder<Long, Vote> find =
            new Finder<Long, Vote>(Long.class, Vote.class);

}
