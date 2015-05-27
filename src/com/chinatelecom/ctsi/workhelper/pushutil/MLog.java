package com.chinatelecom.ctsi.workhelper.pushutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.util.Log;

public class MLog {
	private static final Logger log = LoggerFactory.getLogger(MLog.class);

	public static void log(String tag, String message) {

		log.debug("Status:" + "exception" + " tag:" + tag + " message:"
				+ message);
		Log.d(tag, message);
	}
}
