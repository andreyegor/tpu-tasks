package Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import Serialize.Serializer;
import Serialize.JsonLike.JsonLikeSerializer;
import Serialize.Stream.InputStreamSerializer;
import Serialize.Stream.OutputStreamSerializer;

public class Testing {
    String FILE_NOT_FOUND_PREFIX = "🟥 File not found: ";
    String IO_EXEPTION_PREFIX = "🟥 IOException: ";
    String TEST_EXEPTION_PREFIX = "🟥 Test failed with exeption: ";
    String TEST_FAILED_PREFIX = "🟥 Test failed: ";
    String TEST_PASSED_PREFIX = "🟩 Test passed: ";

    String FILE_NAME = "figure.txt";

    Serializer serializer;
    OutputStreamSerializer oss;
    InputStreamSerializer iss;
    Boolean fnf = false;

    public Testing() {
        serializer = new JsonLikeSerializer();

        try {
            var fos = new FileOutputStream(FILE_NAME);
            var fis = new FileInputStream(FILE_NAME);
            oss = new OutputStreamSerializer(fos, serializer);
            iss = new InputStreamSerializer(fis, serializer);
        } catch (FileNotFoundException e) {
            fnf = true;
            System.out.println(FILE_NOT_FOUND_PREFIX + "Create testing");
        }
    }

    public boolean end() {
        try {
            oss.close();
            iss.close();
        } catch (IOException e) {
            System.out.println(IO_EXEPTION_PREFIX + "On stream close");
            return false;
        }
        var file = new java.io.File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        return true;
    }

    public boolean testSingleFigure() {
        var name_prefix = "Single  ";
        return testWriteRead(Generator.circle(), name_prefix + "circle") &&
                testWriteRead(Generator.ellipse(), name_prefix + "ellipse") &&
                testWriteRead(Generator.rectangle(), name_prefix + "rectangle") &&
                testWriteRead(Generator.triangle(), name_prefix + "triangle") &&
                testWriteRead(Generator.polygon(), name_prefix + "polygon");

    }

    public boolean testFiguresList() {
        var name_prefix = "List of ";
        return testWriteRead(Generator.figuresList(), name_prefix + "figures") &&
                testWriteRead(Generator.otherFiguresList(), name_prefix + "other figures");
    }

    public boolean testGroup() {
        return testWriteRead(Generator.flatGroup(), "Flat group") &&
                testWriteRead(Generator.nestedGroup(), "Nested group");
    }

    private boolean testWriteRead(Object figure, String name) {
        if (fnf) {
            System.out.println(FILE_NOT_FOUND_PREFIX + name);
            return false;
        }
        try {
            oss.write(figure);
            Object otherFigure = iss.read();
            if (!figure.equals(otherFigure)) {
                System.out.println(TEST_FAILED_PREFIX + name);
                return false;
            }
            System.out.println(TEST_PASSED_PREFIX + name);
            return true;
        } catch (IOException e) {
            System.out.println(IO_EXEPTION_PREFIX + name);
            return false;
        } catch (Exception e) {
            System.out.println(TEST_EXEPTION_PREFIX + name);
            return false;
        }

    }

}
