package ch.shkermit;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ch.shkermit.MapManager.GenerateScreenCommands;
import ch.shkermit.MapManager.VideoCapture;

public class Main extends JavaPlugin implements Listener {
    private VideoCapture videoCapture;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        videoCapture = new VideoCapture();
        videoCapture.start();

        getCommand("generateScreen").setExecutor(new GenerateScreenCommands(videoCapture));
    }

    @Override
    public void onDisable() {
        videoCapture.cleanup();
    }
}
