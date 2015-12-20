package kr.edcan.merror.Interface;

import kr.edcan.merror.data.User;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Marzin-Oh on 2015-12-20.
 */
public interface NetworkInterface {
    @POST("/auth/login")
    @FormUrlEncoded
    Call<User> userLogin(@Field("id") String id, @Field("password") String password);

    @POST("/auth/register")
    @FormUrlEncoded
    Call<User> userRegister(@Field("id") String id, @Field("password") String password,
                            @Field("name") String name, @Field("birthday") String birthday);

    @POST("/auth/loginValidate")
    @FormUrlEncoded
    Call<User> loginValidate(@Field("apikey") String apikey);

    @POST("/auth/countPerson")
    Call<String> countPerson();

}
