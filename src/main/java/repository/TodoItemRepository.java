package repository;
import com.example.todolist.entity.TodoItem;
import org.springframework.data.repository.CrudRepository;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
    // No need to add custom query methods for basic CRUD operations
}
