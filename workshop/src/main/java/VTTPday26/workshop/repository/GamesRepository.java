package VTTPday26.workshop.repository;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import VTTPday26.workshop.model.IDNotFoundException;

import static VTTPday26.workshop.model.Constants.*;

import jakarta.json.Json;

import jakarta.json.JsonObject;

@Repository
public class GamesRepository {
    @Autowired
    private MongoTemplate template;


    // db.games.find({
    //     "name": { $regex: "gamename", $options: "i" }  // Case-insensitive regex filter on name
    // })
    // .sort({ "ranking": -1 })  // Sort by rating.average in descending order
    // .skip(0)  // Skip the first 5 documents (pagination)
    // .limit(5)  // Limit the result to 5 documents

    public List<JsonObject> getGames(String name, int limit, int offset){
        //grab game id and name, put into json array as value to games key
        Criteria criteria = Criteria.where(F_NAME)
            .regex(name, "i");

        Query query = Query.query(criteria)
            .with(Sort.by(Sort.Direction.ASC, F_RANKING))
            .limit(limit)
            .skip(offset);

        List<Document> docList = template.find(query, Document.class, C_GAMES);

        //turn list of documents, into list of jsonobjects(each objects hold 1 gid and 1 name)
        return docList.stream()
            .map(doc ->
                Json.createObjectBuilder()
                .add("game_id", doc.getInteger(F_GID))
                .add("name", doc.getString(F_NAME))
                .add("ranking", doc.getInteger(F_RANKING))
                .build()
                ).toList();

    }

    //db.tv_shows.find({ _id: ObjectId(‘abc123’) })
    public Document getGameById(String id) {
        try{
        ObjectId docId = new ObjectId(id);
        Document doc = template.findById(docId,Document.class, C_GAMES);
        return doc;
        }catch(IllegalArgumentException ex){
            throw new IDNotFoundException("Document with ID: " + id + " cannot be found");
        }
    }


    
}
