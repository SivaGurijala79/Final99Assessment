package com.final99.EmployeeAccess.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.final99.EmployeeAccess.dto.EmployeeDto;
import com.final99.EmployeeAccess.exception.ExceptionAdvice;
import com.final99.EmployeeAccess.service.EmployeeService;
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

import static com.final99.EmployeeAccess.testData.TestData.prepareEmployeeDto;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    EmployeeDto employeeDto = prepareEmployeeDto("Employee1", Arrays.asList("Module1", "Module2"));
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private EmployeeController controller;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

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
    public void getAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(employeeDto));
        MvcResult mvcResult = mockMvc.perform(get("/employees")).andExpect(status().is(200)).andReturn();
        String strResponse = mvcResult.getResponse().getContentAsString();
        EmployeeDto[] employees = mapFromJson(strResponse, EmployeeDto[].class);
        assertEquals(1, employees.length);
    }

    @Test
    public void saveEmployee() throws Exception {
        when(employeeService.addEmployee(any())).thenReturn(employeeDto);
        MvcResult mvcResult = mockMvc.perform(post("/employee").content(objectMapper.writeValueAsString(employeeDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201)).andReturn();
        String strResponse = mvcResult.getResponse().getContentAsString();
        EmployeeDto employee = mapFromJson(strResponse, EmployeeDto.class);
        assertEquals(employeeDto.getName(), employee.getName());
    }

    @Test
    public void updateEmployee() throws Exception {
        when(employeeService.updateEmployee(eq(1001), any())).thenReturn(employeeDto);
        MvcResult mvcResult = mockMvc.perform(put("/employee/{id}", 1001).content(objectMapper.writeValueAsString(employeeDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201)).andReturn();
        String strResponse = mvcResult.getResponse().getContentAsString();
        EmployeeDto employee = mapFromJson(strResponse, EmployeeDto.class);
        assertEquals(employeeDto.getName(), employee.getName());
    }

    @Test
    public void deleteEmployee() throws Exception {
        when(employeeService.deleteEmployee(1001)).thenReturn("Success");
        MvcResult result = mockMvc.perform(delete("/employee/1001")).andExpect(status().is(200)).andReturn();
        assertEquals("\"Success\"", result.getResponse().getContentAsString());
    }

    String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    <T> T mapFromJson(String json, Class<T> clazz)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}