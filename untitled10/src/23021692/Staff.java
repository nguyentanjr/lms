public class Staff extends Person {
    private String school;
    private double pay;

    /**
     * contractor.
     *
     * @param name    var
     * @param address var
     * @param pay     var
     */
    public Staff(String name, String address, String school, double pay) {
        super(name, address);
        this.school = school;
        this.pay = pay;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    /**
     * toString.
     *
     * @return String for staff
     */
    @Override
    public String toString() {
        return String.format("Staff[%s,school=%s,pay=%.1f]",
                super.toString(),
                this.school,
                this.pay
        );
    }
}
