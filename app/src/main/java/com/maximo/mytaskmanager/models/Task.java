package com.maximo.mytaskmanager.models;


public class Task {
    private String taskTitle;
    private String taskDescription;
    private Enum state;

    public Task(String taskTitle, String taskDescription, Enum state) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.state = state;
    }

    public enum State {New, Assigned, InProgress, Complete}

    public Enum getState() {
        return state;
    }

    public void setState(Enum state) {
        this.state = state;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
