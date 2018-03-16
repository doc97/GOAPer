import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class MainTest {
    @Test
    public void testMainHasGreeting() {
        Main testSubject = new Main();
        assertNotNull("Main should have a greeting", testSubject.getGreeting());
    }

}
