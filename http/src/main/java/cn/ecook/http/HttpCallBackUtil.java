package cn.ecook.http;

import com.lzy.okgo.model.Response;
import com.socks.library.KLog;

/**
 * @author ciba
 */
public class HttpCallBackUtil {
    private static final String TAG = "HTTP_CALL_BACK";
    private static final int CODE200 = 200;

    private HttpCallBackUtil(){}

    /**
     * 访问网络数据成功
     *
     * @param url
     * @param response ： 回传数据
     * @param callBack ： 回调
     */
    public static <T> void onNextCallBack(String url, Response<String> response, HttpCallBack<T> callBack) {
        if (callBack == null) {
            return;
        }
        String body = response.body();
        KLog.e(TAG, url + ":::::::::: body :::::::::::" + body);
        if (CODE200 != response.code()) {
            onErrorCallBack(url, response.code(), "服务器异常", callBack);
            return;
        }
        if (callBack.cls == null) {
            callBack.onError(HttpCode.EXCEPTION_OTHER, "数据异常");
            return;
        }
        if (callBack.cls == String.class) {
            callBack.onSuccess((T) body);
            return;
        }
        T t = null;
        try {
            t = (T) GsonInstance.instance().fromJson(body, callBack.cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (t == null) {
            onErrorCallBack(url, HttpCode.EXCEPTION_DATA_PARSE, "数据解析异常", callBack);
        } else {
            callBack.onSuccess(t);
        }
    }

    /**
     * 访问网络错误
     *
     * @param url
     * @param code     ： 错误码
     * @param errorMsg ： 错误信息
     * @param callBack ： 回调
     */
    public static <T> void onErrorCallBack(String url, int code, String errorMsg, HttpCallBack<T> callBack) {
        KLog.e(TAG, url + " :::::::::: code ::::::::::: " + code + " ::::::::::: errorMsg ::::::::::: " + errorMsg);
        if (callBack == null) {
            return;
        }
        callBack.onError(code, errorMsg);
    }
}
