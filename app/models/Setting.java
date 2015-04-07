package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Frank on 3/28/15 AD.
 */
@Entity
public class Setting extends Model{

    @Id
    public Long id;

    public String name;

    public Boolean isActivated;

    public static Finder<Long, Setting> find =
            new Finder<Long, Setting>(Long.class, Setting.class);

    public Setting(Long id, String name, Boolean isActivated) {
        this.id = id;
        this.name = name;
        this.isActivated = isActivated;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
        this.update();
    }
}
