package com.chinatelecom.ctsi.workhelper.model;

import java.util.HashMap;
import java.util.Map;

public class PhoneInfo {


    private String clientVersion;

	private String phoneName;
	private int screenWidth, screenHeight,density;
	private String sdkVersion;

    private String imsi;
    private String deviceId;
	private String imei;

    private String other;
	/**
	 * 
	 */
	public PhoneInfo() {
		super();
	}


	public Map<String,String> toMap(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("clientType","Android");
		map.put("clientVersion",clientVersion);
        map.put("imsi",imsi);
        map.put("phoneName",phoneName);
        map.put("screenWidth",""+screenWidth);
        map.put("screenHeight",""+screenHeight);
        map.put("sdkVersion",sdkVersion);
        map.put("other",deviceId);
        map.put("deviceId",deviceId);
        map.put("imei",imei);
        map.put("density",""+density);
		return map;
	}

    @Override
    public String toString() {
        return "PhoneInfo{" +
                "clientVersion='" + clientVersion + '\'' +
                ", phoneName='" + phoneName + '\'' +
                ", screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                ", sdkVersion='" + sdkVersion + '\'' +
                ", imsi='" + imsi + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", imei='" + imei + '\'' +
                ", density='" + density + '\'' +
                ", other='" + other + '\'' +
                '}';
    }

    public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

    public String getClientVersion() {
		return clientVersion;
	}
	public String getImsi() {
		return imsi;
	}
	public String getPhoneName() {
		return phoneName;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public String getSdkVersion() {
		return sdkVersion;
	}
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	} 
	
	
}
