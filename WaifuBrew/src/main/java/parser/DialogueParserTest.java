package parser;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DialogueParserTest {

    private static final String FILE_NAME = "src/main/java/resources/test.json";
    File doc = new File(FILE_NAME);
    private DialogueParser dp;

    @Before
    public void setUp() {
        dp = new DialogueParser(FILE_NAME);
        try {
            dp.parse();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(doc.getAbsolutePath());
            fail("Exception shouldn't have been thrown.");
        }
    }

    @Test
    public void testParseText() {
        String[] arr = {"Nico nico nii~", "Egao todokeru Yazawa Nico nico~", "Nico nii de oboeteru love Nico!",
                "Ah! Dame dame dame!!~ Nico nii wa minna mo yo~", "Gay.", "Nani????"};
//        arr.add(1, "Nico nico nii~");
//        arr.add(2, "Egao todokeru Yazawa Nico nico~");
//        arr.add(3, "Nico nii de oboeteru love Nico!");
//        arr.add(4, "Ah! Dame dame dame!!~ Nico nii wa minna mo yo~");
//        arr.add(5, "Gay.");
//        arr.add(6, "Nani????");

        assertArrayEquals(arr, dp.getDialogueList());
    }

    private void assertArrayEquals(String[] arr1, String[] arr2) {
        if (arr1.length != arr2.length) return;

        for (int i = 0; i < arr1.length; i++) {
            assertEquals(arr1[i], arr2[i]);
        }
    }
}