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
    public String name;
    public String lastname;
    public String email;
    public String password;

    @ManyToOne
    public Team team;
    // Finder will help us easily query data from database.
    public static Finder<Long, Account> find =
            new Finder<Long, Account>(Long.class, Account.class);


    public static Account authenticate(
            String email, String password) {
        return Account.find.where().eq("email", email).eq("password", password).findUnique();
    }
}

