package OSEnums;

import java.util.Locale;

public enum PathsOsLogs {

    PATH_LINUX{
        final String userNameOs = System.getProperty("user.home").toLowerCase(Locale.ROOT);
        @Override
        public String pathEnvariomentSystem(){
             String LOG_LINUX = userNameOs+"/Desktop/configFilePDfIgui/config.log";
            return LOG_LINUX;
        }
    },
    PATH_WINDOWS{
        final String userNameOs = System.getProperty("user.home").toLowerCase(Locale.ROOT);
        @Override
        public String pathEnvariomentSystem(){
            String LOG_WINDOWS = userNameOs+"\\Desktop\\configFilePDfIgui\\config.log";
            return LOG_WINDOWS;
        }
    },
    PATH_MACOS{
        final String userNameOs = System.getProperty("user.home").toLowerCase(Locale.ROOT);
        @Override
        public String pathEnvariomentSystem(){
            final String LOG_MAC_OS = userNameOs+"/Desktop/configFilePDfIgui\\config.log";
            return LOG_MAC_OS;
        }
    };
    PathsOsLogs(){}
    public abstract String pathEnvariomentSystem();
}
