package com.example.lb6;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private Button buttonSaveTask;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonSaveTask = findViewById(R.id.buttonSaveTask);

        dbHelper = new DatabaseHelper(this);

        buttonSaveTask.setOnClickListener(view -> {
            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();

            if (!title.isEmpty() && !description.isEmpty()) {
                dbHelper.addTask(title, description);
                Toast.makeText(AddTaskActivity.this, "Task added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddTaskActivity.this, MainActivity.class));
                finish(); // Close the current activity
            } else {
                Toast.makeText(AddTaskActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
