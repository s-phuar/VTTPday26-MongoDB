package VTTPday26.workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import VTTPday26.workshop.service.BGGService;
import jakarta.json.JsonObject;

@Controller
public class BGGController {
    @Autowired
    private BGGService bggService;

    //localhost:8080/games?limit=5&name=pan
    @GetMapping(path="/games", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGames(
        @RequestParam(name="limit", defaultValue = "25") int limit,
        @RequestParam(name="offset", defaultValue = "0") int offset,
        @RequestParam(name="name", defaultValue = "") String name
        ){

        JsonObject gameJson = bggService.getGames(name, limit, offset);
        System.out.println(gameJson.toString());

        return ResponseEntity.ok().body(gameJson.toString());
    }

    //localhost:8080/games/ranking?limit=30&rank=30
    @GetMapping(path="/games/ranking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGameByRank(
        @RequestParam(name="limit", defaultValue = "25") int limit,
        @RequestParam(name="offset", defaultValue = "0") int offset,
        @RequestParam(name="rank", defaultValue = "") int rank
        ){

        JsonObject gameJson = bggService.getGamesByRank(limit, offset, rank);
        System.out.println(gameJson.toString());

        return ResponseEntity.ok().body(gameJson.toString());
    }

    //localhost:8080/game/679b393d3aeef01315cb6bec
    @GetMapping(path="/game/{game_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGameById(
        @PathVariable(name="game_id") String id){

        JsonObject jsonObj = bggService.getGameById(id);
        
        System.out.println(jsonObj.toString());
        
        return ResponseEntity.ok().body(jsonObj.toString());
    }



}
