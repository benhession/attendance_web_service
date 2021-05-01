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
public class UniversityClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_web_client"}, username = "dylanb2441")
    public void userShouldHaveRoleTutor() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/class/qrcode")
                .param("classId", "TM4702101"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_web_client"}, username = "dylanb2441")
    public void contentTypeShouldBePng() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/class/qrcode")
                .param("classId", "TM4702101"))
                .andExpect(MockMvcResultMatchers.content().contentType("image/png"));

    }

    //TODO: Write tests based on a tutors users stories

}
