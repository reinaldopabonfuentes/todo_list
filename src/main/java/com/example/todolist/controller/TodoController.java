package com.example.todolist.controller;

import com.example.todolist.entity.TodoItem;
import com.example.todolist.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoItemRepository todoItemRepository;

    @Autowired
    public TodoController(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @GetMapping("/items")
    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> todoItems = todoItemRepository.findAll();
        return todoItems;
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<TodoItem> getTodoItemById(@PathVariable Long id) {
        Optional<TodoItem> todoItem = todoItemRepository.findById(id);
        return todoItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/items")
    public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem todoItem) {
        TodoItem savedTodoItem = todoItemRepository.save(todoItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTodoItem);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<TodoItem> updateTodoItem(@PathVariable Long id, @RequestBody TodoItem updatedTodoItem) {
        if (!todoItemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedTodoItem.setId(id);
        TodoItem savedTodoItem = todoItemRepository.save(updatedTodoItem);
        return ResponseEntity.ok(savedTodoItem);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long id) {
        if (!todoItemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        todoItemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
