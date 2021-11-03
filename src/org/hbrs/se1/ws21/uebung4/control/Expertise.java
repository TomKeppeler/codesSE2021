package org.hbrs.se1.ws21.uebung4.control;

import java.util.HashMap;

public class Expertise {
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

    public boolean updateExpertise(int expertisLvl, String expertisLvlName) {
        if (this.expertiseLvl.containsKey(expertisLvl)) {
            this.expertiseLvl.replace(expertisLvl, expertisLvlName);
            return true;
        } else {
            return false;
        }
    }
}
