package org.ning.EasyAndroid.utils;

import android.content.Context;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /*
     * 用于获得一个数且带小数点后二位的小数
     */
    public static String getBigDecimal(String someDate) {
        return getBigDecimal(someDate, "0.00");
    }

    /*
     * 用于获得一个数且带小数点后二位的小数
     */
    public static String getBigDecimal(String someDate, String format) {
        String res = "";
        if (someDate != null) {
            if (someDate.equals("")) {
                res = "数据有误";
            } else if (Double.parseDouble(someDate) == 0 || someDate.equals("NaN")) {
                res = format;
            } else {
                BigDecimal num = new BigDecimal(someDate);
                DecimalFormat df = new DecimalFormat(format);
                res = df.format(num);
            }
        }
        return res;
    }

    /**
     * 判断空值
     *
     * @param value
     * @return
     * @author 颜宁<br>
     * 2016年3月24日上午8:53:37<br>
     */
    public static boolean isEmpty(String value) {
        // TODO Auto-generated method stub
        return value == null || value.length() == 0 || value.replaceAll(" ", "").length() == 0;
    }



    /**
     * 判断EditText是否为空值
     *
     * @param editText
     * @return
     * @author 颜宁<br>
     * 2016年3月24日上午8:53:37<br>
     */
    public static boolean isEditTextNotEmpty(EditText editText) {
        // TODO Auto-generated method stub
        return editText.getEditableText()!= null&&(!editText.getEditableText().toString().equals(""));
    }



    /**
     * 获取小数点之前的部分
     *
     * @param s
     * @return
     * @author 颜宁<br>
     * 2016年6月30日下午3:45:05<br>
     */
    public static String getBeforePoint(String s) {
        if (isEmpty(s)) {
            return s;
        }
        return "" + (int) Math.floor(Double.parseDouble(s));
    }

    /**
     * 去除最后位置的逗号
     *
     * @param value
     * @return
     * @author 颜宁<br>
     * 2016年6月21日上午10:09:04<br>
     */
    public static String removeLastIndexComma(String value) {
        return removeLastIndexComma(value, ",");
    }

    /**
     * 去除最后位置的符号
     *
     * @param value
     * @return
     * @author 颜宁<br>
     * 2016年6月21日上午10:09:04<br>
     */
    public static String removeLastIndexComma(String value, String removeChar) {
        int i = value.lastIndexOf(removeChar);
        if ((i > 0 && value.length() > 0) && i == value.length() - 1) {
            return value.substring(0, i);
        } else {
            return value + "";
        }
    }

    /*
     * 获取asset中的数据资源 陈亚军
     */
    public static String getAssetString(String asset, Context context) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(asset)));
            String line = null;
            StringBuilder builder = new StringBuilder();
            while (null != (line = bufferedReader.readLine())) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            bufferedReader = null;
        }
        return "";
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 判断一个字符串是不是正确的手机号
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        Pattern pattern = Pattern.compile("^(13[0-9]|15[0-9]|170|177|179|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 将金额按照逗号分隔
     *
     * @param money
     * @return
     */
    public static String getFormatMoney(String money) {
        String moneyStr = "";
        int len=2;
        if (money==null)return "0.00";
        if (!money.trim().equals("")) {
            money=0+money;
            if (money == null || money.length() < 1) {
                return "";
            }
            NumberFormat formater = null;
            double num = Double.parseDouble(money);
                formater = new DecimalFormat("###,###.##");
            moneyStr=formater.format(num);
//            // 先将
//            String[] str1 = money.split("\\.");
//            int length = str1.length;
//            String reverseStr = new StringBuilder(str1[0]).reverse().toString();
//            String strTemp = "";
//            for (int i = 0; i < reverseStr.length(); i++) {
//                if (i * 3 + 3 > reverseStr.length()) {
//                    strTemp += reverseStr.substring(i * 3, reverseStr.length());
//                    break;
//                }
//                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
//            }
//            if (strTemp.endsWith(",")) {
//                strTemp = strTemp.substring(0, strTemp.length() - 1);
//            }
//            // 将数字重新反转
//            String resultStr = new StringBuilder(strTemp).reverse().toString();
//            moneyStr = resultStr;
//            if (length > 1) {
//                String temp1 = str1[1];
//                int temp1Int = Long.parseInt(temp1);
//                if (temp1Int < 10) {
//                    moneyStr = moneyStr + ".0" + temp1Int;
//                } else {
//                    moneyStr = moneyStr + "." + temp1Int;
//                }
//            } else {
//                moneyStr = moneyStr + ".00";
//            }
        } else {
            return "";
        }
        return moneyStr;
    }

    public static boolean isNumeric(String str) {

        Pattern pattern = Pattern.compile("[0-9]*");

        Matcher isNum = pattern.matcher(str);

        if (!isNum.matches()) {

            return false;

        }

        return true;

    }
    /**
     * 判断是否包含中文字符
     * @param str
     * @return
     */
    public static boolean isContainsChinese(String str) {
        String regEx = "^[\\u4e00-\\u9fa5]*$";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str.trim());
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }
    /**
     * 判断是否包含大小写英文字符
     * @param str
     * @return
     */
    public static boolean isContainsEnglish(String str) {
        String regEx = "^[A-Za-z]+$";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str.trim());
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    /**
     * 判断是否包含網址
     * @param str
     * @return
     */
    public static boolean isContainsWebUrl(String str) {
        String regEx = "^http\\:\\/\\/.+$";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str.trim());
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }


}
