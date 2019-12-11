package com.qianxunclub.ticket.service.task;

import com.qianxunclub.ticket.service.GetJsCookieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class GetCookieTask
{

    private static String cookieUrl = null;

    private long endTime = 0;


    @Autowired
    private GetJsCookieService getJsCookieService;


    /**
     * 重试拿3次
     * @return
     */
    public static String getCookieUrl()
    {
        int i = 0;
        while (true)
        {
            if (GetCookieTask.cookieUrl != null  || i > 3 )
            {
                break;
            }
            i++;
            try
            {
                Thread.sleep(5 * 1000);
            }
            catch (InterruptedException e)
            {
                log.error("休眠出错");
            }
        }
        return GetCookieTask.cookieUrl;
    }

    /**
     * 一分钟获取一次url
     */
    @Scheduled(fixedDelay= 60 * 1000)
    public void getCookieUrlTask()
    {
        try
        {
            if ( StringUtils.isEmpty(cookieUrl) || isNeedUpdate())
            {

                String url = this.getJsCookieService.getCookieUrl(null,null);
                if (!StringUtils.isEmpty(url))
                {
                    log.info("获取到cookieUrl:" + url);
                    GetCookieTask.cookieUrl = url;
                    endTime = System.currentTimeMillis();
                }
            }
        }
        catch (Exception e)
        {
            log.error("获取cookieUrl出错",e);
        }
    }

    /**
     * 是否需要更新
     * @return
     */
    private boolean isNeedUpdate()
    {
        if (endTime == 0)
        {
            return true;
        }
        if (System.currentTimeMillis() - endTime > 30 * 60 * 1000)
        {
            return true;
        }
        return false;
    }
}
