package com.hrkt.commandlinecalculator;

import lombok.extern.slf4j.Slf4j;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CommandlineInterface {
    public void run(String... args) {
        log.info("hello");

        try(Terminal terminal = TerminalBuilder.terminal()) {
            int ch = 0;
            while ((ch = terminal.reader().read()) != 0x09){
                // TAB(0x09)で抜ける
                char c = (char)ch;
                System.out.println(String.format("%d, %c", ch, ch));
            }
        } catch(IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
