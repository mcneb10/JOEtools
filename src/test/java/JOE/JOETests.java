package JOE;


import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class JOETests {
    // JOE encoding is a long shot, so test it
    @Test
    public void testJOEEncoding() throws Exception {
        // This JSON file tries to test all types
        URL encodingTest = this.getClass().getResource("/testfiles/test.json");
        System.out.println(
                "--FILE 1--\n"+
                        new String(
                                Files.readAllBytes(
                                        Paths.get(
                                                encodingTest.toURI()
                                        )
                                )
                        )
        );
        JOEFile joeFile = Utils.getInstance().gson.fromJson(
                new InputStreamReader(
                        encodingTest.openStream()
                ),
                JOEFile.class
        );
        File tempFile = File.createTempFile("temp","joe");
        //tempFile.deleteOnExit();
        JOEConverter.internalJOEtoJOE(joeFile, tempFile);
        JOEFile joeFile1 = JOEConverter.joeToInternalJOE(tempFile);
        String JSON = JOEConverter.internalJOEToJSON(joeFile1);
        System.out.println("--FILE 2--\n"+JSON);
        System.out.println("Do these to json files have the same values (Order and formatting does not matter)?");
        System.out.print("Please answer true or false: ");
        Scanner scanner = new Scanner(System.in);
        Assert.assertTrue(scanner.nextBoolean());
    }
}