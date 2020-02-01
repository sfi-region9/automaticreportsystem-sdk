package fr.charlotte.arssdk.objects;

public class User {

    private String name;
    private String scc;
    private String vesselID;
    private String report;
    private String uuid;

    public User(String name, String scc, String vesselID, String report, String uuid) {
        this.name = name;
        this.scc = scc;
        this.vesselID = vesselID;
        this.report = report;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getReport() {
        return report;
    }

    public void setVesselID(String vesselID) {
        this.vesselID = vesselID;
    }

    public String getUuid() {
        return uuid;
    }

    public String getVesselID() {
        return vesselID;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getScc() {
        return scc;
    }
}
