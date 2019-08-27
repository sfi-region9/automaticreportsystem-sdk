package fr.colin.arssdk.objects;

public class User {

    private String name;
    private String scc;
    private String vesselid;
    private String report;
    private String uuid;

    public User(String name, String scc, String vesselid, String report, String uuid) {
        this.name = name;
        this.scc = scc;
        this.vesselid = vesselid;
        this.report = report;
        this.uuid = uuid;
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

    public String getUuid() {
        return uuid;
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
