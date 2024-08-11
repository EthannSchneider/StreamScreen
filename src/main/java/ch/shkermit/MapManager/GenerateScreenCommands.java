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
        int max_width = 1;
        int max_height = 1;

        if (args.length >= 2 && args[0].matches("\\d+") && args[1].matches("\\d+")) {
            max_width = Integer.parseInt(args[0]);
            max_height = Integer.parseInt(args[1]);
        }

        Player player = (Player) sender;

        for (int width = 0; width < max_width; width++) {
            for (int height = 0; height < max_height; height++) {
                generateMap(player, width, height);
            }
        }
        
        return true;
    }

    private void generateMap(Player player, int width, int height) {
        MapView mapView = player.getServer().createMap(player.getWorld());

        mapView.setScale(MapView.Scale.FARTHEST);
        mapView.setTrackingPosition(false);
        mapView.getRenderers().clear();
        mapView.addRenderer(new MapRenderer(true) {
            @Override
            public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                videoCapture.renderCanvas(mapCanvas, width, height);
            }
        });
        ItemStack item = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) item.getItemMeta();
        meta.setMapView(mapView);
        item.setItemMeta(meta);

        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), item);
            return;
        }

        player.getInventory().addItem(item);
    }
    
}
