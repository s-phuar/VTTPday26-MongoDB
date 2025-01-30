package VTTPday26.inclass.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import static VTTPday26.inclass.repository.Constants.*;


// mongoimport
// --host=loclhost(-h locahost)
// --port=27017 (p)
// --database=showsdb (d)
// --collection=series (c)
// --type=json
// --jsonArray
// --filename=tv-shows.json

// mongoimport -d showsdb -c series --type=json --jsonArray --file=tv-shows.json


@Repository
public class SeriesRepository {
    @Autowired
    private MongoTemplate template;
    

    //db.series.distinct('genres')
    //.distinct almost always returns an array/list
    public List<String> getAllGenres(){
        Query query = new Query();
        return template.findDistinct(query, F_GENRES, C_SERIES, String.class);   
    }

    // db.series.find({
    //     name: {
    //         $regex: 'name',  //placeholder name
    //         $options:'i'     //case insensitive
    //     }
    // })

    // db.series.find({
    //     'network.country.name':{$regex:'Canada', $options:"i"}
    //     })

    //grabage?
    // /db.series.aggregate([
    //     {
    //         $match: {
    //             'network.country.name': { $regex: 'Canada', $options: 'i' }
    //         }
    //     },
    //     {
    //         $group: {
    //             _id: null,  // Grouping by null to get a single document for distinct genres
    //             genres: { $addToSet: '$genres' }  // Get distinct genres from the genres field
    //         }
    //     }
    // ])

    public List<String> genresByCountry(String country){
        // Criteria criteria = Criteria.where(F_NETWORK_COUNTRY_NAME).is(country);
        Criteria criteria = Criteria.where(F_NETWORK_COUNTRY_NAME)
            .regex(country, "i");

        Query query = Query.query(criteria);

        return template.findDistinct(query, F_GENRES, C_SERIES, String.class);
    }



    //db.series.find({
    //     'rating.average':{$gte:8},
    //     'name':{$regex:'ar', $options:"i"}    
    // })

    public List<Document> findSeriesByName(String name){
        //Define the search criteria
        Criteria criteria_by_rating = Criteria.where(F_RATING_AVERAGE)
            .gte(8);

        Criteria criteria_by_name = Criteria.where(F_NAME)
            .regex(name, "i")
            .andOperator(criteria_by_rating); //andOperator for 1 or more additional conditions, .and for only 2 total conditions

        //more compact but lose some readability
            // Criteria criteria_by_name = Criteria.andOperator(
            //     Criteria.where(F_NAME).regex(name, "i"),
            //     Criteria.where(F_RATING_AVERAGE).gte(8)
            // );



        //create the query based on the defined criteria
        Query query = Query.query(criteria_by_name)
            .with(
                Sort.by(Sort.Direction.DESC, F_RATING_AVERAGE)    //sorting
            )
            .limit(5) //pagination
            .skip(5L); //pagination, L being long data type
        query.fields()
            .include(F_NAME, F_SUMMARY, F_IMAGE_ORIGINAL, F_RATING_AVERAGE) //includes these fields
            .exclude(F_ID); //excludes these fields

        //perform the search, return list of document objects(each document is a row record)
        return template.find(query,Document.class, C_SERIES);
        // template.find(query, Document.class); DO NOT use this, will use default collection name based on java class name('Document')
    }



}
