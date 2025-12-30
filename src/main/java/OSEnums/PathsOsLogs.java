package OSEnums;

import java.util.Locale;
/**
 * @author igor
 * this enum used to pass values of path of each
 * system OS.
 */
public enum PathsOsLogs {

    /**
     * @see logClasses.MainLogClass
     * this enums pass the path of the System Os
     */

    PATH_LINUX{
        final String userNameOs = System.getProperty("user.home").toLowerCase(Locale.ROOT);
        @Override
        public String pathEnvariomentSystem(){
             String LOG_LINUX = userNameOs+"/Desktop/configFilePDfIgui/config.log";
            return LOG_LINUX;
        }
    },

    /**
     * @see logClasses.MainLogClass
     * this enums pass the path of the System Os
     */
    PATH_WINDOWS{
        final String userNameOs = System.getProperty("user.home").toLowerCase(Locale.ROOT);
        @Override
        public String pathEnvariomentSystem(){
            String LOG_WINDOWS = userNameOs+"\\Desktop\\configFilePDfIgui\\config.log";
            return LOG_WINDOWS;
        }
    },

    /**
     * @see logClasses.MainLogClass
     * this enums pass the path of the System Os
     */
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
