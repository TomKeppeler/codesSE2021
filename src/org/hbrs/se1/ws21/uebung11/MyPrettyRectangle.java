package org.hbrs.se1.ws21.uebung11;

public class MyPrettyRectangle {
    private double x1, x2, y1, y2;
    
    public MyPrettyRectangle(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
        //
		//    +-----------+   +---+
		//     |         r |   | s |
		// +---+---+---+   |   |   |
		// | l |   | m |   |   |   |
		// |   |   +---+   |   |   |
		// |   |       |   |   |   |
		// +---+-------+   |   +---+
		//     |           |
		//     +-----------+
    
    public boolean contains(MyPrettyRectangle rectangle){
        return this.x1 >= rectangle.x1 && this.y1 <= rectangle.y1;
    }

    public MyPoint getCenter(){
        return new MyPoint(((this.x2 - this.x1) / 2) + this.x1, ((this.y2 - this.y1) / 2) + this.y1);
    }
    public double getArea(){
        return (this.x2 - this.x1) * (this.y2 - this.y1);
    }
    public double getPerimeter(){
        return ((this.x2 - this.x1) * 2) + ((this.y2 - y1) * 2);
    }
    public boolean sameIdentity(MyPrettyRectangle rec){
        return this == rec;
    }
}
