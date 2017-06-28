package org.bob.open.api.dto.response;


import org.bob.open.api.constant.ApiCodeEnum;

import java.util.HashMap;

/**
 * Created by Bob Jiang on 2017/3/21.
 */
public class ResultResponse<V> extends HashMap<String, Object> {

    private static final long serialVersionUID = -2746096075868445005L;

    protected static final String CODE = "code";
    protected static final String MSG = "msg";
    protected static final String DATA = "data";
    protected String dataKey;

    public ResultResponse(int code, String msg) {
        setCode(code);
        setMsg(msg);
    }

    public ResultResponse(ApiCodeEnum apiCodeEnum) {
        setCode(apiCodeEnum.getCode());
        setMsg(apiCodeEnum.getMsg());
    }

    public ResultResponse() {
        this(ApiCodeEnum.API_UNKNOWN);
    }

    public ResultResponse(ApiCodeEnum apiCodeEnum, V data) {
        this(apiCodeEnum);
        setData(data);
    }

    public ResultResponse(ApiCodeEnum apiCodeEnum, String dataKey, V data) {
        this(apiCodeEnum);
        setData(dataKey, data);
    }

    public static ResultResponse success() {
        return new ResultResponse(ApiCodeEnum.API_SUCCESS);
    }

    public static <V> ResultResponse<V> success(V data) {
        return new ResultResponse(ApiCodeEnum.API_SUCCESS, data);
    }

    public static <V> ResultResponse<V> success(String key, V data) {
        return new ResultResponse(ApiCodeEnum.API_SUCCESS, key, data);
    }

    public static ResultResponse failed() {
        return new ResultResponse(ApiCodeEnum.API_SYS_ERR);
    }

    public static ResultResponse failed(String msg) {
        return new ResultResponse(ApiCodeEnum.API_SYS_ERR.getCode(), msg);
    }

    public static ResultResponse define(Integer code, String msg) {
        return new ResultResponse(code, msg);
    }

    public static ResultResponse define(ApiCodeEnum apiCodeEnum) {
        return new ResultResponse(apiCodeEnum);
    }

    public static <V> ResultResponse<V> define(ApiCodeEnum apiCodeEnum, String key, V data) {
        return new ResultResponse(apiCodeEnum, key, data);
    }

    public static <V> ResultResponse<V> define(ApiCodeEnum apiCodeEnum, V data) {
        return new ResultResponse(apiCodeEnum, data);
    }

    public ResultResponse define() {
        return this;
    }

    public int getCode() {
        return Integer.valueOf(get(CODE).toString()).intValue();
    }

    public void setCode(int code) {
        put(CODE, code);
    }

    public String getMsg() {
        return get(MSG).toString();
    }

    public void setMsg(String msg) {
        put(MSG, msg);
    }

    public V getData() {
        return get(dataKey) != null ? (V) get(dataKey) : null;
    }

    public void setData(V data) {
        setData(DATA, data);
    }

    public void setData(String dataKey, V data) {
        this.dataKey = dataKey;
        put(dataKey, data);
    }

    public boolean isSuccess() {
        return ApiCodeEnum.API_SUCCESS.getCode() == getCode();
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        value.append("{")
                .append(CODE).append(':').append(getCode())
                .append(',').append(MSG).append(":'").append(getMsg()).append('\'');

        if (dataKey != null) {
            value.append(',').append(dataKey).append(":'").append(getData()).append('\'');
        }
        value.append('}');
        return value.toString();
    }

}
