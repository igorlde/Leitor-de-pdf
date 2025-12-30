package logClasses;

import ExceptionsLogs.AcessRejectedException;
import ExceptionsLogs.GlobalHelpLogException;
import ExceptionsLogs.NotFoundNoSuchException;
import ExceptionsLogs.NotFoundPathLogException;
import OSEnums.LogSystemOSEnum;
import OSEnums.PathsOsLogs;
import logInterface.InterfaceLogs;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
/**
 * @author igor
 */
public class MainLogClass implements InterfaceLogs {
    /**
     * {@code Pattern pattern}
     * {@code String systemOs}
     * {@code boolean isFind }
     * {@code  Path logFilePath;}
     * {@code String name;}
     * {@code LocalDateTime dateTime;}
     * {@code  Integer page;}
     * {@code Float zoom;}
     */
    final Pattern pattern = Pattern.compile("^\\[(.*?)\\] Livro: (.*?), Página: (\\d+), Zoom: (.*?)%$");
    final String systemOs = System.getProperty("os.name").toLowerCase(Locale.ROOT);
    private static boolean isFind = LogEntry.isFindLog();
    private Path logFilePath;
    protected String name;
    private LocalDateTime dateTime;
    protected Integer page;
    protected Float zoom;

    /**
     *
     * @param name
     * @param page
     * @param zoom
     * @throws NotFoundNoSuchException
     * @throws NotFoundPathLogException
     * @throws AcessRejectedException
     * @throws IOException
     */
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

    /**
     *
     * @throws NotFoundNoSuchException
     * @throws NotFoundPathLogException
     * @throws AcessRejectedException
     * @throws IOException
     */
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

    /**
     *
     * @return List<LogEntry>
     * @throws NotFoundNoSuchException
     * @throws NotFoundPathLogException
     * @throws AcessRejectedException
     * @throws GlobalHelpLogException
     * @throws IOException
     */
    @Override
    public List<LogEntry> sendBackInformationLog() throws
            NotFoundNoSuchException,
            NotFoundPathLogException,
            AcessRejectedException,
            GlobalHelpLogException,
            IOException {
        /**
        variables of date, page, name, zoom.
         */

        Path pathLog = Paths.get(checkOs());
        List<LogEntry> parsedEntries = new ArrayList<>();
        String line = "";
        /**
         how I inverted the list take if you change the method of searchFile
         from PdfIgorController because it'll give a erro of logic
         */
        try(ReversedLinesFileReader br = new ReversedLinesFileReader(pathLog.toFile(), StandardCharsets.UTF_8)){
            while((line = br.readLine()) != null){
                Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        int page0  = Integer.parseInt(matcher.group(3));
                        if(page0 == 0)continue;

                        String timestamp = matcher.group(1).trim();
                        String bookName = matcher.group(2).trim();
                        int page = Integer.parseInt(matcher.group(3));
                        String zoomString = matcher.group(4).trim().replace(",", ".");
                        float zoom = Float.parseFloat(zoomString);
                        LogEntry entry = new LogEntry(timestamp, bookName, page, zoom);

                        parsedEntries.add(entry);
                        if (isFind) break;//if find break loop for not cause very consume of memory
                    }

            }
        } catch (NotFoundPathLogException | GlobalHelpLogException | NotFoundNoSuchException | AcessRejectedException e) {
            throw new IOException(e);
        }
        return parsedEntries;
    }

    /**
     *
     * @param nameBook
     * @param page
     * @param zoom
     * @throws NotFoundNoSuchException
     * @throws NotFoundPathLogException
     * @throws AcessRejectedException
     * @throws GlobalHelpLogException
     * @throws IOException
     */
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

    /**
     * check the systems OS
     * @return String
     * @throws NotFoundNoSuchException
     * @throws NotFoundPathLogException
     * @throws AcessRejectedException
     * @throws GlobalHelpLogException
     * @throws IOException
     */
    public String checkOs() throws
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

    /**
     *
     * @return Pattern
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     *
     * @return String
     */
    public String getSystemOs() {
        return systemOs;
    }

    /**
     *
     * @return Path
     */
    public Path getLogFilePath() {
        return logFilePath;
    }

    /**
     *
     * @param logFilePath
     */
    public void setLogFilePath(Path logFilePath) {
        this.logFilePath = logFilePath;
    }

    /**
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return LocalDateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     *
     * @param dateTime
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     *
     * @return Integer
     */
    public Integer getPage() {
        return page;
    }

    /**
     *
     * @param page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     *
     * @return Float
     */
    public Float getZoom() {
        return zoom;
    }

    /**
     *
     * @param zoom
     */
    public void setZoom(Float zoom) {
        this.zoom = zoom;
    }
}
