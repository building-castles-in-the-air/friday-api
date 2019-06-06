package com.github.friday.app.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class MessageTemplate {
    private static Properties properties;

    static {
        InputStream is = ClassLoader.getSystemResourceAsStream("config/message.properties");

        if(properties == null) {
            properties = new Properties();
        }

        try {
            properties.load(new InputStreamReader(is, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MessageTemplate() {
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

}
