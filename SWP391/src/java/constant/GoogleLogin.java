/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import constant.IconstantLogin;
import java.io.IOException;
import model.entity.Google;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author datkh
 */
public class GoogleLogin {

    public static String getToken(String code) throws ClientProtocolException, IOException {

        String response = Request.Post(IconstantLogin.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", IconstantLogin.GOOGLE_CLIENT_ID)
                                .add("client_secret", IconstantLogin.GOOGLE_CLIENT_SECRET)
                                .add("redirect_uri", IconstantLogin.GOOGLE_REDIRECT_URI)
                                .add("code", code)
                                .add("grant_type", IconstantLogin.GOOGLE_GRANT_TYPE)
                                .build()
                )
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);

        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");

        return accessToken;

    }

    public static Google getUserInfo(final String accessToken) throws ClientProtocolException, IOException {

        String link = IconstantLogin.GOOGLE_LINK_GET_USER_INFO + accessToken;

        String response = Request.Get(link).execute().returnContent().asString();

        Google googlePojo = new Gson().fromJson(response, Google.class);

        return googlePojo;

    }
}
