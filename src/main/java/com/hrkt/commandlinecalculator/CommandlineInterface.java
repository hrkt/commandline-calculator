package com.hrkt.commandlinecalculator;

import jline.console.ConsoleReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.transform.sax.SAXSource;
import java.io.IOException;

@Component
@Slf4j
public class CommandlineInterface {
    public void run(String... args) {
        log.info("hello");

        ConsoleReader cr = null;
        try {
            cr = new ConsoleReader();
            int ch = 0;
            while ((ch = cr.readCharacter()) != 0x09){
                // TAB(0x09)で抜ける
                char c = (char)ch;
                System.out.println(String.format("%d, %c", ch, ch));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
