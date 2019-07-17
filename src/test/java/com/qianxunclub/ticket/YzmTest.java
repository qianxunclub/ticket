package com.qianxunclub.ticket;

import com.qianxunclub.ticket.util.CaptchaImageForPy;

import org.apache.commons.lang.StringUtils;
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
        String os = System.getProperty("os.name");
        Process process;
        if (os.toLowerCase().startsWith("win")) {
            String[] cmd = new String[]{"cmd", "/c", " cd python  &  set PYTHONIOENCODING=utf-8 & python main.py " + "..\\temp\\index.jpg"};
            process = runtime.exec(cmd);
        } else {
            String bash = "python/run.sh ../temp/index.jpg";
            process = runtime.exec(bash);
        }
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        CaptchaImageForPy.PredictVO predictVO = new CaptchaImageForPy.PredictVO();
        while ((line = bufferedReader.readLine()) != null) {
            if (StringUtils.isEmpty(line.trim())) {
                continue;
            }
            System.out.println(line);
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
