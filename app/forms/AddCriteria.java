package forms;

import models.Criteria;

/**
 * Created by thanachote on 8/4/2558.
 */
public class AddCriteria {
    public String name;

    public String validate() {
        if (name.equals("")) {
            return "Fill a criteria.";
        }
        if (Criteria.find.where().eq("name", name).findRowCount() > 0){
            return "Have this criteria in the system.";
        }
        return null;
    }

}
