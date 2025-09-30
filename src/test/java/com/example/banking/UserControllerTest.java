package com.example.banking;

import com.example.banking.controller.UserController;
import com.example.banking.dto.CreateUserRequestDto;
import com.example.banking.dto.UserDto;
import com.example.banking.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserDto buildUserDto(Long id, String name, String email) {
        return new UserDto(
                id,
                UUID.randomUUID(),
                name,
                email,
                LocalDateTime.now(),
                true
        );
    }

    @Test
    void createUser_shouldReturnUser() throws Exception {
        CreateUserRequestDto request = new CreateUserRequestDto("John Doe", "john@example.com");
        UserDto response = buildUserDto(1L, "John Doe", "john@example.com");

        Mockito.when(userService.createUser(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        UserDto response = buildUserDto(1L, "Alice", "alice@example.com");

        Mockito.when(userService.getUserById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void getAllUsers_shouldReturnPage() throws Exception {
        UserDto user = buildUserDto(1L, "Alice", "alice@example.com");
        Mockito.when(userService.getAllUsers(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(user)));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Alice"));
    }

    @Test
    void getByEmail_shouldReturnUser() throws Exception {
        UserDto user = buildUserDto(2L, "Bob", "bob@example.com");
        Mockito.when(userService.getByEmail("bob@example.com")).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/by-email")
                        .param("email", "bob@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.email").value("bob@example.com"));
    }
}
