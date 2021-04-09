package com.benhession.attendance_web_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "yarrowp3138")
    public void userShouldHaveStudentAuthorisation() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
