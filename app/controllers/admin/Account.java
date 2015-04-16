package controllers.admin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.delete_account;
import views.html.admin.list_account;
import views.html.admin.sorted_list_account;


/**
 * Created by Win 8 Pro on 15/4/2558.
 */
public class Account extends Controller{

    public static Result listAccount() {
        if (request().method().equals("GET")){
            return ok(list_account.render());
        }
        else if (request().method().equals("POST")){
            String orderBy = request().body().asFormUrlEncoded().get("orderBy")[0];
            if(orderBy.equals("team")) {
                return ok(sorted_list_account.render(models.Account.getAllByTeam()));
            }
            else if(orderBy.equals("name")) {
                return ok(sorted_list_account.render(models.Account.getAll()));
            }
        }
        return badRequest();
    }

    public static Result deleteAccount(){

        if (request().method().equals("GET")){
            return ok(delete_account.render(models.Account.getAll()));
        }
        else if(request().method().equals("POST")) {
            String ID = request().body().asFormUrlEncoded().get("id")[0];
            models.Account account = models.Account.find.byId(Long.parseLong(ID));
            if(account != null){
                account.delete();
                ObjectNode result = Json.newObject();
                result.put("type", "success");
                result.put("text", "Deleted");
                return ok(result);
            }
            ObjectNode result = Json.newObject();
            result.put("type", "danger");
            result.put("text", "Delete Fail");
            return ok(result);
        }
        return badRequest();
    }
}
