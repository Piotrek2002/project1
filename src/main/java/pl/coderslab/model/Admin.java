package pl.coderslab.model;

public class Admin {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private int superadmin;
    private int enable;

    public Admin(String first_name, String last_name, String email, String password, int superadmin, int enable) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.superadmin = superadmin;
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Admin[" +
                "id= " + id +
                ", first_name= " + first_name +
                ", last_name= " + last_name +
                ", email= " + email +
                ", password= " + password +
                ", superadmin= " + superadmin +
                ", enable= " + enable +
                "]";
    }

    public Admin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSuperadmin() {
        return this.superadmin;
    }

    public void setSuperadmin(int superadmin) {
        this.superadmin = superadmin;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
