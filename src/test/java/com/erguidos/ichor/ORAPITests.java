package com.erguidos.ichor;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erguidos.ichor.service.open_route_api.ORAPIService;
import com.erguidos.ichor.service.open_route_api.ORAPIServiceInterface;

@SpringBootTest
class ORAPITests {

    @Autowired
    private ORAPIServiceInterface service;

    @Test
    void getDuration() {

        double d = service.getDurationBetweenTwoHospitalPoints(
                new BigDecimal("8.681495"),
                new BigDecimal("49.41461"),
                new BigDecimal("8.687872"),
                new BigDecimal("49.420318")
        );

        System.out.println(d);
    }
}


