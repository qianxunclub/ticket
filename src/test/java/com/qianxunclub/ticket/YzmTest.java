package com.qianxunclub.ticket;

import com.qianxunclub.ticket.util.CaptchaImageForPy;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.InputStream;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhangbin
 * @date 2019-06-27 16:50
 * @description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class YzmTest {

    @Autowired
    private CaptchaImageForPy captchaImageForPy;

    @Test
    public void yz() throws Exception {
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
        String r = captchaImageForPy.get(inputStream);
        if(StringUtils.isEmpty(r)){
            inputStream = process.getErrorStream();
            r = captchaImageForPy.get(inputStream);
        }
        System.out.println(r);
    }
}
