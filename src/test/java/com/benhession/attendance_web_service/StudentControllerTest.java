package com.benhession.attendance_web_service;

import com.benhession.attendance_web_service.data.UniversityClassService;
import com.benhession.attendance_web_service.model.UniversityClass;
import com.benhession.attendance_web_service.representational_models.StudentUniversityClassModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // update dates of students classes in order for tests to work as expected
    @BeforeAll
    static void updateDates(@Autowired UniversityClassService classService) {
        Set<UniversityClass> classSet = classService.findAll();
        for (UniversityClass aClass: classSet) {
            switch (aClass.getClassId()) {
                case "TM4702101":
                    aClass.setDateTime(LocalDateTime.now(ZoneId.of("UTC")).minusWeeks(2));
                    break;
                case "TM4702102":
                    aClass.setDateTime(LocalDateTime.now(ZoneId.of("UTC")).minusWeeks(1));
                    break;
                case "TM4702103":
                    aClass.setDateTime(LocalDateTime.now(ZoneId.of("UTC")));
                    break;
                case "TM3512001":
                    aClass.setDateTime(LocalDateTime.now(ZoneId.of("UTC")).minusYears(1));
                    break;
            }
        }

        classService.saveAll(classSet);
    }

    // As a student I can login using my university login

    /**
     * Fulfills confirmation "Students can login, other user types cannot"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "yarrowp3138")
    public void userShouldHaveRoleStudent() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Fulfills confirmation "Students can login, other user types cannot"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_mobile_client"}, username = "yarrowp3138")
    public void usersWithRoleTutorAreNotAuthorised() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    /**
     * Fulfills confirmation "Students can login, other user types cannot"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_admin", "SCOPE_mobile_client"}, username = "yarrowp3138")
    public void usersWithRoleAdminAreNotAuthorised() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    /**
     * The web client shouldn't be able to reach endpoints designed for the mobile client.
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_web_client"}, username = "yarrowp3138")
    public void clientMustHaveScopeMobileClient() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    /**
     * Must be the username of a student
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "unknown")
    public void studentIdShouldExist() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isExpectationFailed());
    }

    // As a student I can see my timetabled classes

    /**
     * Fulfills the confirmation "The student can only see their own classes"
     * @throws Exception if test fails
     */
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
                    Set<String> expectedIds = new HashSet<>(Arrays.asList("TM4702101", "TM4702102", "TM4702103",
                            "TM3512001"));
                    Set<String> actualIds = new HashSet<>();

                    classModels.forEach(model -> actualIds.add(model.getClassId()));

                    //do test
                    Assertions.assertThat(actualIds)
                            .hasSameSizeAs(expectedIds)
                            .containsAll(expectedIds);

                });
    }

    /**
     * Fulfills the confirmation "The student can only see their own classes"
     * @throws Exception if test fails
     */
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

    /**
     * Fulfills the confirmation "The student can only see their own classes"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "stookeyp3037")
    public void studentWithoutClassesShouldReturnNoContent() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/student/classes"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    // As a student I can scan a QR code to update my attendance record.

    /**
     * Fulfills the confirmation "The attendance record is updated" and part of the confirmation "The student is
     * notified of success".
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "traversm0936")
    public void attendanceRecordIsSetAsTrue() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/student/attend")
                .contentType("application/json")
                .content(new ObjectMapper()
                        .writeValueAsString(new JSONObject()
                                .appendField(
                                        "qrString",
                                        "BB52F36E1C9E10909733B2FCA23290FA"
                                )
                        )
                )
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(mvcResult -> Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                        .isEqualTo("true"));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "yarrowp3138")
    public void attendReturnsFalseIfAttendedIsTrue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/student/attend")
                .contentType("application/json")
                .content(new ObjectMapper()
                        .writeValueAsString(new JSONObject()
                                .appendField(
                                        "qrString",
                                        "BB52F36E1C9E10909733B2FCA23290FA"
                                )
                        )
                )
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(mvcResult -> Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                        .isEqualTo("false"));
    }

    /**
     * Fulfills part of the confirmation "The student is notified if the QR code doesn't match one of their classes"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "traversm0936")
    public void attendReturnsNoContentIfClassIsNotStudents() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/student/attend")
                        .contentType("application/json")
                        .content(new ObjectMapper()
                                .writeValueAsString(new JSONObject()
                                        .appendField(
                                                "qrString",
                                                "187BB4BEDFD30EF473227C8AFC5F8283"
                                        )
                                )
                        )
        )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    /**
     * Fulfills the confirmation "The student can only attend classes that are in progress"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_mobile_client"}, username = "traversm0936")
    public void cannotAttendClassThatIsNotInProgress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/student/attend")
                .contentType("application/json")
                .content(new ObjectMapper()
                        .writeValueAsString(new JSONObject()
                                .appendField(
                                        "qrString",
                                        "78C4100F08DDFEBDBFF91F2EF7C1ECDC"
                                )
                        )
                )
        )
                .andExpect(MockMvcResultMatchers.status().isPreconditionFailed());
    }

}
