package com.unloadbrain.assignment.relay42.metrics.api;

import com.unloadbrain.assignment.relay42.metrics.Application;
import com.unloadbrain.assignment.relay42.metrics.TestConfig;
import com.unloadbrain.assignment.relay42.metrics.dto.Query;
import com.unloadbrain.assignment.relay42.metrics.dto.QueryResponse;
import com.unloadbrain.assignment.relay42.metrics.service.QueryService;
import com.unloadbrain.assignment.relay42.metrics.service.QueryServiceFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class SensorDataQueryApiIT {


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QueryServiceFactory queryServiceFactoryMock;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldReturnTemperatureDataPoints() throws Exception {

        // Given
        when(queryServiceFactoryMock.getService(anyString())).thenReturn(new QueryService<String>() {
            @Override
            public String getOperation() {
                return "coolOperation";
            }

            @Override
            public QueryResponse<String> query(Query query) {
                return QueryResponse.<String>builder().query(query).response("Awesome response").build();
            }
        });

        // When and Then
        mockMvc.perform(post("/api/devices/metrics/aggregate")
                .param("aggregateOperation", "coolOperation")
                .param("aggregateField", "coolAggregateField")
                .param("deviceGroup", "coolDeviceGroup1")
                .param("startTime", "1563142796")
                .param("endTime", "1563142800")
                .param("deviceId", "coolDeviceId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Awesome response"))
                .andExpect(jsonPath("$.query.operation").value("coolOperation"))
                .andExpect(jsonPath("$.query.deviceId").value("coolDeviceId"))
                .andExpect(jsonPath("$.query.deviceGroup").value("coolDeviceGroup1"))
                .andExpect(jsonPath("$.query.startTime").value(1563142796))
                .andExpect(jsonPath("$.query.endTime").value(1563142800))
                .andExpect(jsonPath("$.query.additionalParams.aggregateField").value("coolAggregateField"))
                .andDo(print());
    }
}