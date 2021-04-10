package com.benhession.attendance_web_service;

import com.benhession.attendance_web_service.representational_models.StudentUniversityClassModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerClassesTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "yarrowp3138")
    public void userShouldHaveRoleStudent() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_mobile_client"}, username = "yarrowp3138")
    public void usersWithRoleTutorAreNotAuthorised() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_admin", "SCOPE_mobile_client"}, username = "yarrowp3138")
    public void usersWithRoleAdminAreNotAuthorised() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_web_client"}, username = "yarrowp3138")
    public void clientMustHaveScopeMobileClient() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "unknown")
    public void studentIdShouldExist() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isExpectationFailed());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "yarrowp3138")
    public void shouldReturnClassesForUsername1() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(result -> {

                    String content = result.getResponse().getContentAsString();
                    final ObjectMapper objectMapper = new ObjectMapper();

                    List<StudentUniversityClassModel> classModels =
                            objectMapper.readValue(content, new TypeReference<>() {});
                    Set<String> expectedIds = new HashSet<>(Arrays.asList("TM4702101", "TM4702102", "TM4702103"));
                    Set<String> actualIds = new HashSet<>();

                    classModels.forEach(model -> actualIds.add(model.getClassId()));

                    //do test
                    Assertions.assertThat(actualIds)
                            .hasSameSizeAs(expectedIds)
                            .containsAll(expectedIds);

                });
    }

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "traversm0936")
    public void shouldReturnClassesForUsername2() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(result -> {

                    String content = result.getResponse().getContentAsString();
                    final ObjectMapper objectMapper = new ObjectMapper();

                    List<StudentUniversityClassModel> classModels =
                            objectMapper.readValue(content, new TypeReference<>() {});
                    Set<String> expectedIds = new HashSet<>(
                            Arrays.asList("TM4702101", "TM4702102", "TM4702103")
                    );
                    Set<String> actualIds = new HashSet<>();

                    classModels.forEach(model -> actualIds.add(model.getClassId()));

                    //do test
                    Assertions.assertThat(actualIds)
                            .hasSameSizeAs(expectedIds)
                            .containsAll(expectedIds);

                });
    }

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "stookeyp3037")
    public void studentWithoutClassesShouldReturnNoContent() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}
