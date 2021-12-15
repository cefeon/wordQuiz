package net.cefeon.wordquiz.controller;

import net.cefeon.wordquiz.service.LoadCSVService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LoadCSVController {
    private final LoadCSVService loadCSVService;

    public LoadCSVController(LoadCSVService loadCSVService) {
        this.loadCSVService = loadCSVService;
    }

    @GetMapping(value = "/loadcsv")
    public @ResponseBody
    ResponseEntity<String> get() throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        loadCSVService.saveListToDatabase();
        stopWatch.stop();
        return new ResponseEntity<>("CSV Loaded in "+stopWatch.getLastTaskTimeMillis()/1000+ " seconds", HttpStatus.OK);
    }
}
