package VKR;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SendFileToCluster {
    private static String passwd= "\"Flvbybcnhfnjh12\"";
    public static String sendFileToCluster(String IP, String pathFrom, String addressFileLinux) {
        boolean result = false;
        String request = "";
        try {
            //pscp.exe -pw "Flvbybcnhfnjh12" E:/nopasssudo cluster@192.168.15.58:/home/cluster
            String fullAdr = "cluster@" + IP + addressFileLinux;
            Process p = Runtime.getRuntime().exec(new String[]{"pscp.exe", "-pw", passwd, pathFrom, fullAdr });
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputStr;
            // reading output stream of the command
            while ((inputStr = inputStream.readLine()) != null) {
                //System.out.println(s);
                request += inputStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
    public static void main(String args[]) throws Exception {
        String IP = "192.168.15.58";
        System.out.println(sendFileToCluster("192.168.15.61", "E://testMPI.c", ":/home/cluster"));
    }
}
