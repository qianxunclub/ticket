package com.qianxunclub.ticket;

import com.qianxunclub.ticket.config.Config;
import com.qianxunclub.ticket.util.CaptchaImageForPy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.junit.runner.RunWith;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
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
@Slf4j
public class YzmTest {

    @Autowired
    private CaptchaImageForPy captchaImageForPy;
    @Autowired
    private Config config;

    @Test
    public void yz() throws Exception {
        Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site", "false");
        props.put("python.home", " C:/Users/Administrator/AppData/Local/Programs/Python/Python37/Lib");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interp = new PythonInterpreter();
        interp.execfile("D:/Jojo/ticket/python/main.py");
        PyFunction function = (PyFunction) interp.get("main", PyFunction.class);
        PyObject pyobject = function.__call__(new PyString("D:/Jojo/ticket/temp/index.jpg"));
        System.out.println(pyobject);
    }

    @Test
    public void textPython(){
        Process proc;
        try {
//            proc = Runtime.getRuntime().exec("python D:/Jojo/ticket/python/main.py  D:/Jojo/ticket/temp/index.jpg");// 执行py文件
            //用输入输出流来截取结果`
            ProcessBuilder builder = new ProcessBuilder("python","D:/Jojo/ticket/python/main.py","D:/Jojo/ticket/temp/index.jpg");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
//        String result = interp.exec(" D:/Jojo/ticket/python/main.py  D:/Jojo/ticket/temp/index.jpg");
//            String[] cmd = new String[]{"cmd", "/c", "cd " + System.getProperty("user.dir") + "/" + config.getPythonPath()  + " &  set PYTHONIOENCODING=UTF-8 & python main.py " + "..\\temp\\index.jpg"};
//            process = runtime.exec(cmd);

}
