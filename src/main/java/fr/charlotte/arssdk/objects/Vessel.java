package fr.charlotte.arssdk.objects;

public class Vessel {

    private String name;
    private String vesselID;
    private String coID;
    private String template;
    private String defaultReport;

    public Vessel(String name, String vesselID, String coID, String template, String defaultReport) {
        this.name = name;
        this.vesselID = vesselID;
        this.coID = coID;
        this.template = template;
        this.defaultReport = defaultReport;
    }

    public String getDefaultReport() {
        return defaultReport;
    }

    public String getTemplate() {
        return template;
    }


    public String getName() {
        return name;
    }

    public String getCoID() {
        return coID;
    }

    public String getVesselID() {
        return vesselID;
    }

}
