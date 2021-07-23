package com.qris.qurban.util;

import com.google.gson.Gson;
import com.qris.qurban.model.DataQR;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.junit.jupiter.api.Assertions.*;

class QrisQurbanUtilTest
{
    private static Logger logger = null;

    @BeforeAll
    public static void setLogger()
    {
        System.setProperty("log4j.configurationFile", "log4j2-test.xml");
        logger = LogManager.getLogger("unit-test");
    }

    @Test
    void testGson()
    {
        String result = new Gson().toJson(new DataQR("Ria", "ria@ria.com", "6281286902713", "2021"));
        logger.info("Result: {}", result);

        assertNotNull(result);
    }

    @Test
    void testNewLineUrlParameter() throws UnsupportedEncodingException
    {
        String data = "Aku \n\n sayang \n kamu";
        logger.info("Data: {}", data);

        String result = URLEncoder.encode(data, "UTF-8");
        logger.info("Result: {}", result);

        assertNotNull(result);
    }
}