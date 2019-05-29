package com.example.shikimoriclient.BackEnd.api.user;

import com.example.shikimoriclient.BackEnd.dao.UserRate;
import com.example.shikimoriclient.BackEnd.dao.user.User;
import com.example.shikimoriclient.BackEnd.dao.user.UserCredentials;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Users {
    String AUTHENTICATION_URL = "https://shikimori.one/oauth/authorize?client_id=0634026dff61432521366af450848abaf9c19d732d83d533f0303f53ff517465&redirect_uri=urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob&response_type=code";
    String GRANT_TYPE = "authorization_code";
    String CLIENT_ID = "0634026dff61432521366af450848abaf9c19d732d83d533f0303f53ff517465";
    String CLIENT_SECRET = "3b8d9b5c4c2032c2d72a23b8e538237eae8c0c791c311e4eb8f5abbdf5308696";
    String REDIRECT_URL = "urn:ietf:wg:oauth:2.0:oob";

    @FormUrlEncoded
    @Headers("Users-Agent: ShikiClient")
    @POST("/oauth/token")
    Call<UserCredentials> getUserCredentials(@Field("grant_type") String grantType, @Field("client_id") String clientId, @Field("client_secret") String clientSecret, @Field("code") String code, @Field("redirect_uri") String redirectUrl);

    @GET("/api/users/whoami")
    Call<User> getUser(@Header("Authorization") String authorization);


    @Headers("Content-Type: application/json")
    @POST("/api/v2/user_rates")
    Call<Void> addUserRate(@Body HashMap<String, Object> body, @Header("Authorization") String authorization);
}
