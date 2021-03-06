package cn.ecook.http;

import android.app.Activity;
import android.content.Context;

/**
 * 访问网络回调
 *
 * @author ciba
 * @date 2017/9/25
 */

public abstract class HttpCallBack<T> {
    private String hashTag = null;
    public Class cls = null;

    /**
     * 访问网络回调
     *
     * @param context : 上下文(最好不要上传Application级别的上下文)，用于获取hashCode，addDisposable和clearDisposable使用
     */
    public HttpCallBack(Context context) {
        if (context != null && context instanceof Activity) {
            hashTag = context.hashCode() + "";
        }
        try {
            this.cls = GenericsUtils.getSuperClassGenricType(getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getHashTag() {
        return hashTag;
    }

    public void onStart() {

    }

    /**
     * 网络访问成功回调
     *
     * @param t ：实体类对象
     */
    public abstract void onSuccess(T t);

    /**
     * 网络访问失败
     *
     * @param code ：失败错误码 {@link HttpCode}
     * @param msg  : 错误信息
     */
    public abstract void onError(int code, String msg);
}
