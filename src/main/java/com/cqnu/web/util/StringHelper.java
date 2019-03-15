package com.cqnu.web.util;

import java.util.*;

/**
 * @Description 字符串类型工具
 * @Author xzchen
 * @Date 2019/3/15 11:49
 * @Version 1.0
 **/
public class StringHelper {
    /**
     * 将重复的数据从String去除
     *
     * @param str
     *            String 源数据

     * @return String 去除后的数据
     */
    public static String delRepeatData(String str) {
        ArrayList al = new ArrayList();
        StringBuffer dataBuf = new StringBuffer();
        if (str != null && str.length() >= 1) {
            StringTokenizer st = new StringTokenizer(str, ",");
            while (st.hasMoreTokens()) {
                al.add(st.nextToken());
            }
            String singleDateArr[] = delRepeatData(al);
            if (singleDateArr != null) {
                for (int i = 0; i < singleDateArr.length; i++) {
                    dataBuf.append(singleDateArr[i]);
                    if (i != singleDateArr.length - 1) {
                        dataBuf.append(",");
                    }
                }
            }

        }
        return dataBuf.toString();
    }
    /**
     * 将重复的数据从ArrayList去除
     *
     * @param al 源数据
     *
     * @return String 去除后的数据
     */

    public static String[] delRepeatData(ArrayList al) {
        Set set = new HashSet(al);
        Object objs[] = set.toArray(new String[0]);
        return (String[]) objs;
    }
    /**
     * 将string类型封装成List
     * @param roleIds
     * @return
     */
    public static List<String> stringToList(String roleIds){
        String roleIdStr = delRepeatData(roleIds);
        String str[] = roleIdStr.split(",");
        return Arrays.asList(str);

    }
}
