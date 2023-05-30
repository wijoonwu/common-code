package company.ejm.commoncode.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import company.ejm.commoncode.api.dto.CodeGroupDto;
import company.ejm.commoncode.api.dto.CommonCodeDto;
import company.ejm.commoncode.api.service.CommonCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommonCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommonCodeService commonCodeService;

    private CodeGroupDto codeGroupDto;
    private CommonCodeDto commonCodeDto;

    @BeforeEach
    public void setUp() {
        codeGroupDto = CodeGroupDto
                .builder()
                .id(1L)
                .name("Test Group")
                .build();

        commonCodeDto = CommonCodeDto
                .builder()
                .id(2L)
                .code("1000")
                .name("success")
                .groupName("Test Group")
                .build();
        when(commonCodeService.createGroup(any(CodeGroupDto.class))).thenReturn(codeGroupDto);
        when(commonCodeService.createCode(any(CommonCodeDto.class))).thenReturn(commonCodeDto);
        when(commonCodeService.getCodeById(anyLong())).thenReturn(commonCodeDto);
    }

    @Test
    public void testCreateCodeGroup() throws Exception {
        mockMvc.perform(post("/api/common-codes/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(codeGroupDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    public void testCreateCode() throws Exception {
        mockMvc.perform(post("/api/common-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commonCodeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    public void testGetCode() throws Exception {
        mockMvc.perform(get("/api/common-codes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }
}
