package com.maximo.mytaskmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maximo.mytaskmanager.R;
import com.maximo.mytaskmanager.activities.MainActivity;
import com.maximo.mytaskmanager.activities.TaskDetailPage;
import com.maximo.mytaskmanager.models.Task;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder> {
    List<Task> tasks;
    Context callingActivity;

    public TaskRecyclerViewAdapter(List<Task> tasks, Context callingActivity) {
        this.tasks = tasks;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        return new TaskViewHolder(taskFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TextView taskFragmentTextViewTaskTitle = holder.itemView.findViewById(R.id.FragmentTVTaskTitle);
        TextView taskFragmentTextViewTaskDescription = holder.itemView.findViewById(R.id.FragmentTVTaskDescription);
        TextView taskFragmentTextViewTaskState = holder.itemView.findViewById(R.id.FragmentTVTaskState);
        String taskTitle = tasks.get(position).getTaskTitle();
        String taskDescription = tasks.get(position).getTaskDescription();
        String taskState = tasks.get(position).getState().toString();
        taskFragmentTextViewTaskTitle.setText(position + ". " + taskTitle);
        taskFragmentTextViewTaskDescription.setText(taskDescription);
        taskFragmentTextViewTaskState.setText(taskState);
        View taskItemView = holder.itemView;
        taskItemView.setOnClickListener(view -> {
            Intent goToTaskDetailIntent = new Intent(callingActivity, TaskDetailPage.class);
            goToTaskDetailIntent.putExtra(MainActivity.TASK_TITLE_TAG, taskTitle);
            goToTaskDetailIntent.putExtra(MainActivity.TASK_TITLE_DESCRIPTION, taskDescription);
            callingActivity.startActivity(goToTaskDetailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        public TaskViewHolder(@NonNull View itemView){super(itemView);}
    }
}
