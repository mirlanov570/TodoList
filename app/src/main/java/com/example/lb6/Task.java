package com.example.lb6;

public class Task {
    private int id; // Идентификатор задачи
    private String title; // Название задачи
    private String description; // Описание задачи
    private int status; // Статус задачи (0 - не выполнена, 1 - выполнена)

    // Конструктор класса
    public Task(int id, String title, String description, int status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    // Метод для обновления статуса задачи
    public void setStatus(int status) {
        this.status = status;
    }

    // Метод для строкового представления задачи
    @Override
    public String toString() {
        return title + ": " + description;
    }
}
