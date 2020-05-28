import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class FogIndexTest {

    @Test
    public void test1() throws IOException {
        File file = new File("src\\test\\FogIndexTestText\\TestText");
        FogIndex fi = new FogIndex(file);
        int fiResult = (int)fi.getFogIndex();
        assertEquals(13, fiResult);
    }
}