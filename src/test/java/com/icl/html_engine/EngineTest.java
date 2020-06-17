package com.icl.html_engine;

import com.icl.html_engine.exception.NotValidInputException;
import com.icl.html_engine.service.EngineImpl;
import com.icl.html_engine.service.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;

@SpringBootTest
public class EngineTest {

    @Autowired
    private EngineImpl engine;

    @Test
    public void parseHTMLTest() throws NotValidInputException, InvocationTargetException, IllegalAccessException {
        Person oldMan = new Person("Ivan", 105);
        engine.addAttribute(oldMan.getName(), oldMan);
        engine.addAttribute("testValue", 5);
        engine.addAttribute("x5", 854);

        String text = "<body>" +
                "<strong>{1+2}</strong>" +
                "<strong>1 + (2*(-4.7)) = {1 + (2*(-4.7))}</strong>"
                + "<strong>Ivan's age = {Ivan.getAge()}</strong>"
                + "<strong>Ivan's age x 2 = {Ivan.getAgeX2()}</strong>"
                + "<br/><strong>testValue = {testValue}</strong>"
                + "<p> x5 = {x5}</p>";

        Assertions.assertThat(engine.parseHTML(text)).isEqualTo(
                "<body>" +
                        "<strong>3</strong>" +
                        "<strong>1 + (2*(-4.7)) = -8.4</strong>"
                        + "<strong>Ivan's age = 105</strong>"
                        + "<strong>Ivan's age x 2 = 210</strong>"
                        + "<br/><strong>testValue = 5</strong>"
                        + "<p> x5 = 854</p>");
    }
}
