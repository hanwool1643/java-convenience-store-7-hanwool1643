package store.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import store.common.constants.ErrorConstants;

public class FileReader {

    public static Scanner readFile(String address) {
        File file = new File(address);
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(ErrorConstants.FILE_NOT_FOUND);
        }
    }
}
