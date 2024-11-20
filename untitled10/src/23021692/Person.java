public class Person {
    private String name;
    private String address;

    /**
     * con.
     *
     * @param name    var
     * @param address var
     */
    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * toString.
     *
     * @return string
     */
    public String toString() {
        return String.format("Person[name=%s,address=%s]",
                this.name,
                this.address
        );
    }
}
