package ch.shkermit.MapManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.bukkit.map.MapCanvas;

public class VideoCapture extends Thread {
    public Boolean active = true;

    private BufferedImage currentFrame;

    VideoCaptureUDPServer videoCaptureUDPServer;

    public void renderCanvas(MapCanvas mapCanvas, int width, int height) {
        BufferedImage frame = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);

        int real_width = (width * -128);
        int real_height = (height * -128);
        Graphics2D graphics = frame.createGraphics();
        graphics.drawImage(currentFrame, real_width, real_height, null);

        mapCanvas.drawImage(0,0, frame);
        graphics.dispose();
    }

    public VideoCapture() {
        currentFrame = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);

        videoCaptureUDPServer = new VideoCaptureUDPServer() {
            @Override
            public void onFrame(BufferedImage frame) {
                currentFrame = frame;
            }
        };

        videoCaptureUDPServer.start();
    }

    public void cleanup() {
        videoCaptureUDPServer.cleanup();
    }
}