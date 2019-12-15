package com.hrkt.commandlinecalculator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class CommandlineCalculatorApplication {

	public static void main(String[] args) {

		try (ConfigurableApplicationContext ctx = SpringApplication.run(CommandlineCalculatorApplication.class, args)) {
			CommandlineInterface cli = ctx.getBean(CommandlineInterface.class);
			cli.run();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
