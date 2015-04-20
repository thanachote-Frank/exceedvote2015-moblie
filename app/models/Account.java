package models;

import com.avaje.ebean.*;
import com.avaje.ebean.OrderBy;
import play.db.ebean.*;
import java.util.List;

import javax.annotation.Nullable;
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

    @OneToMany(cascade = CascadeType.REMOVE)
    public List<Rating> ratings;


    // Finder will help us easily query data from database.
    public static Finder<Long, Account> find =
            new Finder<Long, Account>(Long.class, Account.class);

    public Account(String name, String lastname, String email, String password, Team team,UserType type) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.team = team;
        this.type = type;
    }

    public static List<Account> getAll() {
        return Account.find.orderBy("name").findList();
    }

    public static Account getByID(Long id) {
        return Account.find.where().eq("id", id).findUnique();
    }

    public static List<Account> getAllByTeam() {
        OrderBy<Account> orderBy = new OrderBy<>();
        orderBy.asc("team");
        orderBy.asc("name");
        return Account.find.setOrderBy(orderBy).findList();
    }

    public static Account authenticate(
            String email, String password) {
        return Account.find.where().eq("email", email).eq("password", password).findUnique();
    }

    public static Account findEmail(String email) {
        return Account.find.where().eq("email", email).findUnique();
    }

    public static void setTeamToNullByTeamID(String ID){
        for (Account account: Account.find.where().eq("team_id", ID).findList()){
            account.team = null;
            account.update();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static List<Account> search(String name) {
        return Account.find.where().or(Expr.or(Expr.ilike("name", "%" + name + "%"),Expr.like("team.name", "%" + name + "%")),
                Expr.ilike("lastname","%"+ name +"%")).findList();
    }
    public static List<Account> getByUserType(UserType userType){
        return Account.find.where().eq("type",userType).findList();
    }

}

