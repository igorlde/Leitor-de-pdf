package OSEnums;

import ExceptionsLogs.AcessRejectedException;
import ExceptionsLogs.GlobalHelpLogException;
import ExceptionsLogs.NotFoundNoSuchException;
import ExceptionsLogs.NotFoundPathLogException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum LogSystemOSEnum {

    WINDOWS{
         @Override
         public void executedCreatedLogSystem(boolean accept) throws
                 NotFoundNoSuchException,
                 AcessRejectedException,
                 GlobalHelpLogException,
                 NotFoundPathLogException,
                 IOException{
             //this accept permission insert documents.
             Path pathLog = null;
             if(accept){
                pathLog = Paths.get(PathsOsLogs.PATH_WINDOWS.pathEnvariomentSystem());
             }
             Path parentDir = pathLog.getParent();
             if(parentDir != null && Files.notExists(parentDir)){
                 WRITER.write("Criando diretorio windows no arquivos e programas" + parentDir);
                 WRITER.flush();
                 WRITER.close();
                 Files.createDirectories(parentDir);//it part of code, can give erros. take care
             }
             if(Files.notExists(pathLog)){
                 WRITER.write("Criando arquivo log...."+ pathLog);
                 WRITER.flush();
                 WRITER.close();
                 Files.createFile(pathLog);
             }
             return;
    }
    },

    //Linux log function for create in the system
    LINUX{
        @Override
        public void executedCreatedLogSystem(boolean accept) throws
                NotFoundNoSuchException,
                AcessRejectedException,
                GlobalHelpLogException,
                NotFoundPathLogException,
                IOException{
            Path pathLog = null;
            if (accept){
                pathLog = Paths.get(PathsOsLogs.PATH_LINUX.pathEnvariomentSystem());
            }
            Path parentDir = pathLog.getParent();
            if(parentDir != null && Files.notExists(parentDir)){
                WRITER.write("Criando diretorio em Linux Log...."+ parentDir);
                WRITER.flush();
                WRITER.close();
                Files.createDirectories(parentDir);
            }
            if(Files.notExists(pathLog)){
                WRITER.write("Criando arquivo log em Linux..."+ pathLog);
                WRITER.flush();
                WRITER.close();
                Files.createFile(pathLog);
            }
        }
    },
    MAC_OS{
        @Override
        public void executedCreatedLogSystem(boolean accept) throws
                NotFoundNoSuchException,
                AcessRejectedException,
                GlobalHelpLogException,
                NotFoundPathLogException,
                IOException{
            Path pathLog = null;
            if (accept){
                pathLog = Paths.get(PathsOsLogs.PATH_MACOS.pathEnvariomentSystem());
            }
            Path parentDir = pathLog.getParent();
            if(parentDir != null && Files.notExists(parentDir)){
                WRITER.write("Criando diretorio em macOS Log...."+ parentDir);
                WRITER.flush();
                WRITER.close();
                Files.createDirectories(parentDir);
            }
            if (Files.notExists(pathLog)){
                WRITER.write("Criando arquivo log em macOS ....."+ pathLog);
                WRITER.flush();
                WRITER.close();
                Files.createFile(pathLog);
            }
        }
    };

    public abstract void executedCreatedLogSystem(boolean accept) throws
            NotFoundNoSuchException,
            AcessRejectedException,
            GlobalHelpLogException,
            NotFoundPathLogException,
            IOException;

    LogSystemOSEnum(){
    }
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));/*I take this improvement in perfomance, in
     the site called "https://osprogramadores.com/blog/2023/03/09/introducao-profiler-java/" */
    public static BufferedWriter getWRITER() {
        return WRITER;
    }
}
