package controllers.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import forms.CreateTeam;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.delete_team;
import views.html.admin.edit_team;
import views.html.admin.list_team;
import views.html.admin.add_team;


/**
 * Created by thanachote on 14/4/2558.
 */
public class Team extends Controller{


    public static Result listTeam() {
        if (request().method().equals("GET")){
            return ok(list_team.render(models.Team.getAllAndOrder()));
        }
        return badRequest();
    }

    public static Result deleteTeam(){

        if (request().method().equals("GET")){
            return ok(delete_team.render(models.Team.getAll()));
        }
        else if(request().method().equals("POST")) {
            String ID = request().body().asFormUrlEncoded().get("id")[0];
            models.Team team = models.Team.find.byId(Long.parseLong(ID));
            if(team != null){
                team.delete();
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

    public static Result addTeam() {
        if (request().method().equals("GET")) {
            return ok(add_team.render(Form.form(CreateTeam.class)));
        } else if (request().method().equals("POST")) {
            Form<CreateTeam> form = Form.form(CreateTeam.class).bindFromRequest();
            if (form.hasErrors()) {
                ObjectNode result = Json.newObject();
                result.put("type", "danger");
                result.put("text", form.globalError().message());
                return ok(result);
            }
            models.Team team = new models.Team(form.get().name, "" + "/assets/images/logo.png");
            team.save();
            ObjectNode result = Json.newObject();
            result.put("type", "success");
            result.put("text", "Success");
            return ok(result);
        }
        return badRequest();
    }
    
    public static Result edit() {
        if (request().method().equals("GET")){
            return ok(edit_team.render(models.Team.getAllAndOrder()));
        }
        else if(request().method().equals("POST")) {
            try {
                String ID = request().body().asFormUrlEncoded().get("id")[0];
                String name = request().body().asFormUrlEncoded().get("name")[0];
                String description = request().body().asFormUrlEncoded().get("description")[0];
                String logo = request().body().asFormUrlEncoded().get("logo")[0];
                String img1 = request().body().asFormUrlEncoded().get("image1")[0];
                String img2 = request().body().asFormUrlEncoded().get("image2")[0];
                String img3 = request().body().asFormUrlEncoded().get("image3")[0];
                String img4 = request().body().asFormUrlEncoded().get("image4")[0];
                models.Team team = models.Team.getByID(Long.parseLong(ID));
                team.setName(name);
                team.setDescription(description);
                team.setLogo(logo);
                team.update();

                models.Screenshot.deleteAllByTeamID(Long.parseLong(ID));
                (new models.Screenshot(team, img1)).save();
                (new models.Screenshot(team, img2)).save();
                (new models.Screenshot(team, img3)).save();
                (new models.Screenshot(team, img4)).save();
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
            String name = request().body().asFormUrlEncoded().get("input")[0];
            ObjectNode result = Json.newObject();
            ArrayNode node = result.putArray("data");
            models.Team.findByName(name).forEach(team -> {
                ObjectNode node1 = node.addObject();
                node1.put("id", team.id);
                node1.put("name", team.name);
                node1.put("description", team.description);
                node1.put("logo", team.logo);

                ArrayNode node2 = node1.putArray("images");
                models.Screenshot.getAll(team.id).forEach(image->{
                    node2.add(image.url);
                });
                while (node2.size() != 4){
                    node2.add("");
                }

            });
            return ok(result);
        }
        return badRequest();

    }
}
