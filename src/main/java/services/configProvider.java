package services;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface configProvider {
    Config config = readConfig();

    static Config readConfig(){
      return ConfigFactory.load("application.conf");
    }
}
