package OSEnums;


import logClasses.MainLogClass;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
    @author igor
 */
public enum DateRemoveLogEnum {
    /**
     *  this function remove log in a year
     */
    ONE_YEAR_REMOVE_LOG{
        @Override
        public void executeRemove() {
            Calendar dataNow = Calendar.getInstance();
            int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
            int year = calendar.get(Calendar.YEAR);

            if(dataNow.get(Calendar.DAY_OF_MONTH) == monthDay && dataNow.get(Calendar.YEAR) == year) {
                str.append("remove log bye bye...");
                    try(FileWriter fw = new FileWriter(pathOfPdf.toFile(), false)){
                        WRITER.write(str.toString());
                        WRITER.flush();
                        WRITER.close();
                        fw.close();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                calendar.add(Calendar.YEAR, 1);
                }
        }
    };
    public abstract void executeRemove();

    /**
     * {@code StringBuilder str}
     * {@code BufferedWriter WRITER}
     * {@code MainLogClass mainLogClass}
     * {@code  Path pathOfPdf}
     * {@code Calendar calendar}
     */
    private static StringBuilder str = new StringBuilder();
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    private static MainLogClass mainLogClass;
    private static Path pathOfPdf;
    private static Calendar calendar = new GregorianCalendar(2025, Calendar.JANUARY, 30);

    static {
        try {
            mainLogClass = new MainLogClass();
            pathOfPdf = Paths.get(mainLogClass.checkOs());//
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    DateRemoveLogEnum(){}
    @Override
    public String toString() {
        return super.toString();
    }

//    public static void main(String[] args) {
//        ONE_YEAR_REMOVE_LOG.executeRemove();
//    }
}
