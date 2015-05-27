package com.chinatelecom.ctsi.workhelper.httpresponse;

/**
 * Created by apple on 14/11/21.
 */
public class BaseResponseInfo {
    public static String CODE_SUCCESS = "0";
    public static String CODE_FAIL_UNKUNWN = "9001";

    public static String CODE_CONNECT_FAIL = "9002";

    private String result;
    private String msg;
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess(){
        if("0".equals(result)){
            return true;
        }
        return false;
    }
    public boolean isRegisted(){
        if("1002".equals(result)){
            return false;
        }
        return true;
    }
    public static BaseResponseInfo getConnectFailResponse(){
        BaseResponseInfo baseResponseInfo = new BaseResponseInfo();
        baseResponseInfo.setResult(CODE_FAIL_UNKUNWN);
        baseResponseInfo.setMsg("未知错误");
        return baseResponseInfo;
    }
    public static BaseResponseInfo getUnknownErrorFailResponse(){
        BaseResponseInfo baseResponseInfo = new BaseResponseInfo();
        baseResponseInfo.setResult(CODE_CONNECT_FAIL);
        baseResponseInfo.setMsg("连接服务器失败");
        return baseResponseInfo;
    }
    public void setResultInfo(BaseResponseInfo responseInfo){
        this.result = responseInfo.getResult();
        this.msg = responseInfo.getMsg();
    }
}
