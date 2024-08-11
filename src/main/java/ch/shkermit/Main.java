package ch.shkermit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    private int width = 128*2;
    private int height = 128*1;

    private VideoCapture videoCapture;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        videoCapture = new VideoCapture(width, height);
        videoCapture.start();
    }

    @Override
    public void onDisable() {
        videoCapture.cleanup();
    }

    @EventHandler
    public void onMapInitialize(MapInitializeEvent e) {
        MapView mapView = e.getMap();

        mapView.setScale(MapView.Scale.FARTHEST);
        mapView.setTrackingPosition(false);
        mapView.getRenderers().clear();
        mapView.addRenderer(new MapRenderer(true) {
            @Override
            public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                videoCapture.renderCanvas(mapCanvas);
            }
        });
    }
}
