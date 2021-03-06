package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by Frank on 3/28/15 AD.
 */
@Entity
public class Setting extends Model{
    public final static long UPLOAD_LOGO = 1;
    public final static long UPLOAD_SCREENSHOT = 2;
    public final static long EDIT_DESCRIPTION = 3;
    public final static long TEAM_LIST = 4;
    public final static long TEAM_DESCRIPTION = 5;
    public final static long RATING = 6;
    public final static long CREATE_TEAM = 7;
    public final static long CREATE_ACCOUNT = 8;
    public final static long RATING_RESULT = 9;
    public final static long VOTE =10;
    public final static long VOTE_RESULT =11;
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

    public static List<Setting> getAll() {
        return Setting.find.findList();
    }

    public static void deleteAll(){
        for(Setting setting:Setting.getAll()){
            setting.delete();
        }
    }
}
