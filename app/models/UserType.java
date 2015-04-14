package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by Frank on 3/22/15 AD.
 */
@Entity
public class UserType extends Model {

    @Id
    public Long id;

    public String name;
    public Integer weight;

    public static Finder<Long, UserType> find =
            new Finder<Long, UserType>(Long.class, UserType.class);

    public UserType(String name, Integer weight) {
        this.name = name;
        this.weight = weight;
    }

    public static List<UserType> getAll() {
        return UserType.find.orderBy("name").findList();
    }

    public static UserType findType(String name){
        return UserType.find.where().eq("name", name).findUnique();
    }
}
