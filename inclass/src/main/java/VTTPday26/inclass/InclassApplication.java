package VTTPday26.inclass;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import VTTPday26.inclass.repository.SeriesRepository;

@SpringBootApplication
public class InclassApplication implements CommandLineRunner{

	@Autowired
	private SeriesRepository seriesRepository;

	public static void main(String[] args) {
		SpringApplication.run(InclassApplication.class, args);
	}


	//we don't need mongoDB to be opened to run the application
	@Override
	public void run(String... args){


		// // 1
		// List<String> results = seriesRepository.getAllGenres();
		// for (String g:results){
		// 		System.out.printf(">>>>>%s\n", g);
		// 	}
		// System.out.printf(">>>>> results: %d\n\n", results.size());


		//2
		// List<String> results = seriesRepository.genresByCountry("canada");
		// for (String g:results){
		// 	System.out.printf(">>>>>%s\n", g);
		// }
		// System.out.printf(">>>>> results: %d\n\n", results.size());
		// System.out.println(results);

		// //3
		List<Document> results = seriesRepository.findSeriesByName("ar");

		for (Document d: results){
			System.out.printf(">>>%s\n", d.toJson());
		}

		System.out.printf(">>>>> results: %d\n\n", results.size());

		// parsing json to document
		// 	Document doc = Document.parse(json.toString());


	}




}
