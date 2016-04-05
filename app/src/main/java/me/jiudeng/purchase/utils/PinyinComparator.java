package me.jiudeng.purchase.utils;

import java.util.Comparator;

import me.jiudeng.purchase.module.PurchaseData;

/**
 * Created by Yin on 2016/3/30.
 * 对ListView中的数据根据A-Z进行排序，
 * 前面两个if判断主要是将不是以汉字开头的数据放在后面
 *
 * 比较器 重写 compare（）方法，返回3种植
 *  负数： o1 < o2
 *   0	:  o1 = 02
 *  正数： o1 > 02
 */

public class PinyinComparator implements Comparator<PurchaseData>{

    public int compare(PurchaseData data1, PurchaseData data2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序

        String a1 = GetFirstAlp.getFirstAlpha(data1.getItemName()); //首字母
        String a2 = GetFirstAlp.getFirstAlpha(data2.getItemName());

        if (a1.equals("@")
                || a2.equals("#")) {
            return -1;
        } else if (a1.equals("#")
                || a2.equals("@")) {
            return 1;
        } else {
            return a1.compareTo(a2);
        }
    }


}
