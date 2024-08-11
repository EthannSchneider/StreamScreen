package ch.shkermit.MapManager;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class GenerateScreenCommands implements CommandExecutor {
    private VideoCapture videoCapture;

    public GenerateScreenCommands(VideoCapture videoCapture) {
        this.videoCapture = videoCapture;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return false;
        }
        Player player = (Player) sender;

        MapView mapView = player.getServer().createMap(player.getWorld());

        mapView.setScale(MapView.Scale.FARTHEST);
        mapView.setTrackingPosition(false);
        mapView.getRenderers().clear();
        mapView.addRenderer(new MapRenderer(true) {
            @Override
            public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                videoCapture.renderCanvas(mapCanvas);
            }
        });
        ItemStack item = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) item.getItemMeta();
        meta.setMapView(mapView);
        item.setItemMeta(meta);

        player.getInventory().addItem(item);

        return true;
    }
    
}
