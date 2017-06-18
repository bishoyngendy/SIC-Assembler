package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on Wednesday 12/04/2017.
 */
public class ProgramLinesCollection implements Iterable<ProgramLine> {

    private List<ProgramLine> programLines;

    public ProgramLinesCollection() {
        this.programLines = new ArrayList<>();
    }

    @Override
    public Iterator<ProgramLine> iterator() {
        return programLines.iterator();
    }

    public void add(ProgramLine programLine) {
        programLines.add(programLine);
    }

    public void order() {
        programLines.sort(new Comparator<ProgramLine>() {
            @Override
            public int compare(ProgramLine o1, ProgramLine o2) {
                return o1.getIndex() - o2.getIndex();
            }
        });
    }
}
