
import com.example.todolist.controller.TodoController;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.todolist.entity.TodoItem;
import com.example.todolist.repository.TodoItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import java.util.Optional;

@SpringJUnitConfig
@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TodoItemRepository todoItemRepository;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetAllTodoItems() throws Exception {
        // Arrange: Set up mock data and behavior
        List<TodoItem> mockData = List.of(new TodoItem("Task 1", "Description 1"));
        Mockito.when(todoItemRepository.findAll()).thenReturn(mockData);

        // Act and Assert: Perform a GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Task 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Description 1"));
    }

    @Test
    public void testGetTodoItemById() throws Exception {
        // Arrange: Set up mock data and behavior
        TodoItem mockItem = new TodoItem("Task 1", "Description 1");
        Mockito.when(todoItemRepository.findById(1L)).thenReturn(Optional.of(mockItem));

        // Act and Assert: Perform a GET request for a specific item and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Task 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description 1"));
    }

    @Test
    public void testCreateTodoItem() throws Exception {
        // Arrange: Prepare a JSON request body
        String requestBody = "{\"title\":\"New Task\",\"description\":\"New Description\"}";

        // Act and Assert: Perform a POST request to create a new item and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/todo/items/1"));
    }

    @Test
    public void testUpdateTodoItem() throws Exception {
        // Arrange: Prepare a JSON request body
        String requestBody = "{\"title\":\"Updated Task\",\"description\":\"Updated Description\"}";

        // Act and Assert: Perform a PUT request to update an item and verify the response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Description"));
    }

    @Test
    public void testDeleteTodoItem() throws Exception {
        // Arrange: Set up mock data and behavior
        Mockito.when(todoItemRepository.existsById(1L)).thenReturn(true);

        // Act and Assert: Perform a DELETE request to delete an item and verify the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
