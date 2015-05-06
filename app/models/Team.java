package models;

import com.avaje.ebean.*;
import play.db.ebean.*;


import javax.persistence.*;
import javax.persistence.OrderBy;
import java.util.List;

/**
 * Created by thanachote on 27/2/2558.
 */
@Entity
public class Team extends Model implements Comparable<Team>{
    @Id
    public Long id;
    public String name;

    @Column(columnDefinition = "TEXT")
    public String description;
    public String logo;

    @OneToMany(cascade = CascadeType.REMOVE)
    public List<Account> accounts;

    @OneToMany(cascade = CascadeType.REMOVE)
    public List<Screenshot> screenshots;

    @OneToMany(cascade = CascadeType.REMOVE)
    public List<Rating> ratings;

    // Finder will help us easily query data from database.
    public static Model.Finder<Long, Team> find =
            new Model.Finder<Long, Team>(Long.class, Team.class);

    public Team(String name, String logo) {
        this.name = name;
        this.logo = logo;
        this.description = "";
    }

//    public static Account authenticate(
//            String username, String password) {
//        return Account.find.where().eq("username", username).eq("password", password).findUnique();
//    }

    public static List<Team> getAll() {
        return Team.find.orderBy("name").findList();
    }

    public static Team getDescription(Long teamID) {
        return Team.find.where().eq("id", teamID).findUnique();
    }

    public static List<Account> getSortedAllMember(Long teamID) {
        com.avaje.ebean.OrderBy<Account> orderBy = new com.avaje.ebean.OrderBy<>();
        orderBy.asc("name");
        orderBy.asc("lastname");
        return Account.find.setOrder(orderBy).where().eq("team_id", teamID).findList();
    }

    //    public static void setDescription(Long teamID, String description) {
//        Team team = Team.find.byId(teamID);
//        team.description = description;
//        System.out.println(description);
//        team.update();
//    }
    public static Team findTeam(String email) {
        return Account.findEmail(email).team;
    }

    public void setDescription(String description) {
        this.description = description;
        this.update();
    }

    public void setLogo(String url) {
        this.logo = url;
        this.update();
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Team getByID(Long id) {
        return Team.find.where().eq("id", id).findUnique();
    }

    public static List<Team> getAllAndOrder(){
        return Team.find.order("name").findList();
    }

    public static List<Team> findByName(String name) {
        return Team.find.where().ilike("name","%"+name+"%").order("name").findList();
    }

    public static Team getByName(String name) {
        return Team.find.where().eq("name",name).findUnique();
    }

    @Override
    public int compareTo(Team o) {
        return o.name.compareTo(this.name);
    }
}
