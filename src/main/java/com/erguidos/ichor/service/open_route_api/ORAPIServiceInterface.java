package com.erguidos.ichor.service.open_route_api;

import java.math.BigDecimal;

public interface ORAPIServiceInterface {
	double getDurationBetweenTwoHospitalPoints(BigDecimal fromLon, BigDecimal fromLat, BigDecimal toLon, BigDecimal toLat);
}
