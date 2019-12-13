package com.qianxunclub.ticket.util;

import com.qianxunclub.ticket.config.Config;

import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-06-11 14:12
 * @description: TODO
 */
@Component
@AllArgsConstructor
@Slf4j
public class CaptchaImageForPy {

    private Config config;

    public String check(String base64String) {
        String filename = UUID.randomUUID() + ".jpg";
        File folder = new File("temp");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, filename);
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] data = decoder.decodeBuffer(base64String);
            IOUtils.copy(new ByteArrayInputStream(data), new FileOutputStream(file));
            Runtime runtime = Runtime.getRuntime();
            String os = System.getProperty("os.name");
            Process process;
            if (os.toLowerCase().startsWith("win")) {
                String[] cmd = new String[]{"cmd", "/c", "cd python  &  set PYTHONIOENCODING=" + System.getProperty("file.encoding") + " & python main.py " + "..\\temp\\" + filename};
                process = runtime.exec(cmd);
            } else {
                String bash = config.getPythonPath() + "/run.sh ../temp/" + filename;
                process = runtime.exec(bash);
            }
            process.waitFor();
            InputStream inputStream = process.getInputStream();
            String r = this.get(inputStream);
            if(StringUtils.isEmpty(r)){
                inputStream = process.getErrorStream();
                r = this.get(inputStream);
            }
            return r;
        } catch (Exception e) {
            log.error("", e);
            return "系统出错，联系QQ：960339491";
        } finally {
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }

    public String get(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, System.getProperty("file.encoding"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        PredictVO predictVO = new PredictVO();
        while ((line = bufferedReader.readLine()) != null) {
            if (StringUtils.isEmpty(line.trim())) {
                continue;
            }
            String[] parts = line.split("\\s");
            if (parts.length == 1) {
                predictVO.getQuestions().add(parts[0]);
            } else {
                PictureVO pictureVO = new PictureVO(Integer.valueOf(parts[1]), Integer.valueOf(parts[0]), parts[2]);
                predictVO.getPictures().add(pictureVO);
            }
        }
        return this.getResult(predictVO);
    }

    private String getResult(PredictVO predictVO) {
        final int[][][] offset = {
                {
                        {40, 77},
                        {112, 77},
                        {184, 77},
                        {256, 77},
                },
                {
                        {40, 149},
                        {112, 149},
                        {184, 149},
                        {256, 149},
                },
        };

        List<Integer> integerList = new ArrayList<>();
        for (String question : predictVO.getQuestions()) {
            for (PictureVO pictureVO : predictVO.getPictures()) {
                if (question.equalsIgnoreCase(pictureVO.getDesc())) {
                    int[] pic = offset[pictureVO.getY()][pictureVO.getX()];
                    integerList.add(pic[0]);
                    integerList.add(pic[1]);
                }
            }
        }
        return StringUtils.join(integerList, ",");
    }

    @Data
    public static class PredictVO {
        private List<String> questions = new ArrayList<>();
        private List<PictureVO> pictures = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    public static class PictureVO {
        private Integer x;
        private Integer y;
        private String desc;
    }
}


