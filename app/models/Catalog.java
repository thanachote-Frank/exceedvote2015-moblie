package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Frank on 3/22/15 AD.
 */
@Entity
public class Catalog extends Model{

    @Id
    public Long Id;

    public String name;

    @OneToMany(cascade = CascadeType.REMOVE)
    public List<Vote> votes;

    public static Finder<Long, Catalog> find =
            new Finder<Long, Catalog>(Long.class, Catalog.class);

    public Catalog(String name){
        this.name = name;
    }

    public static List<Catalog> getAll(){
        return Catalog.find.all();
    }

    public static List<Catalog> getAllAndOrder(){
        return Catalog.find.order("name").findList();
    }

    public static List<Catalog> findByName(String name){
        return Catalog.find.where().ilike("name","%"+name+"%").order("name").findList();
    }



}
