package se.cygni.snake.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SnakeController {
    @RequestMapping(value = "/getGames", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getApproval() {
        List<String> strings = new ArrayList<>();
        strings.add("Game 1");
        strings.add("Game 2");
        strings.add("Game 3");
        return strings;
    }
}
