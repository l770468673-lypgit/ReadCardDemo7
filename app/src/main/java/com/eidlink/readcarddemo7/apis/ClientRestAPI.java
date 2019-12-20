package com.eidlink.readcarddemo7.apis;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Define all rest API with server here Use open source Retrofit for http access
 * http://square.github.io/retrofit/
 */
public interface ClientRestAPI {

//    //查询雄迈账户信息	actionId=29
//    @FormUrlEncoded
//    @POST("guard/entranceGuard")
//    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
//    Call<Bean_QueryXMid> QueryXMID(@Field("actionId") String actionId,
//                                   @Field("gatewayId") String gatewayId);
        @FormUrlEncoded
        @POST("guard/entranceGuard")
        @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        Call<bean_person> QueryIdCard(@Field("actionId") String actionId,
                                      @Field("reqId") String reqId);

}
