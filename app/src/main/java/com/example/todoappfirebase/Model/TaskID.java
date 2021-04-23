package com.example.todoappfirebase.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class TaskID {

    @Exclude
    public String TaskID;

    public <T extends TaskID> T withID (@NonNull final String id){
        this.TaskID = id;
        return (T) this;



    }
}
