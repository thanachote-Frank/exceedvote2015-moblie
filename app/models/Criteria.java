package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Frank on 3/22/15 AD.
 */
@Entity
public class Criteria extends Model{

    @Id
    public Long Id;

    public String name;

    public static Finder<Long, Criteria> find =
            new Finder<Long, Criteria>(Long.class, Criteria.class);


}
