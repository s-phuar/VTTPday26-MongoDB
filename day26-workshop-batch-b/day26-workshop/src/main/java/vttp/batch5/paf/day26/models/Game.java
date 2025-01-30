package vttp.batch5.paf.day26.models;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

import static vttp.batch5.paf.day26.repositories.Constants.*;

public class Game {

    private int gid;
    private String name;
    private int ranking;
    private String url;
    private String image;
    private List<Comment> comments;

    public int getGid() {return gid;}
    public void setGid(int gid) {this.gid = gid;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getRanking() {return ranking; }
    public void setRanking(int ranking) {this.ranking = ranking;}
    public String getUrl() {return url; }
    public void setUrl(String url) {this.url = url; }
    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}
    public List<Comment> getComments() {return comments;}
    public void setComments(List<Comment> comments) {this.comments = comments;}
    
    public JsonObject toJson(){
        JsonArrayBuilder arr = Json.createArrayBuilder();
        comments.stream()
            .map(c -> c.toJson())
            .forEach(j -> arr.add(j));

        return Json.createObjectBuilder()
            .add(F_GID, gid)
            .add(F_NAME, name)
            .add(F_RANKING, ranking)
            .add(F_URL, url)
            .add(F_IMAGE, image)
            .add(F_COMMENTS, arr.build())
            .build();
    }
    
    public static Game toGame(JsonObject g, List<JsonObject> c){
        Game game =  toGame(g);
        game.setComments(
            c.stream()
                .map(Comment::toComment)  
                .toList()
        );
        return game;
    }


    public static Game toGame(JsonObject g) {
        Game game = new Game();
        game.setGid(g.getInt(F_GID));
        game.setName(g.getString(F_NAME));
        game.setRanking(g.getInt(F_RANKING));
        game.setUrl(g.getString(F_URL));
        game.setImage(g.getString(F_IMAGE));
        return game;
    }

}
