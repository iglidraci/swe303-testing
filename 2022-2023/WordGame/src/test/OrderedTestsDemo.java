package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class OrderedTestsDemo {
    @Test
    @Order(3)
    void validValues() {
        System.out.println("perform assertions against valid values");
        assertTrue(true);
    }
    @Test
    @Order(2)
    void emptyValues() {
        System.out.println("perform assertions against empty values");
        assertTrue(true);
    }
    @Test
    @Order(1)
    void nullValues() {
        System.out.println("perform assertions against null values");
        assertTrue(true);
    }

}