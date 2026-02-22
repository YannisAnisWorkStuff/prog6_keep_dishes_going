package com.yiannisftiti.prog6.keepdishesgoing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.Modulith;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.scheduling.annotation.EnableScheduling;

@Modulith
@EnableScheduling
public class KeepdishesgoingApplication {

    public static final Logger log = LoggerFactory.getLogger(KeepdishesgoingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KeepdishesgoingApplication.class, args);
	}

    @EventListener(ApplicationStartedEvent.class)
    void onApplicationStarted() {
        ApplicationModules modules = ApplicationModules.of(KeepdishesgoingApplication.class);
        modules.forEach(module -> log.info("\n{}", module));
    }

}
