package osoble.bloodhero.Models;

/**
 * Created by abdulahiosoble on 11/25/17.
 */

public class Appointment {
    private String id;
    private String bloodbank_id;
    private String bloodbank_name;
    private String time;
    private String date;

    public Appointment(String id, String bloodbank_id,String bloodbank_name, String date, String time) {
        this.id = id;
        this.bloodbank_id = bloodbank_id;
        this.bloodbank_name = bloodbank_name;
        this.date = date;
        this.time = time;
    }

    public Appointment() {
    }

    public String getBloodbank_id() {
        return bloodbank_id;
    }

    public String getTime(){
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getID(){
        return id;
    }

    public String getBloodbank_name() {
        return bloodbank_name;
    }
}
