package com.unloadbrain.assignment.relay42.metrics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestConfig.class})
@ActiveProfiles("it")
public class ContextLoadIT {

    @Test
    public void contextLoads() {
        // Verify application context load properly.
    }
}
