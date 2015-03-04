package models;

import play.db.ebean.Model;
import javax.persistence.*;
import java.util.List;


/**
 * Created by USER on 4/3/2558.
 */
public class Vote extends Model {

    public Long teamId;
    public Long accountId;
    public String kind;
    public Long vote;
}
