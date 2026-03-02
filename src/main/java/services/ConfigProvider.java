package services;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigProvider {

    public static final Config config = ConfigFactory.load("application.conf");

}
