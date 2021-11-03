package org.hbrs.se1.ws21.uebung4.control;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class StoredPrintStream extends PrintStream {

    private ArrayList<String> printList = new ArrayList<>();

    public ArrayList<String> getPrintList() {
        return printList;
    }

    public StoredPrintStream(OutputStream out) {
        super(out);
    }

    @Override
    public void println(String line) {
        printList.add(line);
        super.println(line);
    }
}
