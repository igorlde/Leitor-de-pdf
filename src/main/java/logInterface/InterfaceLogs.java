package logInterface;

import ExceptionsLogs.AcessRejectedException;
import ExceptionsLogs.GlobalHelpLogException;
import ExceptionsLogs.NotFoundNoSuchException;
import ExceptionsLogs.NotFoundPathLogException;
import logClasses.LogEntry;

import java.io.IOException;
import java.util.List;
/**
 * @author igor
 */
public interface InterfaceLogs {
    /**
     *
     * @return
     * @throws NotFoundNoSuchException
     * @throws NotFoundPathLogException
     * @throws AcessRejectedException
     * @throws GlobalHelpLogException
     * @throws IOException
     */
    public List<LogEntry> sendBackInformationLog() throws
            NotFoundNoSuchException,
            NotFoundPathLogException,
            AcessRejectedException,
            GlobalHelpLogException,
            IOException;

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
    public void createdLog(String nameBook, Integer page, Float zoom) throws
            NotFoundNoSuchException,
            NotFoundPathLogException,
            AcessRejectedException,
            GlobalHelpLogException,
            IOException;

}
