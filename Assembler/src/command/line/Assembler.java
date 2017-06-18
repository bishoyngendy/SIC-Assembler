package command.line;

import com.sun.media.sound.InvalidFormatException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/27/17.
 */
public interface Assembler {
    void saveData(File inputFile) throws FileNotFoundException, InvalidFormatException;
    
    void createObjectFile(String file) throws InvalidFormatException;

    void createListingFile(String file);
}
