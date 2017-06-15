package com.dashubio.stores;


import com.dashubio.actions.Action;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.HttpResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lgvalle on 02/08/15.
 */
public abstract class Store {

    private static Map<String, Store> mStoreMap = new HashMap<String, Store>();

    public synchronized static <T> T getInstance(Class<T> cls) {
        if (null == mStoreMap.get(cls.getName())) {
            try {
                mStoreMap.put(cls.getName(), (Store) cls.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return (T) mStoreMap.get(cls.getName());
    }

    //protected HttpResult mResp;
   // protected String mCurrentStatus;//status of the application and its business logic
    protected Throwable mThrowable;

//    public HttpResult getResp() {
//        return mResp;
//    }
//
//    public void setResp(HttpResult resp) {
//        this.mResp = resp;
//    }
//
//    public String getCurrentStatus() {
//        return mCurrentStatus;
//    }
//
//    public void setCurrentStatus(String currentStatus) {
//        this.mCurrentStatus = currentStatus;
//    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    public void setThrowable(Throwable throwable) {
        this.mThrowable = mThrowable;
    }

    protected void emitStoreChange() {
        Dispatcher.getInstance().emitChange(changeEvent());
    }

    protected abstract StoreChangeEvent changeEvent();

    public abstract void onAction(Action action);

    public abstract class StoreChangeEvent {
        String currentStatus;
        HttpResult httpResp;

        public StoreChangeEvent(String status) {
            this.currentStatus = status;
        }

        public StoreChangeEvent(String status, HttpResult resp) {
            this.currentStatus = status;
            this.httpResp = resp;
        }

        public String getCurrentStatus() {
            return currentStatus;
        }

        public HttpResult getHttpResp() {
            return httpResp;
        }
    }
}
