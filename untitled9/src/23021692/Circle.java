public class Circle {
    private double radius;
    private String color;
    protected static final double PI = 3.14;

    public Circle() {
    }

    /**
     * set.
     *
     * @param radius var
     */
    public Circle(double radius) {
        this.radius = radius;
    }

    ;

    /**
     * set.
     *
     * @param radius var
     * @param color  var
     */
    public Circle(double radius, String color) {
        this.radius = radius;
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getArea() {
        return PI * this.radius * this.radius;
    }

    /**
     * override.
     *
     * @return string
     */
    @Override
    public String toString() {
        return "Circle[radius=" + getRadius() + ",color=" + getColor() + "]";
    }

}
