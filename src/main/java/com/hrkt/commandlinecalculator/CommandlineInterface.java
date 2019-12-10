package com.hrkt.commandlinecalculator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandlineInterface {
    public void run(String... args) {
        log.info("hello");
        DeskCalculator deskCalculator = new DeskCalculator();

        try(Terminal terminal = TerminalBuilder.terminal()) {
            var ch = 0;
            while ((ch = terminal.reader().read()) != 0x09){
                log.debug(String.format("%d, %c", ch, ch));
                // TAB(0x09)で抜ける
                switch(ch) {
                    case '+': {
                        deskCalculator.pushPlusButton();
                        log.debug("plus");
                        break;
                    }
                    case '=': {
                        // same as 'Enter', so do not break here.
                    }
                    case 13: {
                        // 'Enter'
                        deskCalculator.pushEvalButton();
                        log.debug(deskCalculator.getCurrentValue().toPlainString());
                        System.out.println(deskCalculator.getCurrentValue().toPlainString());
                        break;
                    }
                    case 8: {
                        // 'Backspace'
                        deskCalculator.pushClearButton();
                        log.debug(deskCalculator.getCurrentValue().toPlainString());
                        break;
                    }
                    default: {
                        deskCalculator.pushChar((char)ch);
                    }
                }
            }
        } catch(IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
