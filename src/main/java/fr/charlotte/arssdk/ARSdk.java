package fr.charlotte.arssdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import fr.charlotte.arssdk.objects.CheckVessel;
import fr.charlotte.arssdk.objects.CheckVesselName;
import fr.charlotte.arssdk.objects.User;
import fr.charlotte.arssdk.objects.auth.Login;
import fr.charlotte.arssdk.objects.auth.Register;
import fr.charlotte.arssdk.objects.Vessel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ARSdk {

    public final static OkHttpClient HTTP_CLIENT = new OkHttpClient();

    public static final ARSdk DEFAULT_INSTANCE = new ARSdk("https://ars.nwa2coco.fr", "https://auth.nwa2coco.fr");
    public static final MediaType JSON = MediaType.parse("application/json");
    public final static Type VESSEL_TYPE = new TypeToken<ArrayList<Vessel>>() {
    }.getType();
    private String baseURL;
    private String authURL;

    public ARSdk(String url, String authURL) {
        this.baseURL = url;
        this.authURL = authURL;
    }

    public void destroyUser(User user) throws IOException {
        Request r = new Request.Builder().url(baseURL + "/destroy_user").post(RequestBody.create(JSON, new Gson().toJson(user))).build();
        Request r2 = new Request.Builder().url(authURL + "/destroy_user").post(RequestBody.create(JSON, new Gson().toJson(user))).build();
        HTTP_CLIENT.newCall(r).execute().body().string();
        HTTP_CLIENT.newCall(r2).execute().body().string();
    }

    public void updateVesselTemplate(CheckVessel vessel) throws IOException {
        Request r = new Request.Builder().url(baseURL + "/update_template").post(RequestBody.create(JSON, new Gson().toJson(vessel))).build();
        HTTP_CLIENT.newCall(r).execute().body().string();
    }

    public void updateVesselDefault(CheckVesselName vesselName) throws IOException {
        Request r = new Request.Builder().url(baseURL + "/update_name").post(RequestBody.create(JSON, new Gson().toJson(vesselName))).build();
        HTTP_CLIENT.newCall(r).execute().body().string();
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
        user.setVesselID(vesselid);
        Request r = new Request.Builder().url(baseURL + "/switch_vessel").post(RequestBody.create(JSON, new Gson().toJson(user))).build();
        return Boolean.parseBoolean(HTTP_CLIENT.newCall(r).execute().body().string());
    }

    public User syncronizeUser(User user) throws IOException, UserNotFoundException {
        Request r = new Request.Builder().url(baseURL + "/syncronize_user").post(RequestBody.create(JSON, new Gson().toJson(user))).build();
        User sd = new Gson().fromJson(HTTP_CLIENT.newCall(r).execute().body().string(), User.class);
        if (sd.getName().equalsIgnoreCase("invalidID")) {
            throw new UserNotFoundException();
        }
        return sd;
    }

    public String[] registerUser(String name, String username, String password, String vaisseau, String email, String scc) throws IOException {
        Register register = new Register(name, username, password, vaisseau, email, scc);
        OkHttpClient o = new OkHttpClient();
        Request r = new Request.Builder().url(authURL + "/register").post(RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(register))).build();

        String lig = o.newCall(r).execute().body().string();
        if (lig.contains("Error while register, ")) {
            return new String[]{"false", lig};
        }
        return new String[]{"true", lig};
    }

    public String[] loginUser(String username, String password) throws IOException {

        Login login = new Login(username, password);
        OkHttpClient o = new OkHttpClient();
        Request r = new Request.Builder().url(authURL + "/login").post(RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(login))).build();
        String lig = o.newCall(r).execute().body().string();


        if (lig.contains("Error while login, ")) {
            return new String[]{"false", lig};
        }
        return new String[]{"true", lig};
    }

    public ArrayList<Vessel> allVessels() throws IOException {
        Request r = new Request.Builder().url(baseURL + "/allvessels").get().build();
        String jsd = HTTP_CLIENT.newCall(r).execute().body().string();
        return new Gson().fromJson(jsd, VESSEL_TYPE);
    }

}
