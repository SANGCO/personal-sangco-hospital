package dev.sangco.hospital.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberGeneratorTest {

    @Test
    public void substring_test() {
        String a = "202100000";
        String b = "345";
        String c = a.substring(0, a.length() - b.length());
        System.out.println(c);
        System.out.println(c + b);

    }

}