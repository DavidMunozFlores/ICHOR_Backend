package com.erguidos.ichor.utils;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import com.erguidos.ichor.enums.BloodType;

public final class IchorUtils {
    private static final Random RANDOM = new Random();

    private static final String[] FIRST_NAMES = {
        "Alejandro", "Lucía", "Javier", "Sofía", "Mateo", 
        "María", "David", "Elena", "Carlos", "Carmen",
        "Juan", "Pedro", "Ana", "Miguel",
        "Hugo", "Martín", "Lucas", "Leo", "Daniel", 
        "Manuel", "Pablo", "Álvaro", "Adrián", "Diego",
        "Alba", "Julia", "Paula", "Emma", "Daniela", 
        "Sara", "Martina", "Lara", "Irene", "Laura"
    };

    private static final String[] SURNAMES = {
        "García", "Rodríguez", "González", "Fernández", "López", 
        "Martínez", "Sánchez", "Pérez", "Gómez", "Martín",
        "Jiménez", "Ruiz", "Hernández", "Diaz", "Moreno", 
        "Muñoz", "Álvarez", "Romero", "Alonso", "Gutiérrez",
        "Navarro", "Torres", "Domínguez", "Ramos", "Vázquez",
        "Ramírez", "Serrano", "Blanco", "Molina", "Morales"
    };
    
    private static final String DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

    private static final Double HEIGHT_MIN = 100.0d;
    private static final Double HEIGHT_MAX = 250.0d;
    private static final Double WEIGHT_MIN =  25.0d;
    private static final Double WEIGHT_MAX = 400.0d;
    
    
    private static <T> T getRandomFromArray(T[] array) {
        return array[IchorUtils.RANDOM.nextInt(array.length)];
    }
    
    public static String randomName() {
        return IchorUtils.getRandomFromArray(IchorUtils.FIRST_NAMES);
    }
    
    public static String randomSurname() {
        return IchorUtils.getRandomFromArray(IchorUtils.SURNAMES);
    }
    
    public static String randomFullName() {
        String firstName = IchorUtils.randomName();
        String fatherSurname = IchorUtils.getRandomFromArray(IchorUtils.SURNAMES);
        String motherSurname = IchorUtils.getRandomFromArray(IchorUtils.SURNAMES);
        
        while (motherSurname.equals(fatherSurname)) {
            motherSurname = IchorUtils.getRandomFromArray(IchorUtils.SURNAMES);
        }
        
        return firstName + " " + fatherSurname + " " + motherSurname;
    }
    
    public static List<String> generateFullNames(int count) {
        return IntStream.range(0, count)
                        .mapToObj(i -> IchorUtils.randomFullName())
                        .toList();
    }
    
    public static BloodType randomBloodType() {
        return IchorUtils.getRandomFromArray(BloodType.values());
    }
    
    public static String randomInternalID() {
        return String.format(
            "PAC-%8s",
            UUID.randomUUID().toString().substring(0, 8)
        )
        .replace(" ", "0")
        .toUpperCase();
    }
    
    public static String randomIdentification() {
        int number = IchorUtils.RANDOM.nextInt(100_000_000);
        
        int letterIndex = number % 23;
        char controlLetter = IchorUtils.DNI_LETTERS.charAt(letterIndex);
        
        return String.format("%08d%c", number, controlLetter);
    }
    
    public static Double randomHeight() {
        return IchorUtils.RANDOM.nextDouble(HEIGHT_MIN, HEIGHT_MAX);
    }
    
    public static Double randomWeight() {
        return IchorUtils.RANDOM.nextDouble(WEIGHT_MIN, WEIGHT_MAX);
    }
}
