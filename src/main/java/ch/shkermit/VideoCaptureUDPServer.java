package ch.shkermit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// https://stackoverflow.com/questions/21420252/how-to-receive-mpeg-ts-stream-over-udp-from-ffmpeg-in-java
public class VideoCaptureUDPServer extends Thread {
    public boolean running = true;

    private DatagramSocket socket;

    public void onFrame(BufferedImage frame) { }

    public void run() {
        try {
            byte[] buffer = new byte[1024*1024];
            socket = new DatagramSocket(1337);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            while (running) {
                socket.receive(packet);

                byte[] data = packet.getData();

                if (data[0]==-1 && data[1]==-40) { // FF D8 (start of file)
                    if (output.size()>0) {
                        try {
                            ByteArrayInputStream stream = new ByteArrayInputStream(output.toByteArray());
                            onFrame(ImageIO.read(stream));
                        } catch (IOException e) {}

                        output.reset();
                    }
                }

                output.write(data,0,packet.getLength());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cleanup() {
        running = false;
        if (socket!=null) socket.disconnect();
        if (socket!=null) socket.close();
    }
}
