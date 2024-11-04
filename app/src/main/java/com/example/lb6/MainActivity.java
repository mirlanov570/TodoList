package com.example.lb6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listViewTasks;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);
        listViewTasks = findViewById(R.id.listViewTasks);


        taskList = dbHelper.getAllTasks();
        taskAdapter = new TaskAdapter(this, taskList, dbHelper);
        listViewTasks.setAdapter(taskAdapter);


        FloatingActionButton fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });



        listViewTasks.setOnItemClickListener((adapterView, view, position, id) -> {
            Task task = taskList.get(position);
            task.setStatus(task.getStatus() == 0 ? 1 : 0);
            dbHelper.updateTask(task);

            if (task.getStatus() == 1) {

                taskList.remove(position);
                taskList.add(task);
                Toast.makeText(MainActivity.this, "Task marked as done", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Task marked as not done", Toast.LENGTH_SHORT).show();
            }

            taskAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        taskList.clear();
        taskList.addAll(dbHelper.getAllTasks());
        taskAdapter.notifyDataSetChanged();
    }
}
