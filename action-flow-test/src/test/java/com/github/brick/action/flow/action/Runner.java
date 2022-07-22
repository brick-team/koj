package com.github.brick.action.flow.action;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Timer;
@Component
public class Runner implements CommandLineRunner {

    private static Timer timer = null;
    @Override
    public void run(String... args) throws Exception {
        Work timer = new Work();
        timer.task(() -> {
            System.out.println(System.currentTimeMillis());
            System.out.println("111");
        }, 3000);

        Runner.timer = timer;

    }

    public static Timer getTimer() {
        return timer;
    }
}
