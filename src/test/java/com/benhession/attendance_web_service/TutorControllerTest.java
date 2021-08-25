package com.benhession.attendance_web_service;

import com.benhession.attendance_web_service.data.UniversityClassService;
import com.benhession.attendance_web_service.model.UniversityClass;
import com.benhession.attendance_web_service.representational_models.StudentAttendedModel;
import com.benhession.attendance_web_service.representational_models.TutorModuleModel;
import com.benhession.attendance_web_service.representational_models.TutorUniversityClassModel;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class TutorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    static void updateDates(@Autowired UniversityClassService classService) {
        Set<UniversityClass> classSet = classService.findAll();
        for (UniversityClass aClass: classSet) {
            if (aClass.getClassId().equals("TM3542102")) {
                aClass.setDateTime(LocalDateTime.now(ZoneId.of("UTC")));
            }

            switch (aClass.getClassId()) {
                case "TM3542102":
                    aClass.setDateTime(LocalDateTime.now(ZoneId.of("UTC")));
                    break;
                case "TM3542101":
                    aClass.setDateTime(LocalDateTime.now(ZoneId.of("UTC")).plusDays(1));
                    break;
            }
        }

        classService.saveAll(classSet);
    }

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
                                            Assertions.assertThat(aStudent.getAttended()).isFalse();
                                            break;
                                        case "traversm0936":
                                            Assertions.assertThat(aStudent.getAttended()).isTrue();
                                    }
                                });
                                break;
                            case "TM4702102":
                            case "TM4702103":
                                aClass.getStudents().forEach(aStudent -> {
                                    switch (aStudent.getStudentId()) {
                                        case "yarrowp3138":
                                            Assertions.assertThat(aStudent.getAttended()).isTrue();
                                            break;
                                        case "traversm0936":
                                            Assertions.assertThat(aStudent.getAttended()).isFalse();
                                    }
                                });
                                break;
                            case "TM3512001":
                                aClass.getStudents().forEach(aStudent -> {
                                    if ("yarrowp3138".equals(aStudent.getStudentId())) {
                                        Assertions.assertThat(aStudent.getAttended()).isFalse();
                                    }
                                });
                        }
                    }));
                });
    }

    /**
     * Fulfills part of the confirmation "Tutors can log in, other user types cannot"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_web_client"}, username = "dylanb2441")
    public void tutorsCanGetClasses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tutor/classes"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Fulfills part of the confirmation "Tutors can log in, other user types cannot"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_web_client"}, username = "yarrowp3138")
    public void studentsCannotGetClasses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tutor/classes"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    /**
     * Fulfills part of the confirmation "Tutors can log in, other user types cannot"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_mobile_client"}, username = "dylanb2441")
    public void userMustHaveWebClientScope() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tutor/classes"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    /**
     * Fulfills part of the confirmation "Marking the student as attended updates the attendance record"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_web_client"}, username = "dylanb2441")
    public void studentIsMarkedAsAttended() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/tutor/student-attended").with(csrf())
                .contentType("application/json")
                .content(new ObjectMapper()
                        .writeValueAsString(new JSONObject()
                                .appendField(
                                        "studentId",
                                        "harrisog2543"
                                )
                                .appendField(
                                        "classId",
                                        "TM3542102"
                                )
                        )
                )
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo((mvcResult) -> {
                    String content = mvcResult.getResponse().getContentAsString();
                    final ObjectMapper objectMapper = new ObjectMapper();

                    TutorUniversityClassModel theClass =
                            objectMapper.readValue(content, TutorUniversityClassModel.class);

                    Optional<StudentAttendedModel> theStudent = Optional.empty();

                    for (StudentAttendedModel student : theClass.getStudents()) {
                        if (student.getStudentId().equals("harrisog2543")) {
                            theStudent = Optional.of(student);
                        }
                    }

                    Assertions.assertThat(theStudent.isPresent()).isTrue();
                    Assertions.assertThat(theStudent.get().getAttended()).isTrue();

                });
    }

    /**
     * Fulfills part of the confirmation "Students cannot be marked as having attended upcoming classes"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_web_client"}, username = "dylanb2441")
    public void cannotMarkAttendedIfClassIsUpcoming() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/tutor/student-attended").with(csrf())
                .contentType("application/json")
                .content(new ObjectMapper()
                        .writeValueAsString(new JSONObject()
                                .appendField(
                                        "studentId",
                                        "harrisog2543"
                                )
                                .appendField(
                                        "classId",
                                        "TM3542101"
                                )
                        )
                )
        ).andExpect(MockMvcResultMatchers.status().isPreconditionFailed());
    }
}
