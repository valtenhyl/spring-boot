package com.valten.support;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FtpDownloadRoute extends RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger(FtpDownloadRoute.class);

    @Value("${ftp.url}")
    private String ftpUrl;

    @Value("${ftp.local-dir}")
    private String localDir;

    @Autowired
    private DataProcessor dataProcessor;

    @Override
    public void configure() {
        from(ftpUrl)
                .to("file:" + localDir)
                .process(dataProcessor)
                .log(LoggingLevel.INFO, logger, "download file ${file:name} complete.");
    }
}