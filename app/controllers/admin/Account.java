package controllers.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.*;


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

    public static Result edit() {
        if (request().method().equals("GET")){
            return ok(edit_account.render(models.Account.getAll()));
        }
        else if(request().method().equals("POST")) {
            try {
                String ID = request().body().asFormUrlEncoded().get("id")[0];
                String name = request().body().asFormUrlEncoded().get("name")[0];
                String lastname = request().body().asFormUrlEncoded().get("lastname")[0];
                String password = request().body().asFormUrlEncoded().get("password")[0];
                models.Account account = models.Account.getByID(Long.parseLong(ID));
                account.setName(name);
                account.setLastname(lastname);
                account.setPassword(password);
                account.update();
            } catch (Exception e){
                System.out.println(e);
                ObjectNode result = Json.newObject();
                result.put("type", "danger");
                result.put("text", "Save Fail");
                return badRequest(result);
            }
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Saved");
            return ok(result);
        }
        return badRequest();
    }

    public static Result search(){
        if (request().method().equals("POST")){
            String page = request().body().asFormUrlEncoded().get("page")[0];
            String name = request().body().asFormUrlEncoded().get("input")[0];

            if(page.equals("list")) {
                return ok(sorted_list_account.render(models.Account.search(name)));
            }
            else if(page.equals("delete")){
                return ok(search_delete_account.render(models.Account.search(name)));
            }
            else if(page.equals("edit")){
                return ok(search_edit_account.render(models.Account.search(name)));
            }
        }
        return badRequest();

    }
}
