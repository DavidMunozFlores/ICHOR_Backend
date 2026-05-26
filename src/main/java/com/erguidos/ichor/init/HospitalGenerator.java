package com.erguidos.ichor.init;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.repository.HospitalRepository;

@Component
public class HospitalGenerator {

    private final HospitalRepository hospitalRepository;
    
    HospitalGenerator(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }
    
    public void generate() {
        if (this.hospitalRepository.count() > 0) { return; }

        List<Hospital> hospitals = List.of(
            Hospital.builder()
                .setName("Hospital Universitario La Paz")
                .setAddress("Paseo de la Castellana, 261, Madrid")
                .setLongitude(new BigDecimal("-3.68913000"))
                .setLatitude(new BigDecimal("40.47743000"))
                .build(),
            Hospital.builder()
                .setName("Hospital Clinic de Barcelona")
                .setAddress("Carrer de Villarroel, 170, Barcelona")
                .setLongitude(new BigDecimal("2.15250000"))
                .setLatitude(new BigDecimal("41.38790000"))
                .build(),
            Hospital.builder()
                .setName("Hospital Universitario Virgen del Rocío")
                .setAddress("Avenida Manuel Siurot, s/n, Sevilla")
                .setLongitude(new BigDecimal("-5.97730000"))
                .setLatitude(new BigDecimal("37.36190000"))
                .build(),
            Hospital.builder()
                .setName("Hospital Universitario y Politécnico La Fe")
                .setAddress("Avinguda de Fernando Abril Martorell, 106, Valencia")
                .setLongitude(new BigDecimal("-0.35441000"))
                .setLatitude(new BigDecimal("39.45800000"))
                .build(),
            Hospital.builder()
                .setName("Complejo Hospitalario Universitario de A Coruña")
                .setAddress("Xubias de Arriba, 84, A Coruña")
                .setLongitude(new BigDecimal("-8.41600000"))
                .setLatitude(new BigDecimal("43.37350000"))
                .build()
        );

        this.hospitalRepository.saveAll(hospitals);
    }
}
