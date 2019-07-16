package com.qianxunclub.ticket;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author zhangbin
 * @date 2019-07-15 17:23
 * @description: TODO
 */
public class CallJsTest {

    @Test
    public void callJs() throws Exception {

        StringBuffer sb = new StringBuffer();

        FileReader reader = new FileReader(ResourceUtils.getFile("classpath:common.js"));
        BufferedReader br = new BufferedReader(reader);

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new StringReader(sb.toString()));
        Invocable invocable = (Invocable) engine;
        String scriptResult = (String) invocable.invokeFunction("Test");
        System.out.println(scriptResult);
    }
}
