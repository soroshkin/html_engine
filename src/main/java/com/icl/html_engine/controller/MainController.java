package com.icl.html_engine.controller;

import com.icl.html_engine.exception.NotValidInputException;
import com.icl.html_engine.service.EngineImpl;
import com.icl.html_engine.service.Person;
import com.icl.html_engine.util.FileHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Controller
@Slf4j
public class MainController {
    private EngineImpl engine;
    private FileHandler fileHandler;
    private Person oldMan;

    @Autowired
    public MainController(EngineImpl engine, FileHandler fileHandler, Person oldMan) {
        this.engine = engine;
        this.fileHandler = fileHandler;
        this.oldMan = oldMan;
    }

    @GetMapping(path = "/readHTML")
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String convertHTML(Model model) {
        engine.addAttribute(oldMan.getName(), oldMan);
        engine.addAttribute("testValue", 5);
        engine.addAttribute("x5", 854);
        try {
            fileHandler.writeToFile(engine.parseHTML(fileHandler.readFileToString()));
        } catch (NotValidInputException | IOException | IllegalAccessException | InvocationTargetException e) {
            log.error(e.getMessage());
            model.addAttribute("exception", e);
            return "error";
        }

        return "convertedHTML";
    }
}
