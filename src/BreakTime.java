import javax.swing.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class BreakTime {

    public static void main(String[] args) {
        try {
            if (args.length == 1 && args[0] != null && (args[0].equals("--version") || args[0].equals("-v"))) {
                StringBuilder sb = new StringBuilder("BreakTime version: 0.0.1\n");
                sb.append("Copyright (2020): bakincode (github: https://github.com/bakincode)");
                System.out.println(sb.toString());
                return;
            }

            if (args.length == 1 && args[0] != null && (args[0].equals("--help") || args[0].equals("-h"))) {
                StringBuilder sb = new StringBuilder("Help:\n");
                sb.append("Description: Application to remember the need for breaks at work. You need to specify on command line the time between breaks.\n");
                sb.append("Command: java -jar breaktime.jar <<value>> <<units>>\n");
                sb.append("Argument <<value>>: = number of min or hour\n");
                sb.append("Argument <<units>>: = min or hour\n");
                sb.append("Example: java -jar breaktime.jar  15 min");
                System.out.println(sb.toString());
                return;
            }

            if (args.length < 2) {
                System.err.println("arguments in fault, you should specify <<value>> and <<units>>, units --help or -h to see more");
                return;
            }

            long time;
            try {
                if (args[0] != null || !args[0].equals("")) time = Long.parseLong(args[0]);
                else {
                    System.err.println("you need specify <<value>>");
                    return;
                }
            } catch (Exception e) {
                System.err.println("first argument should be a integer number");
                return;
            }

            String units = args[1];

            if (units != null && (!units.equals("min") && !units.equals("hour"))) {
                System.err.println("second argument should be format (min or hours)");
                return;
            } else if (units == null) {
                System.err.println("second argument <<units>> in fault");
                return;
            }

            long sec = 1000, min = 60 * sec, hour = 60 * min, timer;

            switch (units) {
                case "min":
                    timer = time * min;
                    break;
                case "hour":
                    timer = time * hour;
                    break;
                default:
                    timer = 0;
                    break;
            }

            JFrame jFrame = new JFrame("BreakTime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            JOptionPane.showMessageDialog(jFrame, "Welcome to BreakTime, the process will start with breaks of " + time + " " + units + ".");

            LocalDateTime start, end;
            long minutes, totalPauseTime = 0, totalWorkTime = 0, workTime = toMinutes(time, units);

            System.out.println("["+ LocalDateTime.now().format(formatter)+"] Started!");

            while (true) {
                try {
                    Thread.sleep(timer);
                    start = LocalDateTime.now();
                    totalWorkTime += workTime;
                    System.out.println("[" + start.format(formatter) + "] break time (total work time = " + totalWorkTime + " min and total pause time = " + totalPauseTime + " min.)");
                    JOptionPane.showMessageDialog(jFrame, "(" + LocalDateTime.now().format(formatter) + ") Break time, get out of computer! When you back click on OK to start a new work cycle (total work time = " + totalWorkTime + " min and total pause time = " + totalPauseTime + " min.)");
                    end = LocalDateTime.now();
                    minutes = start.until(end, ChronoUnit.MINUTES);
                    totalPauseTime += minutes;
                    System.out.println("[" + end.format(formatter) + "] return to work (total work time = " + totalWorkTime + " min and total pause time = " + totalPauseTime + " min.)");

                } catch (InterruptedException e) {
                    System.err.println("internal error");
                    return;
                }
            }
        } catch (Exception e){
            System.err.println("Internal error. contact dev team. (github: https://github.com/bakincode)");
        }
    }

    public static long toMinutes(long time,  String type){
        switch (type){
            case "hour":
                return time * 60;
            case "min":
                return time;
            default:
                return 0;
        }
    }
}