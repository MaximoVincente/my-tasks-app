//package com.maximo.mytaskmanager.models;
//
//import androidx.annotation.NonNull;
//
//
//
//public class Task {
//
//    public Long id;
//    private String taskTitle;
//    private String taskDescription;
//    private TaskStateEnum state;
//
//    public Task(String taskTitle, String taskDescription, TaskStateEnum state) {
//        this.taskTitle = taskTitle;
//        this.taskDescription = taskDescription;
//        this.state = state;
//    }
//
//    public Task() {
//    }
//
//
//    public String getTaskTitle() {
//        return taskTitle;
//    }
//
//    public void setTaskTitle(String taskTitle) {
//        this.taskTitle = taskTitle;
//    }
//
//    public String getTaskDescription() {
//        return taskDescription;
//    }
//
//    public void setTaskDescription(String taskDescription) {
//        this.taskDescription = taskDescription;
//    }
//
//    public TaskStateEnum getState() {
//        return state;
//    }
//
//    public void setState(TaskStateEnum state) {
//        this.state = state;
//    }
//
//    public enum TaskStateEnum {
//        NEW("New"),
//        ASSIGNED("Assigned"),
//        INPROGRESS("In Progress"),
//        COMPLETE("Complete");
//
//        private final String taskState;
//
//        TaskStateEnum(String taskState) {this.taskState = taskState;}
//
//        public String getTaskState(){return taskState;}
//
//        public static TaskStateEnum fromString(String possibleTaskState){
//            for (TaskStateEnum state : TaskStateEnum.values()){
//                if (state.taskState.equals(possibleTaskState)){
//                    return state;
//                }
//            }
//            return null;
//        }
//
//        @NonNull
//        @Override
//        public String toString() {
//            if (taskState == null) {
//                return "";
//            }
//            return taskState;
//        }
//    }
//}
