package VKR;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PingIP {

    public static boolean runSystemCommand(String IP) {
        boolean result = false;
        try {
            Process p = Runtime.getRuntime().exec("ping " + IP);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String inputStr;
            String request = "";
            // reading output stream of the command
            while ((inputStr = inputStream.readLine()) != null) {
                //System.out.println(s);
                request += inputStr;
            }
            String eqWordForChecking = "TTL";
            result = equalsString(request, eqWordForChecking);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean equalsString(String word, String eqWord){
        boolean resultOfTask;
        int indexTTL = word.indexOf(eqWord); // Нахождение символа в строке
        resultOfTask = indexTTL != -1;
        return resultOfTask;
    }
}
