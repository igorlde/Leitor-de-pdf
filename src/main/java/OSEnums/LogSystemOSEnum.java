package OSEnums;

import ExceptionsLogs.AcessRejectedException;
import ExceptionsLogs.GlobalHelpLogException;
import ExceptionsLogs.NotFoundNoSuchException;
import ExceptionsLogs.NotFoundPathLogException;

import java.io.IOException;
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
                 System.out.println("Criando diretorio windows no arquivos e programas" + parentDir);
                 Files.createDirectories(parentDir);//it part of code, can give erros. take care
             }
             if(Files.notExists(pathLog)){
                 System.out.println("Criando arquivo log...."+ pathLog);
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
                System.out.println("Criando diretorio em Linux Log...."+ parentDir);
                Files.createDirectories(parentDir);
            }
            if(Files.notExists(pathLog)){
                System.out.println("Criando arquivo log em Linux..."+ pathLog);
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
                System.out.println("Criando diretorio em macOS Log...."+ parentDir);
                Files.createDirectories(parentDir);
            }
            if (Files.notExists(pathLog)){
                System.out.println("Criando arquivo log em macOS ....."+ pathLog);
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

}
