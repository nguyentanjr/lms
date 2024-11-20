public class Cylinder extends Circle {
    private double height;

    /**
     * constructor.
     */
    public Cylinder() {
        super();
        this.height = 0;
    }

    /**
     * set.
     *
     * @param height var
     */
    public Cylinder(double height) {
        super();
        this.height = height;
    }

    /**
     * set.
     *
     * @param radius var
     * @param height var
     */
    public Cylinder(double radius, double height) {
        super(radius);
        this.height = height;
    }

    /**
     * set.
     *
     * @param radius var
     * @param height var
     * @param color  var
     */
    public Cylinder(double radius, double height, String color) {
        super(radius, color);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getVolume() {
        return super.getArea() * height;
    }

    @Override
    public String toString() {
        return "Cylinder[" + super.toString() + ",height=" + height + "]";
    }


}
