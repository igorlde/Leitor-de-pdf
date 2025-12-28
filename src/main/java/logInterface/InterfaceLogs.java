package logInterface;

import ExceptionsLogs.AcessRejectedException;
import ExceptionsLogs.GlobalHelpLogException;
import ExceptionsLogs.NotFoundNoSuchException;
import ExceptionsLogs.NotFoundPathLogException;
import logClasses.LogEntry;

import java.io.IOException;
import java.util.List;

public interface InterfaceLogs {
    public List<LogEntry> sendBackInformationLog() throws
            NotFoundNoSuchException,
            NotFoundPathLogException,
            AcessRejectedException,
            GlobalHelpLogException,
            IOException;
    public void createdLog(String nameBook, Integer page, Float zoom) throws
            NotFoundNoSuchException,
            NotFoundPathLogException,
            AcessRejectedException,
            GlobalHelpLogException,
            IOException;

}
