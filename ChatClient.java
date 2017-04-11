import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.io.OutputStream;
import java.io.PrintStream;

public final class ChatClient {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("codebank.xyz", 38001)) {
			
			Scanner kb = new Scanner(System.in);
			OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");
			String message;
			System.out.print("Enter Username: ");
			message = kb.nextLine();
			out.printf(message + "%n");
			
			Runnable runner = new messageReceive(socket);
			new Thread(runner).start();
			
			while(true) {
				message = kb.nextLine();
				out.printf(message + "%n");
			}
        }
    }
	
	public static class messageReceive implements Runnable {

		private Socket socket;
	
		public messageReceive(Socket ThreadSocket) {
			socket = ThreadSocket;
		}

		public void run() {
			try {
			
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String returnMessage;
				boolean on = true;
				while(true) {
					returnMessage = br.readLine();
					System.out.println(returnMessage);
				}
			} catch(Exception E){E.printStackTrace();}
			
		}
	}
	
}