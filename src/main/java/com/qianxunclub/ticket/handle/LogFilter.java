package com.qianxunclub.ticket.handle;

import com.qianxunclub.ticket.model.LoggerMessage;
import com.qianxunclub.ticket.util.LogUtil;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author zhangbin
 * @date 2019-07-01 14:21
 * @description: TODO
 */
@Component
public class LogFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        LoggerMessage loggerMessage = new LoggerMessage(event.getMessage(), DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())), event.getThreadName(), event.getLoggerName(), event.getLevel().levelStr);
        // LogUtil.push(loggerMessage.toString());
        return FilterReply.ACCEPT;
    }
}
