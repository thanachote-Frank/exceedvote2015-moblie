package models;

import play.db.ebean.*;

import javax.persistence.*;

/**
 * Created by Frank on 2/25/15 AD.
 */

@Entity
public class Account extends Model {
    @Id
    public Long id;
    public String username;
    public String password;

    // Finder will help us easily query data from database.
    public static Finder<Long, Account> find =
            new Finder<Long, Account>(Long.class, Account.class);


    public static Account authenticate(
            String username, String password) {
        return Account.find.where().eq("username", username).eq("password", password).findUnique();
    }
}

