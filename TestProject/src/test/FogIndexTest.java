import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FogIndexTest {

    @Test
    public void test1() throws IOException {
        File file = new File("src\\test\\FogIndexTestText");
        FogIndex fi = new FogIndex(file);
        double fiResult = fi.getFogIndex();
        assertEquals(13.275213675213676, fiResult);
    }

}