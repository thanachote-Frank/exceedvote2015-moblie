package controllers.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.user.Secured;
import forms.Register;
import models.*;
import models.UserType;
import play.Logger;
import play.Play;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.*;
import play.mvc.Security;


/**
 * Created by Win 8 Pro on 15/4/2558.
 */
public class Account extends Controller{
    @Security.Authenticated(Secured.class)
    public static Result listAccount() {
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("GET")){
            Logger.info(session("email") + " ACCESS TO ACCOUNT LIST PAGE");
            return ok(list_account.render(models.Account.getAll()));
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
    @Security.Authenticated(Secured.class)
    public static synchronized Result deleteAccount(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }

        if (request().method().equals("GET")){
            Logger.info(session("email") + " ACCESS TO DELETE ACCOUNT PAGE");
            return ok(delete_account.render(models.Account.getAll()));
        }
        else if(request().method().equals("POST")) {
            String ID = request().body().asFormUrlEncoded().get("id")[0];
            models.Account account = models.Account.find.byId(Long.parseLong(ID));
            if(account != null){
                Logger.info(session("email") + " DELETE ACCOUNT_ID="+account.id);
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
    @Security.Authenticated(Secured.class)
    public static synchronized Result edit() {
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("GET")){
            Logger.info(session("email") + " EDIT ACCOUNT PAGE");
            return ok(edit_account.render(models.Account.getAll(), models.Team.getAllAndOrder(), models.UserType.getAllAndOrder()));
        }
        else if(request().method().equals("POST")) {
            try {
                String ID = request().body().asFormUrlEncoded().get("id")[0];
                String name = request().body().asFormUrlEncoded().get("name")[0];
                String lastname = request().body().asFormUrlEncoded().get("lastname")[0];
                String email = request().body().asFormUrlEncoded().get("email")[0];
                String password = request().body().asFormUrlEncoded().get("password")[0];
                String teamName = request().body().asFormUrlEncoded().get("team")[0];
                String type = request().body().asFormUrlEncoded().get("userType")[0];
                models.Account account = models.Account.getByID(Long.parseLong(ID));
                account.setName(name);
                account.setLastname(lastname);
                account.setPassword(password);
                account.setEmail(email);
                account.setTeam(models.Team.getByName(teamName));
                account.setType(UserType.findType(type));
                account.update();
                Logger.info(session("email") + " EDIT ACCOUNT_ID="+ID);
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

    @Security.Authenticated(Secured.class)
    public static Result search(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
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
    @Security.Authenticated(Secured.class)
    public static synchronized Result addAccount(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("GET")){
            Logger.info(session("email") + " ADD ACCOUNT PAGE");
            return ok(add_account.render(Form.form(Register.class),models.Team.getAll(),models.UserType.getAllAdmin()));
        }else if (request().method().equals("POST")){
            Form<Register> form = Form.form(Register.class).bindFromRequest();
            if (form.hasErrors()){
                ObjectNode result = Json.newObject();
                result.put("type", "danger");
                result.put("text", form.globalError().message());
                return ok(result);
            }
            if (form.get().team == null) {
                models.Account account = new models.Account(form.get().name, form.get().lastname, form.get().email, form.get().password, null, models.UserType.findType(form.get().type));
                account.save();
            }else {
                models.Account account = new models.Account(form.get().name, form.get().lastname, form.get().email, form.get().password, models.Team.find.byId(form.get().team), models.UserType.findType(form.get().type));
                account.save();
            }
            Logger.info(session("email") + " ADD NEW ACCOUNT");
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Success");
            return ok(result);
        }
        return badRequest();
    }

    @Security.Authenticated(Secured.class)
    public static Result logout(){
        if(!models.Account.findEmail(session().get("email")).type.equals(models.UserType.findType("Admin"))){
            Logger.error(session("email") + " TRY TO BE ADMIN");
            return redirect(controllers.user.routes.Menu.mainMenu());
        }
        if (request().method().equals("GET")) {
            session().clear();
            return redirect(controllers.user.routes.Account.login());
        }
        return badRequest();
    }
}
