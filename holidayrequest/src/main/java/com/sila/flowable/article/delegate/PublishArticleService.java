package com.sila.flowable.holidayrequest.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublishArticleService implements JavaDelegate
{
    private static final Logger log = LoggerFactory.getLogger(PublishArticleService.class);
    @Override
    public void execute(DelegateExecution execution)
    {
        log.info("Publishing the approved article.");
    }
}
