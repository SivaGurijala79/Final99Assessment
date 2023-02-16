package com.final99.EmployeeAccess.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final99.EmployeeAccess.dto.ModuleDto;
import com.final99.EmployeeAccess.exception.ExceptionAdvice;
import com.final99.EmployeeAccess.service.ModuleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static com.final99.EmployeeAccess.testData.TestData.prepareModuleDto;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ModuleControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    ModuleDto moduleDto = prepareModuleDto("Module1", Arrays.asList("Employee1", "Employee2"));
    @Mock
    private ModuleService moduleService;
    @InjectMocks
    private ModuleController controller;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ExceptionAdvice())
                .setMessageConverters(
                        new MappingJackson2HttpMessageConverter(
                                new ObjectMapper()))
                .build();
    }

    @Test
    public void getAllModules() throws Exception {
        when(moduleService.getAllModules()).thenReturn(Collections.singletonList(moduleDto));
        MvcResult mvcResult = mockMvc.perform(get("/modules")).andExpect(status().is(200)).andReturn();
        String strResponse = mvcResult.getResponse().getContentAsString();
        ModuleDto[] modules = mapFromJson(strResponse, ModuleDto[].class);
        assertEquals(1, modules.length);
    }

    @Test
    public void saveModule() throws Exception {
        when(moduleService.addModule(any())).thenReturn(moduleDto);
        MvcResult mvcResult = mockMvc.perform(post("/module").content(objectMapper.writeValueAsString(moduleDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201)).andReturn();
        String strResponse = mvcResult.getResponse().getContentAsString();
        ModuleDto module = mapFromJson(strResponse, ModuleDto.class);
        assertEquals(moduleDto.getName(), module.getName());
    }

    @Test
    public void updateModule() throws Exception {
        when(moduleService.updateModule(eq(1001), any())).thenReturn(moduleDto);
        MvcResult mvcResult = mockMvc.perform(put("/module/{id}", 1001).content(objectMapper.writeValueAsString(moduleDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201)).andReturn();
        String strResponse = mvcResult.getResponse().getContentAsString();
        ModuleDto module = mapFromJson(strResponse, ModuleDto.class);
        assertEquals(moduleDto.getName(), module.getName());
    }

    @Test
    public void deleteModule() throws Exception {
        when(moduleService.deleteModule(1001)).thenReturn("Success");
        MvcResult result = mockMvc.perform(delete("/module/1001")).andExpect(status().is(200)).andReturn();
        assertEquals("\"Success\"", result.getResponse().getContentAsString());
    }

    <T> T mapFromJson(String json, Class<T> clazz)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}