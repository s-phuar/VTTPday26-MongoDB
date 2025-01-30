package VTTPday26.workshop.service;

import static VTTPday26.workshop.model.Constants.F_GID;
import static VTTPday26.workshop.model.Constants.F_IMAGE;
import static VTTPday26.workshop.model.Constants.F_NAME;
import static VTTPday26.workshop.model.Constants.F_RANKING;
import static VTTPday26.workshop.model.Constants.F_URL;
import static VTTPday26.workshop.model.Constants.F_USER;
import static VTTPday26.workshop.model.Constants.F_USERS_RATED;
import static VTTPday26.workshop.model.Constants.F_YEAR;

import java.time.Instant;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VTTPday26.workshop.repository.GamesRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Service
public class BGGService {
    @Autowired
    private GamesRepository gamesRepository;

    public JsonObject getGames(String name, int limit, int offset){
        List<JsonObject> jsonList = gamesRepository.getGames(name, limit, offset);

        //building array of games
        JsonArrayBuilder gamesBuilder = Json.createArrayBuilder();
        for(int i = 0; i < jsonList.size(); i ++){
            gamesBuilder = gamesBuilder.add(jsonList.get(i));
        }
        JsonArray gamesArray = gamesBuilder.build();
        System.out.println(gamesArray);

        //building final json object
        return Json.createObjectBuilder()
            .add("games", gamesArray)
            .add("offset", offset)
            .add("limit", limit)
            .add("total", gamesArray.size())
            .add("timestamp", Instant.now().toString())
            .build();
    }


    public JsonObject getGamesByRank(int limit, int offset, int rank){
        List<JsonObject> jsonList = gamesRepository.getGames("", limit, offset);

        //from the list, pick out a singular object based on rank
        JsonObject temp = null;
        for (JsonObject jsonObject : jsonList) {
            if(jsonObject.getInt(F_RANKING) == rank){
                temp = jsonObject;
            }
        }

        return temp;
    }

    public JsonObject getGameById(String id){
        Document doc = gamesRepository.getGameById(id);

        JsonObject jsonObj = Json.createObjectBuilder()
            .add("game_id", doc.getInteger(F_GID))
            .add("name", doc.getString(F_NAME))
            .add("year", doc.getInteger(F_YEAR))
            .add("ranking", doc.getInteger(F_RANKING))
            // .add("averageRanking", doc.getInteger(F_USER))/////
            .add("users_rated", doc.getInteger(F_USERS_RATED))
            .add("url", doc.getString(F_URL))
            .add("thumbnail", doc.getString(F_IMAGE))
            .add("timestamp", Instant.now().toString())
            .build();

        return jsonObj;
    }   




}
