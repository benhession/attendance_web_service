package com.benhession.attendance_web_service;

import com.benhession.attendance_web_service.representational_models.TutorModuleModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TutorControllerTest {

    @Autowired
    MockMvc mockMvc;

    /**
     * Fulfills confirmation "The attendance record matches that of each class"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_web_client"}, username = "dylanb2441")
    public void returnsCorrectAttendanceRecordForTutor() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/tutor/classes"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(result -> {

                    // get returned models
                    String content = result.getResponse().getContentAsString();
                    final ObjectMapper objectMapper = new ObjectMapper();
                    List<TutorModuleModel> returnedModels =
                            objectMapper.readValue(content, new TypeReference<>() {});

                    // check attendance matches data.sql
                    returnedModels.forEach(aModule -> aModule.getClasses().forEach(aClass -> {
                        switch (aClass.getClassId()) {
                            case "TM4702101":
                                aClass.getStudents().forEach(aStudent -> {
                                    switch (aStudent.getStudentId()) {
                                        case "yarrowp3138":
                                            Assertions.assertFalse(aStudent.getAttended());
                                            break;
                                        case "traversm0936":
                                            Assertions.assertTrue(aStudent.getAttended());
                                    }
                                });
                                break;
                            case "TM4702102":
                            case "TM4702103":
                                aClass.getStudents().forEach(aStudent -> {
                                    switch (aStudent.getStudentId()) {
                                        case "yarrowp3138":
                                            Assertions.assertTrue(aStudent.getAttended());
                                            break;
                                        case "traversm0936":
                                            Assertions.assertFalse(aStudent.getAttended());
                                    }
                                });
                                break;
                            case "TM3512001":
                                aClass.getStudents().forEach(aStudent -> {
                                    if ("yarrowp3138".equals(aStudent.getStudentId())) {
                                        Assertions.assertFalse(aStudent.getAttended());
                                    }
                                });
                        }
                    }));
                });
    }
}
