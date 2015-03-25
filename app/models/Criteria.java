package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

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

    public Criteria(String name){
        this.name = name;
    }

    public static List<Criteria> getall(){
        return Criteria.find.all();
    }
    public static String toStringId(Long id,int num){
        return "rating-"+(id*5+num);
    }

}
