package org.ning.EasyAndroid.utils;

import android.app.Service;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

/**
 * 设备信息
 * 
 * @author 颜宁<br>
 *         2016年3月22日下午4:13:02<br>
 */
public class DeviceUtils {
	/**
	 * 设备序列号
	 */
	private static String SerialNumber = null;

	/**
	 * 获取设备序列号
	 * 
	 * @author 颜宁<br>
	 *         2016年3月22日下午4:13:19<br>
	 * @return
	 */
	public static String getSerialNumber(@NonNull Context context) {
		if (StringUtils.isEmpty(SerialNumber)) {
			// 获取手机的序列号
			String imie = ((TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE)).getDeviceId();
			// 获取平板序列号
			String serialNo = android.os.Build.SERIAL;
			// 获取mac地址
			WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			String mac = wm.getConnectionInfo().getMacAddress();
			if (!StringUtils.isEmpty(imie)) {
				SerialNumber = imie;
			} else if (!StringUtils.isEmpty(serialNo) && !serialNo.equals("unknown")) {
				SerialNumber = serialNo;
			} else if (!StringUtils.isEmpty(mac)) {
				SerialNumber = mac;
			}
		}
		return SerialNumber;
	}

	private static TelephonyManager telephonyManager;

	private static TelephonyManager getTelephonyManager(@NonNull Context context) {
		if (telephonyManager == null) {
			telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}
		return telephonyManager;
	}

	/**
	 * 获取电话号码
	 * 
	 * @author 颜宁<br>
	 *         2016年3月24日上午10:11:25<br>
	 * @param context
	 * @return
	 */
	public static String getNativePhoneNumber(@NonNull Context context) {
		String NativePhoneNumber = null;
		NativePhoneNumber = getTelephonyManager(context).getLine1Number();
		if(NativePhoneNumber!=null) {
			int length = NativePhoneNumber.length();
			if (length > 11) {
				NativePhoneNumber = NativePhoneNumber.substring(length - 11);
			}
		}
		return NativePhoneNumber;
	}

	/**
	 * 获取手机服务商信息(这里目前只支持中国境内的运营商)
	 * 
	 * @author 颜宁<br>
	 *         2016年3月24日上午10:11:30<br>
	 * @param context
	 * @return
	 */
	public static String getSIMProvidersName(@NonNull Context context) {
		/**
		 * 国际移动用户识别码
		 */
		String IMSI;
		String ProvidersName = "N/A";
		try {
			IMSI = getTelephonyManager(context).getSubscriberId();
			// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
			System.out.println(IMSI);
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
				ProvidersName = "中国移动";
			} else if (IMSI.startsWith("46001")) {
				ProvidersName = "中国联通";
			} else if (IMSI.startsWith("46003")) {
				ProvidersName = "中国电信";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ProvidersName;
	}
}
