package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Frank on 3/22/15 AD.
 */
@Entity
public class UserType extends Model {

    @Id
    public Long id;

    public String name;

    public static Finder<Long, UserType> find =
            new Finder<Long, UserType>(Long.class, UserType.class);
}
