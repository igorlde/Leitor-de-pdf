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
             stringOp.append("Verificando/Criando arquivos de log e diretorios...").append(parentDir).append(System.lineSeparator());
             if(parentDir != null && Files.notExists(parentDir)){
                 Files.createDirectories(parentDir);//it part of code, can give erros. take care
             }
             if(Files.notExists(pathLog)){
                 Files.createFile(pathLog);
                 WRITER.write(stringOp.toString());
                 WRITER.flush();
                 WRITER.close();
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
            stringOp.append("verificando/Criando arquivos de log e diretorios..").append(parentDir).append(System.lineSeparator());
            if(parentDir != null && Files.notExists(parentDir)){
                Files.createDirectories(parentDir);
            }
            if(Files.notExists(pathLog)){
                Files.createFile(pathLog);
                WRITER.write(stringOp.toString());
                WRITER.flush();
                WRITER.close();
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
            stringOp.append("Verificando/Criando diretórios em macOS Log: ").append(parentDir).append(System.lineSeparator());
            if(parentDir != null && Files.notExists(parentDir)){
                Files.createDirectories(parentDir);
            }
            if (Files.notExists(pathLog)){
                Files.createFile(pathLog);
                WRITER.write(stringOp.toString());
                WRITER.flush();
                WRITER.close();
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
    private static final StringBuilder stringOp = new StringBuilder();
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));/*I take this improvement in perfomance, in
     the site called "https://osprogramadores.com/blog/2023/03/09/introducao-profiler-java/" */
    public static BufferedWriter getWRITER() {
        return WRITER;
    }
}