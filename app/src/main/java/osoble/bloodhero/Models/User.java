package osoble.bloodhero.Models;

/**
 * Created by abdulahiosoble on 10/16/17.
 */

public class User {
    private String name;
    private String password;
    private String email;
    private String bloodType;
    private String id;
    private String region;
    private boolean status;

    public User(String name, String password, String email, String bloodType, String id, String region) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.bloodType = bloodType;
        this.id = id;
        this.region = region;
        status = false;

    }

    public User() {}

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBloodType() {
        return bloodType;
    }

    public boolean isPrivacy() {
        return status;
    }

    public void setPrivacy(boolean privacy) {
        this.status = privacy;
    }

    public String getRegion() {
        return region;
    }

    public String getId() {
        return id;
    }
}
