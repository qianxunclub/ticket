package com.qianxunclub.ticket.util;

/**
 * @author zhangbin
 * @date 2019-06-04 09:55
 * @description: TODO
 */
public class CaptchaImage {

    private static final int margin = 72;

    private static Integer[] image(int positionNum) {
        int x = 48;
        int y = 38;
        if (4 < positionNum && positionNum <= 8) {
            y = y + margin;
            positionNum = positionNum - 4;
        }

        Integer[] position = new Integer[]{x + margin * (positionNum - 1), y};
        return position;
    }

    public static String position(String[] imagePosition) {
        String positions = "";
        for (String positionNum : imagePosition) {
            int n = Integer.parseInt(positionNum.trim());
            Integer[] position = CaptchaImage.image(n);
            positions += position[0] + "," + position[1] + ",";
        }
        return positions.substring(0, positions.length() - 1);
    }

    public static void main(String[] args) {
        System.out.println(CaptchaImage.position(new String[]{"1", "5", "8"}));
    }
}
