package VKR;
//change program for analyse
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class VersionOS {
    public static void main(String[] args){
        checkOS("192.168.15.58"); //Linux
        //checkOS("192.168.0.3"); //Windows
        //checkOS("192.168.0.5"); //???

    }
    public static int checkOS(String IP) {
        int result = 0;
        int result1 = 1;//for testing
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"C:\\Program Files (x86)\\Nmap\\nmap.exe", "-O", IP});
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputStr;
            String request = "";
            // reading output stream of the command
            while ((inputStr = inputStream.readLine()) != null) {
                //System.out.println(s);
                request += inputStr; //выходной результат 
            }
            switch (checkOSinRequest(request)){
                case (0):
                    System.out.println("This is the unknown OS - " + IP);
                    result = 0;
                    break;
                case (1):
                    System.out.println("It's Linux - " + IP);
                    result = 1;
                    break;
                case (2):
                    System.out.println("It's Windows - " + IP);
                    result = 1;
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result1;
    }

    private static int checkOSinRequest(String result){
        int indexJava = result.indexOf("Linux"); // Нахождение слова в строке
        int OS = 0;
        if(indexJava == - 1) OS = checkWindows(result); //System.out.println("I change loop Linux - Windows ");
        else OS = 1; //System.out.println("I found OS - Linux");
        return OS;
    }

    private static int checkWindows(String win) {
        int indexJava = win.indexOf("Windows"); // Нахождение слова в строке
        int OS = 0;
        if(indexJava == - 1) OS = 0; //System.out.println("I don't know");
        else OS = 2; //System.out.println("I found OS - Windows" + indexJava);
        return OS;
    }
}
