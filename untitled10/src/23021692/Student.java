public class Student extends Person {
    private String program;
    private double fee;
    private int year;

    /**
     * constructor.
     *
     * @param program var
     * @param name    var
     * @param address var
     * @param year    var
     * @param fee     var
     */
    public Student(String name, String address, String program, int year, double fee) {
        super(name, address);
        this.fee = fee;
        this.program = program;
        this.year = year;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * return String.
     *
     * @return string
     */
    @Override
    public String toString() {
        return String.format("Student[%s,program=%s,year=%d,fee=%.1f]",
                super.toString(),
                this.program,
                this.year,
                this.fee
        );
    }
}
