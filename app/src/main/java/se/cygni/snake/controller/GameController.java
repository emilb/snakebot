package se.cygni.snake.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.cygni.game.World;
import se.cygni.game.enums.Direction;
import se.cygni.snake.suscriber.NewGameSubscriber;

@RestController
public class GameController {
    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private NewGameSubscriber newGameSubscriber;

    @RequestMapping(value = "/newGame", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public World newGame() throws JsonProcessingException {
        LOG.error("SATRATLDSF");
        return newGameSubscriber.createNewGame(10,10);
    }

    @RequestMapping(value = "/addPlayer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addPlayer(@RequestParam ("name") String name) {
        return newGameSubscriber.addPlayer(name);
    }

    @RequestMapping(value = "/makeMove", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public World makeMove(@RequestParam ("id") String id, @RequestParam ("direction") String direction) {
        LOG.error("SAKSDLASKLSADLDKSLADKAS");

        try{
            Direction.valueOf(direction);
            LOG.error("SKRETLRELTR:E");
        }catch (Exception e) {
            LOG.error("SAAAAAA " + e);
        }
        return newGameSubscriber.movePlayer(id, Direction.valueOf(direction));
    }

    @RequestMapping(value = "/startGame", method = RequestMethod.GET)
    public void startGame() {
        newGameSubscriber.startGame();
    }

    @RequestMapping(value = "/tick", method = RequestMethod.GET)
    public World tick() throws Exception {
        return newGameSubscriber.tick();
    }


}