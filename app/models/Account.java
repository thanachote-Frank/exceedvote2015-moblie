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
    public UserType type;

    @ManyToOne
    public Team team;
    // Finder will help us easily query data from database.
    public static Finder<Long, Account> find =
            new Finder<Long, Account>(Long.class, Account.class);

    public Account(String name, String lastname, String email, String password, Team team) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.team = team;
    }



    public static Account authenticate(
            String email, String password) {
        return Account.find.where().eq("email", email).eq("password", password).findUnique();
    }


    public static Account findEmail(String email) {
        return Account.find.where().eq("email", email).findUnique();
    }

}

