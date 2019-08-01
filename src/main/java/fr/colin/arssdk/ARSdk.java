package fr.colin.arssdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import fr.colin.arssdk.objects.User;
import fr.colin.arssdk.objects.Vessel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ARSdk {

    public final static OkHttpClient HTTP_CLIENT = new OkHttpClient();

    public static final ARSdk DEFAULT_INSTANCE = new ARSdk("https://ars.nwa2coco.fr");

    public static final MediaType JSON = MediaType.parse("application/json");
    public final static Type VESSEL_TYPE = new TypeToken<ArrayList<Vessel>>() {
    }.getType();
    private String baseURL;

    public ARSdk(String url) {
        this.baseURL = url;
    }

    public void registerUser(User user) throws IOException {
        Request r = new Request.Builder().url(baseURL + "/register_user").post(RequestBody.create(JSON, new Gson().toJson(user))).build();
        HTTP_CLIENT.newCall(r).execute().body().string();
        //TODO Register user
    }

    public void postUserReport(User user) throws IOException {
        Request r = new Request.Builder().url(baseURL + "/submit").post(RequestBody.create(JSON, new Gson().toJson(user))).build();
        HTTP_CLIENT.newCall(r).execute().body().string();
    }

    public String syncronize(User user) throws IOException {
        Request r = new Request.Builder().url(baseURL + "/syncronize").post(RequestBody.create(JSON, new Gson().toJson(user))).build();
        return HTTP_CLIENT.newCall(r).execute().body().string();
    }

    public boolean isCo(String coid, String vesselid) throws IOException {
        Request r = new Request.Builder().url(baseURL + "/check_co").post(RequestBody.create(JSON, String.format("{\"vesselid\":\"%s\",\"coid\":\"%s\"}", vesselid, coid))).build();
        return Boolean.parseBoolean(HTTP_CLIENT.newCall(r).execute().body().string());
    }

    public boolean switchVessel(User user, String vesselid) throws IOException {
        user.setVesselid(vesselid);
        Request r = new Request.Builder().url(baseURL + "/switch_vessel").post(RequestBody.create(JSON, new Gson().toJson(user))).build();
        return Boolean.parseBoolean(HTTP_CLIENT.newCall(r).execute().body().string());
    }


    public ArrayList<Vessel> allVessels() throws IOException {
        Request r = new Request.Builder().url(baseURL + "/allvessels").get().build();
        String jsd = HTTP_CLIENT.newCall(r).execute().body().string();
        return new Gson().fromJson(jsd, VESSEL_TYPE);
    }

}
