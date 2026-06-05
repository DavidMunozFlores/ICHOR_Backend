//package com.erguidos.ichor;
//
//import java.math.BigDecimal;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.erguidos.ichor.service.open_route_api.ORAPIService;
//import com.erguidos.ichor.service.open_route_api.ORAPIServiceInterface;
//
//
//class ORAPITests {
//	private String URL = "https://api.openrouteservice.org/v2/directions/driving-car?";
//	private String KEY = "eyJvcmciOiI1YjNjZTM1OTc4NTExMTAwMDFjZjYyNDgiLCJpZCI6IjAzZTg1MjZmMjQ2OTQxMTE4NDYyOWZiNGM5M2U3ZTkzIiwiaCI6Im11cm11cjY0In0=";
//	
//    private ORAPIServiceInterface service = new ORAPIService(KEY, URL, "");
//
//    // Coordenadas fijas de los hospitales (ID: 1 al 5)
//    // Orden requerido por tu firma: Longitud, Latitud
//    private final BigDecimal H1_LON = new BigDecimal("-3.68913000");  // La Paz
//    private final BigDecimal H1_LAT = new BigDecimal("40.47743000");
//    
//    private final BigDecimal H2_LON = new BigDecimal("2.15250000");   // Clinic Barcelona
//    private final BigDecimal H2_LAT = new BigDecimal("41.38790000");
//    
//    private final BigDecimal H3_LON = new BigDecimal("-5.97730000");  // Virgen del Rocío
//    private final BigDecimal H3_LAT = new BigDecimal("37.36190000");
//    
//    private final BigDecimal H4_LON = new BigDecimal("-0.35441000");  // La Fe
//    private final BigDecimal H4_LAT = new BigDecimal("39.45800000");
//    
//    private final BigDecimal H5_LON = new BigDecimal("-8.41600000");  // CHU A Coruña
//    private final BigDecimal H5_LAT = new BigDecimal("43.37350000");
//
//    @Test
//    void testDuration_H1_to_H2() {
//        double d = service.getDurationBetweenTwoHospitalPoints(H1_LON, H1_LAT, H2_LON, H2_LAT);
//        System.out.println("Duración H1 (La Paz) -> H2 (Clínic): " + d);
//    }
//
//    @Test
//    void testDuration_H1_to_H3() {
//        double d = service.getDurationBetweenTwoHospitalPoints(H1_LON, H1_LAT, H3_LON, H3_LAT);
//        System.out.println("Duración H1 (La Paz) -> H3 (Virgen del Rocío): " + d);
//    }
//
//    @Test
//    void testDuration_H2_to_H3() {
//        double d = service.getDurationBetweenTwoHospitalPoints(H2_LON, H2_LAT, H3_LON, H3_LAT);
//        System.out.println("Duración H2 (Clínic) -> H3 (Virgen del Rocío): " + d);
//    }
//
//    @Test
//    void testDuration_H3_to_H4() {
//        double d = service.getDurationBetweenTwoHospitalPoints(H3_LON, H3_LAT, H4_LON, H4_LAT);
//        System.out.println("Duración H3 (Virgen del Rocío) -> H4 (La Fe): " + d);
//    }
//
//    @Test
//    void testDuration_H4_to_H5() {
//        double d = service.getDurationBetweenTwoHospitalPoints(H4_LON, H4_LAT, H5_LON, H5_LAT);
//        System.out.println("Duración H4 (La Fe) -> H5 (A Coruña): " + d);
//    }
//    
//    @Test
//    void testDuration_H1_to_H5() {
//        double d = service.getDurationBetweenTwoHospitalPoints(H1_LON, H1_LAT, H5_LON, H5_LAT);
//        System.out.println("Duración H1 (La Paz) -> H5 (A Coruña): " + d);
//    }
//}
