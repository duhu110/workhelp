package com.chinatelecom.ctsi.workhelper.protocol.base;

public interface BaseListener {
    void onTimeout();

    void onUnavaiableNetwork();

    void onServerException(String message);

    void onPrev();
    
    void onSuccess();


}
