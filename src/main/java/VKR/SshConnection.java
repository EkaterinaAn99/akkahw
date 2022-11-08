package VKR;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SshConnection {

    private static String username = "cluster";
    private static String password = "Flvbybcnhfnjh12";
    private static int port = 22;

    public static String executeCommandSsh(String host, String command) throws Exception {
        Session session = null;
        ChannelExec channel = null;
        String responseString;
        try {
            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            responseString = new String(responseStream.toByteArray());
            //System.out.println(responseString);

            InputStream in=channel.getInputStream();

            byte[] tmp=new byte[1024];

            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    //System.out.print(new String(tmp, 0, i));

                }
                if(channel.isClosed()){
                    if(in.available()>0) continue;
                    if (channel.getExitStatus() > 1) System.out.println("You have problem! Exit-status: "+channel.getExitStatus() + " " + host);
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }


        }finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }


        return responseString;
    }
    public static void main(String args[]) throws Exception {

        String host = "192.168.15.62";
        String request;
        request = executeCommandSsh(host, "which mpiexec");
        System.out.println("RESULT\n" + request);
    }
}
