package com.erguidos.ichor.init;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.repository.HospitalRepository;

@Component
public class HospitalGenerator {
    private final HospitalRepository hospitalRepository;
    private final Random random;
    
    HospitalGenerator(
        HospitalRepository hospitalRepository,
        Random random
    ) {
        this.hospitalRepository = hospitalRepository;
        this.random = random;
    }
    
    public void generate() {
        if (this.hospitalRepository.count() > 0) { return; }

        List<Hospital> hospitals = List.of(
            Hospital.builder()
                .setName("Hospital Universitario La Paz")
                .setAddress("Paseo de la Castellana 261, 28046 Madrid")
                .setLongitude(new BigDecimal("-3.68913000"))
                .setLatitude(new BigDecimal("40.47743000"))
                .build(),
            Hospital.builder()
                .setName("Hospital Clinic de Barcelona")
                .setAddress("Calle de Villarroel 170, 08036 Barcelona")
                .setLongitude(new BigDecimal("2.15250000"))
                .setLatitude(new BigDecimal("41.38790000"))
                .build(),
            Hospital.builder()
                .setName("Hospital Universitario Virgen del Rocío")
                .setAddress("Avenida Manuel Siurot s/n, 41013 Sevilla")
                .setLongitude(new BigDecimal("-5.97730000"))
                .setLatitude(new BigDecimal("37.36190000"))
                .build(),
            Hospital.builder()
                .setName("Hospital Universitario y Politécnico La Fe")
                .setAddress("Avenida de Fernando Abril Martorell 106, 46026 Valencia")
                .setLongitude(new BigDecimal("-0.35441000"))
                .setLatitude(new BigDecimal("39.45800000"))
                .build(),
            Hospital.builder()
                .setName("Complejo Hospitalario Universitario de A Coruña")
                .setAddress("Carretera de las Xubias 84, 15006 A Coruna")
                .setLongitude(new BigDecimal("-8.41600000"))
                .setLatitude(new BigDecimal("43.37350000"))
                .build(),
            Hospital.builder()
                .setName("Hospital General Universitario Gregorio Marañón")
                .setAddress("Calle del Doctor Esquerdo 46, 28007 Madrid")
                .setLongitude(new BigDecimal("-3.67050000"))
                .setLatitude(new BigDecimal("40.41830000"))
                .build(),
            Hospital.builder()
                .setName("Hospital Universitario Marqués de Valdecilla")
                .setAddress("Avenida de Valdecilla 25, 39008 Santander")
                .setLongitude(new BigDecimal("-3.82940000"))
                .setLatitude(new BigDecimal("43.45420000"))
                .build(),
            Hospital.builder()
                .setName("Hospital Universitario Miguel Servet")
                .setAddress("Paseo de Isabel la Catolica 1, 50009 Zaragoza")
                .setLongitude(new BigDecimal("-0.90150000"))
                .setLatitude(new BigDecimal("41.63610000"))
                .build()
        );

        this.hospitalRepository.saveAll(hospitals);
    }
    
    public Hospital getRandomHospital() {
        long totalHospitals = this.hospitalRepository.count();
        if (totalHospitals == 0) {
            return null;
        }
        int randomIndex = this.random.nextInt((int) totalHospitals);
        Page<Hospital> hospitalPage = this.hospitalRepository.findAll(PageRequest.of(randomIndex, 1));
        return hospitalPage.getContent().get(0);
    }
}