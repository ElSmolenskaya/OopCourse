package ru.academits.smolenskaya.minesweeper.model;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Timer {
    private int secondsCount;
    ScheduledExecutorService scheduler;
    private final int maximumSecondsCount;
    private final Collection<TimerSubscriber> subscribers = new CopyOnWriteArrayList<>();

    public Timer(int maximumSecondsCount) {
        this.maximumSecondsCount = maximumSecondsCount;
    }

    public int getSecondsCount() {
        return secondsCount;
    }

    public void start() {
        scheduler = Executors.newScheduledThreadPool(1);

        Runnable runnable = () -> {
            notifySubscribers();

            if (secondsCount < maximumSecondsCount) {
                ++secondsCount;
            } else {
                stop();
            }
        };

        scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();

        notifySubscribers();
    }

    protected void notifySubscribers() {
        for (final TimerSubscriber subscriber : subscribers) {
            notifySubscriber(subscriber);
        }
    }

    private void notifySubscriber(TimerSubscriber subscriber) {
        assert subscriber != null;

        subscriber.timerChanged();
    }

    public void subscribe(TimerSubscriber subscriber) {
        if (subscriber == null) {
            throw new NullPointerException("Subscriber is empty");
        }

        if (subscribers.contains(subscriber)) {
            throw new IllegalArgumentException("The subscriber " + subscriber + " has already been signed");
        }

        subscribers.add(subscriber);
        notifySubscriber(subscriber);
    }

    @SuppressWarnings("unused")
    public void unsubscribe(TimerSubscriber subscriber) {
        if (subscriber == null) {
            throw new NullPointerException("Subscriber is empty");
        }

        if (!subscribers.contains(subscriber)) {
            throw new IllegalArgumentException("Unknown subscriber: " + subscriber);
        }

        subscribers.remove(subscriber);
    }
}