package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;

class DisabledTestsDemo {

	@Disabled("Disabled until bug #42 has been resolved")
    @Test
    void testWillBeSkipped() {
		assertTrue(false);
    }

    @Test
    void testWillBeExecuted() {
    	assertTrue(true);
    }
    

}
