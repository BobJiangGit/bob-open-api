package org.bob.open.api.constant;

/**
 * Created by Bob Jiang on 2017/3/1.
 */
public enum ApiCodeEnum {

    API_UNKNOWN(ApiCodeInfo.API_UNKNOWN, "unknown"),
    API_SUCCESS(ApiCodeInfo.API_SUCCESS, "success"),
    API_BAD_REQUEST(ApiCodeInfo.API_BAD_REQUEST, "bad Request"),
    API_NOT_FOUND(ApiCodeInfo.API_NOT_FOUND, "api not found"),
    API_ERROR(ApiCodeInfo.API_ERROR, "api error"),

    API_SYS_ERR(ApiCodeInfo.API_SYS_ERR, "system error"),
    API_PARAM_ERR(ApiCodeInfo.API_PARAM_ERR, "params error"),
    API_PARAM_NULL(ApiCodeInfo.API_PARAM_NULL, "params is null"),
    API_DATA_ERR(ApiCodeInfo.API_DATA_ERR, "system data error"),
    API_SIGN_ERR(ApiCodeInfo.API_SIGN_ERR, "sign error"),
    API_DATA_NOT_EXIST(ApiCodeInfo.API_DATA_NOT_EXIST, "data not exist"),
    ;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    ApiCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
