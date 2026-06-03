package com.erguidos.ichor.service.open_route_api;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.erguidos.ichor.dto.response.open_route_api.ORAPIGetDurationResponse;
import com.erguidos.ichor.exceptions.ORAPIException;

@Service
public class ORAPIService implements ORAPIServiceInterface {
	private final String BASE_URL;
	private final String KEY;
	private final String API_RESPONSE_ERROR = "Cannot determinate duration throw API response";
	private final String API_HTTP_ERROR = "API HTTP ERROR: ";
			
	private RestClient rc;
	
	public ORAPIService(@Value(
			"${app.api.key}") String key,
			@Value("${app.api.url_base}") String url) {
		rc = RestClient.create();
		KEY = key;
		BASE_URL = url;
	}

	@Override
	public double getDurationBetweenTwoHospitalPoints(
			BigDecimal fromLon, BigDecimal fromLat, BigDecimal toLon, BigDecimal toLat) {
		
		ORAPIGetDurationResponse dr = 
				rc
				.get()
				.uri(BASE_URL + "api_key={key}&start={fromLon},{fromLat}&end={toLon},{toLat}",
						KEY, fromLon, fromLat, toLon, toLat)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
					throw new ORAPIException(API_HTTP_ERROR + request.getURI().getPath() + " " + response.getStatusCode());
				})
				.body(ORAPIGetDurationResponse.class);
		
		if(dr == null || dr.features() == null || dr.features().isEmpty())
			throw new ORAPIException(API_RESPONSE_ERROR);
		
		return dr.features().get(0).properties().summary().duration();
	}
}

