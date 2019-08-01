package fr.colin.arssdk.objects;

public class User {

    private String name;
    private String scc;
    private String vesselid;
    private String report;

    public User(String name, String scc, String vesselid, String report) {
        this.name = name;
        this.scc = scc;
        this.vesselid = vesselid;
        this.report = report;
    }

    public String getName() {
        return name;
    }

    public String getReport() {
        return report;
    }

    public void setVesselid(String vesselid) {
        this.vesselid = vesselid;
    }

    public String getVesselid() {
        return vesselid;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getScc() {
        return scc;
    }
}
