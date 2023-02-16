package com.final99.EmployeeAccess;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeAccessApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("test passed");
        assertTrue(true);
    }

}
