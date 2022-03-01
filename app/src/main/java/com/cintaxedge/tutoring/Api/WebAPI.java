package com.cintaxedge.tutoring.Api;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.cintaxedge.tutoring.Models.CategoryItem;
import com.cintaxedge.tutoring.Models.ChangePassResponse;
import com.cintaxedge.tutoring.Models.FlashCard;
import com.cintaxedge.tutoring.Models.ForgetPasswordResponse;
import com.cintaxedge.tutoring.Models.Lesson;
import com.cintaxedge.tutoring.Models.LoginResponse;
import com.cintaxedge.tutoring.Models.PredictionResult;
import com.cintaxedge.tutoring.Models.Prefrences;
import com.cintaxedge.tutoring.Models.RegisterResponse;
import com.cintaxedge.tutoring.Models.AllTest;
import com.cintaxedge.tutoring.Models.Result;
import com.cintaxedge.tutoring.Models.ResultAll;
import com.cintaxedge.tutoring.Models.RightAnswers;
import com.cintaxedge.tutoring.Models.SubmitQuestionResponse;
import com.cintaxedge.tutoring.Models.ViewLesson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebAPI {
    public static final String BASE_URL = "https://officers.asvab-tutoring.com/app-api.php/";
    private final Application mApplication;

    private static final String TAG = "WebAPI";

    public WebAPI(Application mApplication) {
        this.mApplication = mApplication;
    }

    public static void Register(Context context, String fname, String lname, String email, String mobile, String password, BiConsumer<String, String> callback) {
        String url = BASE_URL;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "register")
                .addFormDataPart("first_name", fname)
                .addFormDataPart("last_name", lname)
                .addFormDataPart("email", email)
                .addFormDataPart("mobile", mobile)
                .addFormDataPart("password", password)
                .addFormDataPart("test_id", "4")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept("false", e.toString());
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        RegisterResponse registerResponse = new RegisterResponse();
                        Gson gson = new Gson();
                        registerResponse = gson.fromJson(res, RegisterResponse.class);


                        if (registerResponse.isSuccess()) {
                            callback.accept("true", registerResponse.getMessage());
                        } else {
                            callback.accept("false", registerResponse.getMessage());
                        }
                    }
                });

    }


    public static void login(Context context, String email, String password, BiConsumer<String, String> callback) {

        String url = BASE_URL;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "login")
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept("false", e.toString());
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        LoginResponse loginResponse = new LoginResponse();
                        Gson gson = new Gson();
                        loginResponse = gson.fromJson(res, LoginResponse.class);


                        if (loginResponse.isSuccess()) {

                            if (loginResponse.getUserDetails() == null) {
                                callback.accept("false", loginResponse.getMessage());
                            } else {
                                if (loginResponse.getUserDetails().getTest_id().equals("4")) {
                                    callback.accept("true", loginResponse.getMessage());
                                    Prefrences.saveUserDetails(context, loginResponse);
                                } else {
                                    callback.accept("false", "Please use your SIFT account login");
                                }
                            }
                        } else {
                            callback.accept("false", loginResponse.getMessage());
                        }
                    }
                });

    }


    public static void forgotpassword(String email, Consumer<String> callback) {
        String url = BASE_URL;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "forgot-password")
                .addFormDataPart("email", email)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept(e.toString());
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        ForgetPasswordResponse forgetPasswordResponse = new ForgetPasswordResponse();
                        Gson gson = new Gson();
                        forgetPasswordResponse = gson.fromJson(res, ForgetPasswordResponse.class);
                        if (forgetPasswordResponse.getMessage() == null) {
                            callback.accept("Something Went Wrong!");
                        } else {
                            callback.accept(forgetPasswordResponse.getMessage());
                        }

                    }
                });
    }

    public static void getCategories(Context context, BiConsumer<ArrayList<CategoryItem>, Boolean> callback) {
        String url = BASE_URL;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "category")
                .addFormDataPart("authorizationToken", Prefrences.getAuthToken(context))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {

                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {

                            ArrayList<CategoryItem> items = new ArrayList<>();

                            try {
                                String jsonData = response.body().string();
                                JSONObject Jobject = new JSONObject(jsonData);
                                JSONArray Jarray = Jobject.getJSONArray("list");
                                for (int i = 0; i < Jarray.length(); i++) {
                                    JSONObject object = Jarray.getJSONObject(i);

                                    Gson gson = new Gson();
                                    CategoryItem obj = gson.fromJson(object.toString(), CategoryItem.class);
                                    items.add(obj);

                                }
                                if (items.size() > 0) {
                                    callback.accept(items, true);
                                } else {
                                    callback.accept(items, false);
                                }


                            } catch (Exception e) {

                                callback.accept(null, false);
                            }

                        }


                    }
                });

    }

    public static void sendtutorRequest(Context context, String fname, String lname, String email, String mobile, String subject, String message, Consumer<Boolean> callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "contact_us")
                .addFormDataPart("first_name", fname)
                .addFormDataPart("last_name", lname)
                .addFormDataPart("email", email)
                .addFormDataPart("mobile", mobile)
                .addFormDataPart("subject", subject)
                .addFormDataPart("message", message)
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL)
                .method("POST", body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept(false);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();


                        Log.d(TAG, "onResponse: code:" + response.code());
                        if (response.code() == 200) {
                            callback.accept(true);
                        } else {
                            callback.accept(false);
                        }
                    }
                });

    }


    public static void changePassword(Context context, String password, BiConsumer<Boolean, String> callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "change-password")
                .addFormDataPart("authorizationToken", Prefrences.getAuthToken(context))
                .addFormDataPart("new_password", password)
                .addFormDataPart("conf_password", password)
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL)
                .method("POST", body)
                .build();


        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept(false, "Failed");
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        ChangePassResponse changePassResponse = new ChangePassResponse();
                        Gson gson = new Gson();
                        changePassResponse = gson.fromJson(res, ChangePassResponse.class);
                        if (changePassResponse.getMessage().equals("Password has been successfully changed.")) {
                            callback.accept(true, changePassResponse.getMessage());
                        } else {
                            callback.accept(false, changePassResponse.getMessage());
                        }
                    }
                });


    }


    public static void updateProfile(Context context, String email, String mobile, BiConsumer<String, String> callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "edit-profile")
                .addFormDataPart("authorizationToken", Prefrences.getAuthToken(context))
                .addFormDataPart("email", email)
                .addFormDataPart("mobile", mobile)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .method("POST", body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept("false", e.toString());
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        LoginResponse loginResponse = new LoginResponse();
                        Gson gson = new Gson();
                        loginResponse = gson.fromJson(res, LoginResponse.class);

                        if (loginResponse.isSuccess()) {
                            callback.accept("true", loginResponse.getMessage());
                        } else {
                            callback.accept("false", loginResponse.getMessage());
                        }
                    }
                });
    }


    public static void allLessons(Context context, String catid, BiConsumer<ArrayList<Lesson>, Boolean> callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "lessons")
                .addFormDataPart("authorizationToken", Prefrences.getAuthToken(context))
                .addFormDataPart("cat_id", catid)
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL)
                .method("POST", body)
                .build();


        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {

                            ArrayList<Lesson> lessons = new ArrayList<>();

                            try {
                                String jsonData = response.body().string();
                                JSONObject Jobject = new JSONObject(jsonData);
                                JSONArray Jarray = Jobject.getJSONArray("list");
                                for (int i = 0; i < Jarray.length(); i++) {
                                    JSONObject object = Jarray.getJSONObject(i);
                                    Gson gson = new Gson();
                                    Lesson obj = gson.fromJson(object.toString(), Lesson.class);
                                    lessons.add(obj);

                                }

                                if (lessons.size() > 0) {
                                    callback.accept(lessons, true);
                                } else {
                                    callback.accept(lessons, false);
                                }


                            } catch (Exception e) {

                                //callback.accept(null,false);
                            }

                        }


                    }
                });

    }

    public static void allTest(Context context, BiConsumer<JSONObject, Boolean> callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "all-test")
                .addFormDataPart("authorizationToken", Prefrences.getAuthToken(context))
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL)
                .method("POST", body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {

                            try {
                                String jsonData = response.body().string();
                                JSONObject Jobject = new JSONObject(jsonData);
//
//                                JSONArray Jarray = Jobject.getJSONArray("Math Skills");
//
//
//                                ArrayList<AllTest> data = new Gson().fromJson(Jarray.toString(), new TypeToken<List<AllTest>>(){}.getType());
//                                Log.d(TAG, "onResponse: datazz"+data);

//                                for (int i = 0; i < Jarray.length(); i++) {
//                                    JSONObject object     = Jarray.getJSONObject(i);
//                                    Gson gson= new Gson();
//                                    AllTest obj = gson.fromJson(object.toString(), AllTest.class);
//                                    tests.add(obj);
//                                }

                                if (Jobject != null) {
                                    callback.accept(Jobject, true);
                                } else {
                                    callback.accept(Jobject, false);
                                }


                            } catch (Exception e) {
                                callback.accept(null, false);
                            }

                        }


                    }
                });

    }

    public static void viewLesson(Context context, String cat_id, String lesson_id, BiConsumer<ViewLesson, Boolean> callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "lesson-view")
                .addFormDataPart("authorizationToken", Prefrences.getAuthToken(context))
                .addFormDataPart("cat_id", cat_id)
                .addFormDataPart("lesson_id", lesson_id)
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL)
                .method("POST", body)
                .build();


        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept(null, false);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        ViewLesson viewLesson = new ViewLesson();
                        Gson gson = new Gson();
                        viewLesson = gson.fromJson(res, ViewLesson.class);

                        if (viewLesson.isSuccess.equals("true")) {

                            callback.accept(viewLesson, true);
                        } else {
                            callback.accept(null, false);
                        }
                    }
                });

    }


    public static void submitAnswer(Context context, String cat_id, String lesson_id, String question_id, String answer_no, String total, BiConsumer<Boolean, String> callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "test")
                .addFormDataPart("authorizationToken", Prefrences.getAuthToken(context))
                .addFormDataPart("cat_id", cat_id)
                .addFormDataPart("lesson_id", lesson_id)
                .addFormDataPart("question_id", question_id)
                .addFormDataPart("answer_no", answer_no)
                .addFormDataPart("total", total)
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL)
                .method("POST", body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept(false, "Failed");
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        Log.d(TAG, "onResponse: answerresp:" + res);
                        Log.d(TAG, "onResponse:token: " + Prefrences.getAuthToken(context));
                        SubmitQuestionResponse submitQuestionResponse = new SubmitQuestionResponse();
                        Gson gson = new Gson();
                        submitQuestionResponse = gson.fromJson(res, SubmitQuestionResponse.class);
                        if (submitQuestionResponse.getMessage().equals("You have answered this question successfully.")) {
                            callback.accept(true, submitQuestionResponse.getPending_total());
                        } else {
                            callback.accept(false, submitQuestionResponse.getMessage());
                        }
                    }
                });
    }

    public static void getResults(Context context, String cat_id, String lesson_id, BiConsumer<Boolean, RightAnswers> callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "result")
                .addFormDataPart("authorizationToken", Prefrences.getAuthToken(context))
                .addFormDataPart("cat_id", cat_id)
                .addFormDataPart("lesson_id", lesson_id)
                .addFormDataPart("term", "")
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL)
                .method("POST", body)
                .build();


        Log.d(TAG, "getResults: prefid:" + Prefrences.getAuthToken(context));
        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept(false, null);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        Log.d(TAG, "onResponse: resp:" + res);
                        RightAnswers rightAnswers = new RightAnswers();
                        Gson gson = new Gson();
                        rightAnswers = gson.fromJson(res, RightAnswers.class);
                        callback.accept(true, rightAnswers);
                    }
                });


    }


    public static void getflashCardsdata(Context context, BiConsumer<Boolean, FlashCard> callback) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "knowledge-update")
                .addFormDataPart("authorizationToken", Prefrences.getAuthToken(context))
                .build();
        Request request = new Request.Builder()
                .url("https://officers.asvab-tutoring.com/app-api.php")
                .method("POST", body)
                .addHeader("Cookie", "PHPSESSID=ep74v9oo9rkig36dh7958lh3gg")
                .build();


        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept(false, null);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();
                        Log.d(TAG, "onResponse: resp:" + res);
                        FlashCard flashCard = new FlashCard();
                        Gson gson = new Gson();
                        flashCard = gson.fromJson(res, FlashCard.class);
                        callback.accept(true, flashCard);
                    }
                });

    }


    public static void getAllTestPracticeQues(Context context, String catid, String lessonId, BiConsumer<Boolean, ViewLesson> callback) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "practice-test")
                .addFormDataPart("authorizationToken", Prefrences.getAuthToken(context))
                .addFormDataPart("cat_id", catid)
                .addFormDataPart("lesson_id", lessonId)
                .build();
        Request request = new Request.Builder()
                .url("https://officers.asvab-tutoring.com/app-api.php")
                .method("POST", body)
                .build();
        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept(false, null);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();
                        Log.d(TAG, "onResponse: resp:" + res);
                        ViewLesson lssn = new ViewLesson();
                        Gson gson = new Gson();
                        lssn = gson.fromJson(res, ViewLesson.class);
                        callback.accept(true, lssn);
                    }
                });


    }


    public static void getAllResults(Context context, BiConsumer<Boolean, ResultAll> callback) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action", "results")
                .addFormDataPart("authorizationToken", Prefrences.getAuthToken(context))
                .build();
        Request request = new Request.Builder()
                .url("https://officers.asvab-tutoring.com/app-api.php")
                .method("POST", body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        callback.accept(false, null);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        String res = response.body().string();
                        Log.d(TAG, "onResponse: respsda:" + res);

                        ResultAll rslt = new ResultAll();
                        Gson gson = new Gson();
                        rslt = gson.fromJson(res, ResultAll.class);
                        callback.accept(true, rslt);
                    }
                });

    }

    public static void getPredictionResults(Context context ,BiConsumer<Boolean, PredictionResult> callback) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("action","view-prediction-result")
                .addFormDataPart("authorizationToken",Prefrences.getAuthToken(context))
                .addFormDataPart("cat_id","92")
                .build();
        Request request = new Request.Builder()
                .url("https://officers.asvab-tutoring.com/app-api.php")
                .method("POST", body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(final Call call, IOException e) {
                       // callback.accept(false, null);
                    }
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        String res = response.body().string();

                        PredictionResult rslt = new PredictionResult();
                        Gson gson = new Gson();
                        rslt = gson.fromJson(res, PredictionResult.class);
                        Log.d(TAG, "onResponse: rslt.getAfqtReport().getMath_Skills(); "+rslt.getAfqt_report().getMath_Skills());
                         callback.accept(true, rslt);
                    }
                });

    }

    }
