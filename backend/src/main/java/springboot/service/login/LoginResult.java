package springboot.service.login;

import springboot.domain.entity.AccessToken;
import springboot.service.utils.BaseResult;

public class LoginResult extends BaseResult {

    private AccessToken accessToken;

    LoginResult(String message, boolean isSuccess) {
        super(message, isSuccess);
    }

    LoginResult(String message, boolean isSuccess, AccessToken accessToken) {
        super(message, isSuccess);
        this.accessToken = accessToken;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }
}
