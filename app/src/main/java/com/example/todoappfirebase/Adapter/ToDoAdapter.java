package com.example.todoappfirebase.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoappfirebase.AddNewTask;
import com.example.todoappfirebase.MainActivity;
import com.example.todoappfirebase.Model.ToDoModel;
import com.example.todoappfirebase.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> toDoList;
    private MainActivity activity;
    private FirebaseFirestore firestore;

    public ToDoAdapter(MainActivity mainActivity, List<ToDoModel> toDoList){
        this.toDoList = toDoList;
        activity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.each_task,parent, false);
        firestore = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }


    public void deleteTask(int position){
        ToDoModel toDoModel = toDoList.get(position);
        firestore.collection("task").document(toDoModel.TaskID).delete();
        toDoList.remove(position);
        notifyItemRemoved(position);
    }
    public Context getContext(){
        return activity;
    }
    public void editTask(int position){
        ToDoModel toDoModel = toDoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("task", toDoModel.getDue());
        bundle.putString("id",toDoModel.TaskID);

        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);

        addNewTask.show(activity.getSupportFragmentManager(), addNewTask.getTag());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ToDoModel toDoModel = toDoList.get(position);
        holder.mCheckBox.setText(toDoModel.getTask());
        holder.mDueDateTv.setText("Due on " + toDoModel.getDue());
        holder.mCheckBox.setChecked(toBoolean(toDoModel.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    firestore.collection("task").document(toDoModel.TaskID).update("status",1);
                }else{
                    firestore.collection("task").document(toDoModel.TaskID).update("status",0);
                }

            }
        });

    }

    private boolean toBoolean(int status){
        return status !=0;
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mDueDateTv;
        CheckBox mCheckBox;

      public MyViewHolder(@NonNull View itemView) {
          super(itemView);

          mDueDateTv = itemView.findViewById(R.id.due_date_tv);
          mCheckBox = itemView.findViewById(R.id.mcheckbox);
      }
  }



}
