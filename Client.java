import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Set the server address
        int serverPort = 12345; // Set the server port

        try (Socket socket = new Socket(serverAddress, serverPort);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            String fileName = "Presentation48 .pptx"; // Change this to the file you want to send
            File file = new File(fileName);

            // Send the file name to the server
            out.writeUTF(fileName);

            try (// Send the file data to the server
            FileInputStream fileInputStream = new FileInputStream(file)) {
                int bytesRead;
                byte[] buffer = new byte[1024];
                int packetNumber = 0;

                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    packetNumber++;
                    out.write(buffer, 0, bytesRead);
                    System.out.println("Packet " + packetNumber + " sent (" + bytesRead + " bytes)");
                }
            }
            System.out.println("File sent successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
