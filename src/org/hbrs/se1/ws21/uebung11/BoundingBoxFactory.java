package org.hbrs.se1.ws21.uebung11;

public class BoundingBoxFactory {
    public static MyPrettyRectangle getboundingBox(MyPrettyRectangle[] recs){
        for (int index = 0; index < recs.length; index++) {
            if(recs[index].contains(recs[index+1])){
                return recs[index];
            }
        }
        return null;
    }
}
