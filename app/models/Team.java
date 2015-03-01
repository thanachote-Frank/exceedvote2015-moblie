package models;

import play.db.ebean.*;


import javax.persistence.*;
import java.util.List;

/**
 * Created by thanachote on 27/2/2558.
 */
@Entity
public class Team extends Model {
    @Id
    public Long id;
    public String name;
    public String description;

    // Finder will help us easily query data from database.
    public static Model.Finder<Long, Team> find =
            new Model.Finder<Long, Team>(Long.class, Team.class);


//    public static Account authenticate(
//            String username, String password) {
//        return Account.find.where().eq("username", username).eq("password", password).findUnique();
//    }

    public static List<Team> getAll() {
        return Team.find.all();
    }

    public static Team getDescription(Long teamID) {
        System.out.print(teamID);
        return Team.find.where().eq("id", teamID).findUnique();
    }

    public static List<Account> getAllMember(Long teamID) {
        return Account.find.where().eq("team_id", teamID).findList();
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

    public static Team getByID(Long id) {
        return Team.find.where().eq("id", id).findUnique();
    }


}