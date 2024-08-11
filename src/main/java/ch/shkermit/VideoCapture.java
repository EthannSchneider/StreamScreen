package ch.shkermit;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.bukkit.map.MapCanvas;

public class VideoCapture extends Thread {
    public Boolean active = true;
    public int width;
    public int height;

    private BufferedImage currentFrame;

    VideoCaptureUDPServer videoCaptureUDPServer;

    public void renderCanvas(MapCanvas mapCanvas) {
        BufferedImage frame = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = frame.createGraphics();
        graphics.drawImage(currentFrame,0,0,null);

        mapCanvas.drawImage(0,0, frame);
        graphics.dispose();
    }

    public VideoCapture(int width, int height) {
        this.width = width;
        this.height = height;

        currentFrame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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