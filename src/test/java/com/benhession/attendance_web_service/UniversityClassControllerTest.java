package com.benhession.attendance_web_service;

import com.benhession.attendance_web_service.data.UniversityClassService;
import com.benhession.attendance_web_service.model.UniversityClass;
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
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class UniversityClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // update dates classes in order for tests to work as expected
    @BeforeAll
    static void updateDates(@Autowired UniversityClassService classService) {
        Set<UniversityClass> classSet = classService.findAll();
        for (UniversityClass aClass: classSet) {
            switch (aClass.getClassId()) {
                case "TM4702101":
                    aClass.setDateTime(LocalDateTime.now(ZoneId.of("UTC")).plusWeeks(1));
                    break;
                case "TM4702102":
                    aClass.setDateTime(LocalDateTime.now(ZoneId.of("UTC")).minusWeeks(1));
            }
        }

        classService.saveAll(classSet);
    }

    /**
     * Fulfills part of the confirmation "Only tutors can obtain QR codes"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_web_client"}, username = "dylanb2441")
    public void userShouldHaveRoleTutor() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/class/qrcode")
                .param("classId", "TM4702101"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Fulfills part of the confirmation "Only tutors can obtain QR codes"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_student", "SCOPE_web_client"}, username = "yarrowp3138")
    public void userShouldNotHaveRoleStudent() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/class/qrcode")
                .param("classId", "TM4702101"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    /**
     * Fulfills part of the confirmation "Only tutors can obtain QR codes"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_web_client"}, username = "aFakeUsername")
    public void tutorIdShouldExist() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/class/qrcode")
                .param("classId", "TM4702101"))
                .andExpect(MockMvcResultMatchers.status().isExpectationFailed());
    }

    /**
     * Fulfills the confirmation "Hash codes are only available for classes that have not finished"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_web_client"}, username = "dylanb2441")
    public void classShouldNotHaveFinished() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/class/qrcode")
                .param("classId", "TM4702102"))
                .andExpect(MockMvcResultMatchers.status().isPreconditionFailed());
    }

    /**
     * Fulfills the confirmation "Tutors can only obtain QR codes for classes which they are assigned"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_web_client"}, username = "bloomfieldm2843")
    public void tutorShouldBeAssignedToClass() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/class/qrcode")
                .param("classId", "TM4702101"))
                .andExpect(MockMvcResultMatchers.status().isPreconditionFailed());
    }


    /**
     * Fulfills the confirmation "The QR code image should be an appropriate format"
     * @throws Exception if test fails
     */
    @Test
    @WithMockUser(authorities = {"ROLE_attendance_tutor", "SCOPE_web_client"}, username = "dylanb2441")
    public void contentTypeShouldBePng() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/class/qrcode")
                .param("classId", "TM4702101"))
                .andExpect(MockMvcResultMatchers.content().contentType("image/png"));

    }



}
