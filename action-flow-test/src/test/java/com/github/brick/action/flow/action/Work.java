package com.github.brick.action.flow.action;

import java.util.Timer;
import java.util.TimerTask;

public class Work extends Timer {
    public static void main(String[] args) {
        Work timer = new Work();
        timer.task(() -> {
            System.out.println(System.currentTimeMillis());
            System.out.println("111");
        }, 3000);
        System.out.println(System.currentTimeMillis());
        timer.restTimer(1000);

    }
    private Runnable runnable;
    private TimerTask timerTask;

    // 开一个任务
    public void task(Runnable runnable, long delay) {
        this.runnable = runnable;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Work.this.runnable.run();
            }
        };
        this.schedule(timerTask, delay);
    }

    // 重置一个任务的时间
    public void restTimer(long delay) {
        timerTask.cancel();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        this.schedule(timerTask, delay);
    }
}
