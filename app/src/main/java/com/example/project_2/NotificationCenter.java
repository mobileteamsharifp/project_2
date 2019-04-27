package com.example.project_2;

import java.util.ArrayList;

public class NotificationCenter {
    private static NotificationCenter notificationCenter;
    private ArrayList<Observer> dataObservers = new ArrayList<>();

    public void registerForData(Observer observer) {
        dataObservers.add(observer);
    }

    public void unRegisterForData(Observer observer) {
        dataObservers.remove(observer);
    }

    public static NotificationCenter getNotificationCenter() {
        if (notificationCenter == null)
            notificationCenter = new NotificationCenter();
        return notificationCenter;
    }

    public void data_loaded(){
        for (Observer observer : dataObservers) {
            observer.update();
        }
    }

    private NotificationCenter(){

    }
}
