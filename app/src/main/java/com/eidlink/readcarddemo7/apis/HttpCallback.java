package com.eidlink.readcarddemo7.apis;





import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class HttpCallback<T> implements Callback<T> {
    private final static String TAG = "HttpCallback";

    private final static int HTTP_OK = 200;
    private final static int HTTP_CREATED = 201;
    // private final static int HTTP_NOT_AUTH = 401;
    // private final static int HTTP_FORBIDDEN = 403;
    // private final static int HTTP_NOT_FOUND = 404;
    private final static int RES_NOT_AUTHORIZED = 99;

    private Runnable mSuccAction;
    private Runnable mFailAction;

    public HttpCallback() {

    }

    public HttpCallback(Runnable failAction) {
        mSuccAction = null;
        mFailAction = failAction;
    }

    public HttpCallback(Runnable succAction, Runnable failAction) {
        mSuccAction = succAction;
        mFailAction = failAction;
    }

    /**
     * @param t
     * @return true, expected data processed, false, not expected data
     */
    protected abstract boolean processData(T t);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        // server response error
        if (!response.isSuccessful()) {
            //Util.sendToast(Util.getString(R.string.server_failure, response.message(),
            //       String.valueOf(response.code())));
            return;
        }

        // process data
        if (processData(response.body())) {
            if (mSuccAction != null) {
                mSuccAction.run();
            }
        } else if (mFailAction != null) {
            mFailAction.run();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        //Util.sendToast(Util.getString(R.string.network_failure_msg));
        LUtils.e(TAG, "onFailure, exception:" + t);
        if (mFailAction != null) {
            mFailAction.run();
        }
    }
}
