package com.example.issues.Entities;

public enum Difficult {
    ONE(1),TWO(2),THREE(3),FOUR(4),FIVE(5);

    private final int difficultValue;

    Difficult(int difficultValue) {
        this.difficultValue = difficultValue;
    }

    public int getValue() {
        return this.difficultValue;
    }
}
