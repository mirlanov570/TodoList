package com.example.lb6;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    private final ArrayList<Task> tasks;
    private final DatabaseHelper dbHelper;

    public TaskAdapter(Context context, ArrayList<Task> tasks, DatabaseHelper dbHelper) {
        super(context, 0, tasks);
        this.tasks = tasks;
        this.dbHelper = dbHelper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        CheckBox checkbox = convertView.findViewById(R.id.checkbox);
        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
        TextView textViewDescription = convertView.findViewById(R.id.textViewDescription);

        textViewTitle.setText(task.getTitle());
        textViewDescription.setText(task.getDescription());


        checkbox.setChecked(task.getStatus() == 1);


        if (task.getStatus() == 1) {
            textViewTitle.setPaintFlags(textViewTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textViewTitle.setPaintFlags(textViewTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }


        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setStatus(isChecked ? 1 : 0);
            dbHelper.updateTask(task);


            if (isChecked) {
                tasks.remove(position);
                tasks.add(task);
            }

            notifyDataSetChanged();
        });


        convertView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Удалить задачу")
                    .setMessage("Вы уверены, что хотите удалить эту задачу?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        dbHelper.deleteTask(task.getId()); // Удаляем задачу из базы данных
                        tasks.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(getContext(), "Задача удалена", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Нет", null)
                    .show();
            return true;
        });

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
