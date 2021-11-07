package org.hbrs.se1.ws21.uebung4.model.mitarbeiter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Expertise implements Iterable<String>, Serializable {
    private HashMap<Integer, String> expertiseLvl;

    public Expertise() {
        expertiseLvl = new HashMap<>();
    }

    public boolean setNewExpertise(int expertisLvl, String expertisLvlName) {
        if (this.expertiseLvl.size() < 4) {
            this.expertiseLvl.put(expertisLvl, expertisLvlName);
            return true;
        } else {
            return false;
        }
    }

    public String getExpertisName(int expertisLvl) {
        return this.expertiseLvl.getOrDefault(expertisLvl, "Das Expertise-Level ist nicht vorhanden");
    }

    public int getExpertisLvl(String expertisBezeichnung) {
        for (int i = 1; i < 4; i++) {
            if (this.expertiseLvl.getOrDefault(i, "").equals(expertisBezeichnung)) {
                return i;
            }
        }
        return -1;
    }

    public boolean updateExpertise(int expertisLvl, String expertisLvlName) {
        if (this.expertiseLvl.containsKey(expertisLvl)) {
            this.expertiseLvl.replace(expertisLvl, expertisLvlName);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index = 0;
            Object[] values = expertiseLvl.values().toArray();

            @Override
            public boolean hasNext() {
                return index < expertiseLvl.size() && index < 3;
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new IllegalArgumentException();
                }
                return (String)values[index++];
            }
        };
    }
}
