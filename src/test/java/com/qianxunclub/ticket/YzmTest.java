package com.qianxunclub.ticket;

import com.qianxunclub.ticket.util.CaptchaImageForPy;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author zhangbin
 * @date 2019-06-27 16:50
 * @description: TODO
 */
public class YzmTest {
    @Test
    public void getOs() {
        String os = System.getProperty("os.name");
        System.out.println(os);
    }


    @Test
    public void yz() throws Exception{
        Runtime runtime = Runtime.getRuntime();
        String bash = "python/run.sh ../temp/index.jpg";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            bash = "python\\run.bat ..\\temp\\index.jpg";
        }
        Process process = runtime.exec(bash);
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        CaptchaImageForPy.PredictVO predictVO = new CaptchaImageForPy.PredictVO();
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split("\\s");
            if (parts.length == 1) {
                predictVO.getQuestions().add(parts[0]);
            } else {
                CaptchaImageForPy.PictureVO pictureVO = new CaptchaImageForPy.PictureVO(Integer.valueOf(parts[1]), Integer.valueOf(parts[0]), parts[2]);
                predictVO.getPictures().add(pictureVO);
            }
        }
        System.out.println(predictVO);
    }
}
