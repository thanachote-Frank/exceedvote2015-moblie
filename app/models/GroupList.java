package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by thanachote on 27/2/2558.
 */
@Entity
public class GroupList {
    @Id
    public Long id;
    public String name;
    public String description;

    // Finder will help us easily query data from database.
    public static Model.Finder<Long, GroupList> find =
            new Model.Finder<Long, GroupList>(Long.class, GroupList.class);


//    public static Account authenticate(
//            String username, String password) {
//        return Account.find.where().eq("username", username).eq("password", password).findUnique();
//    }

    public static List<GroupList> getAll() {
        return GroupList.find.all();
    }
}
