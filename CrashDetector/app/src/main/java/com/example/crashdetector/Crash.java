package com.example.crashdetector;

public class Crash {

    String time;
    float speed;
    float acceleration;
    String type;

    public Crash(String time, String type, float speed, float acceleration) {
        this.time = time;
        this.speed = speed;
        this.acceleration = acceleration;
        this.type = type;
    }
}
