package logClasses;

import ExceptionsLogs.AcessRejectedException;
import ExceptionsLogs.GlobalHelpLogException;
import ExceptionsLogs.NotFoundNoSuchException;
import ExceptionsLogs.NotFoundPathLogException;
import OSEnums.LogSystemOSEnum;
import OSEnums.PathsOsLogs;
import logInterface.InterfaceLogs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainLogClass implements InterfaceLogs {
    //very important this variable
    final Pattern pattern = Pattern.compile("^\\[(.*?)\\] Livro: (.*?), Página: (\\d+), Zoom: (.*?)%$");
    final String systemOs = System.getProperty("os.name").toLowerCase(Locale.ROOT);

    private Path logFilePath;
    protected String name;
    private LocalDateTime dateTime;
    protected Integer page;
    protected Float zoom;

    public MainLogClass(String name, Integer page, Float zoom) throws
            NotFoundNoSuchException,
            NotFoundPathLogException,
            AcessRejectedException,
            IOException {

        this.logFilePath = Paths.get(checkOs());
        this.name = name;
        this.dateTime = LocalDateTime.now();
        this.page = page;
        this.zoom = zoom;
    }
    public MainLogClass() throws
            NotFoundNoSuchException,
            NotFoundPathLogException,
            AcessRejectedException,
            IOException {

        this.logFilePath = Paths.get(checkOs());
        this.name = "default";
        this.dateTime = LocalDateTime.now();
        this.page = 1;
        this.zoom = 150.5F;
    }

    @Override
    public List<LogEntry> sendBackInformationLog() throws
            NotFoundNoSuchException,
            NotFoundPathLogException,
            AcessRejectedException,
            GlobalHelpLogException,
            IOException {
        //variables of date, page, name, zoom.
        //read log
        Path pathLog = Paths.get(checkOs());
        List<LogEntry> parsedEntries = new ArrayList<>();
        String line = "";
        try(BufferedReader br = Files.newBufferedReader(pathLog)){
            while((line = br.readLine()) != null){
                //I have who study it
                Matcher matcher = pattern.matcher(line);
                if(matcher.find()){
                    //It's amazing kk
                    String timestamp = matcher.group(1).trim();
                    String bookName = matcher.group(2).trim();
                    int page = Integer.parseInt(matcher.group(3));
                    String zoomString = matcher.group(4).trim().replace(",", "."); // Troca vírgula por ponto se necessário
                    float zoom = Float.parseFloat(zoomString);
                    LogEntry entry = new LogEntry(timestamp, bookName, page, zoom);
                    parsedEntries.add(entry);
                }
            }
        } catch (NotFoundPathLogException | GlobalHelpLogException | NotFoundNoSuchException | AcessRejectedException e) {
            throw new IOException(e);
        }
        return parsedEntries;
    }

    @Override
    public void createdLog(String nameBook, Integer page, Float zoom) throws
            NotFoundNoSuchException,
            NotFoundPathLogException,
            AcessRejectedException,
            GlobalHelpLogException,
            IOException{
        MainLogClass thisClass = new MainLogClass(nameBook, page, zoom);
        checkOs();
        try(FileWriter fw = new FileWriter(logFilePath.toFile(), true);
            BufferedWriter bw = new BufferedWriter(fw)){
                bw.write(thisClass.toString());
                bw.newLine();
        }  catch (NotFoundPathLogException | GlobalHelpLogException | NotFoundNoSuchException | AcessRejectedException e) {
            throw new IOException(e);
        }
    }

    private boolean copyLogOverInsideProject() throws
            NotFoundNoSuchException,
            NotFoundPathLogException,
            AcessRejectedException,
            GlobalHelpLogException,
            IOException {
        final String userName = System.getProperty("user.home").toLowerCase();
        final String path = userName +
                "\\Documents\\some-projects-java\\LeitorPdfwithGUi2.0\\src\\main\\java\\configFiles\\books.log";
        boolean isOk = false;
        List<LogEntry> listLog = sendBackInformationLog();
        Path pathInside = Paths.get(path);
            try(FileWriter fw = new FileWriter(pathInside.toFile(), true);
                BufferedWriter bw = new BufferedWriter(fw)){
                    bw.write(listLog.toString());
                    bw.newLine();
                    isOk = true;
            }catch (NotFoundPathLogException | GlobalHelpLogException | NotFoundNoSuchException | AcessRejectedException e){
                throw new IOException(e);
            }
        return isOk;

    }

    @Override
    public String toString() {
        // Formata a data/hora para ser legível no log.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);

        // Retorna a linha completa do log.
        return String.format("[%s] Livro: %s, Página: %d, Zoom: %.1f%%",
                formattedDateTime,
                this.name,
                this.page,
                this.zoom
        );
    }
    //check the systems OS
    private String checkOs() throws
            NotFoundNoSuchException,
            NotFoundPathLogException,
            AcessRejectedException,
            GlobalHelpLogException,
            IOException{
        if(systemOs.contains("win")) {
            LogSystemOSEnum.WINDOWS.executedCreatedLogSystem(true);
            return PathsOsLogs.PATH_WINDOWS.pathEnvariomentSystem();
        } else if (systemOs.contains("mac")) {
            LogSystemOSEnum.MAC_OS.executedCreatedLogSystem(true);
            return PathsOsLogs.PATH_MACOS.pathEnvariomentSystem();
        } else if (systemOs.contains("nix") || systemOs.contains("nux") || (systemOs.contains("aix"))){
            LogSystemOSEnum.LINUX.executedCreatedLogSystem(true);
            return PathsOsLogs.PATH_LINUX.pathEnvariomentSystem();
        }
        return null;
    }
    //I do this to facillty test of codes
   public static void main(String[] args) throws IOException {
       MainLogClass mainLogClass = new MainLogClass();
//       mainLogClass.createdLog("molester book", 10, 153.4F);
//       System.out.println(systemOs); it do
       System.out.println(
               mainLogClass.copyLogOverInsideProject());
  }

    public Pattern getPattern() {
        return pattern;
    }

    public String getSystemOs() {
        return systemOs;
    }

    public Path getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(Path logFilePath) {
        this.logFilePath = logFilePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Float getZoom() {
        return zoom;
    }

    public void setZoom(Float zoom) {
        this.zoom = zoom;
    }
}
