package de.uniqueplayers.function;

import net.risingworld.api.Plugin;
import net.risingworld.api.Server;
import net.risingworld.api.World;
//import net.risingworld.api.Timer;
//import net.risingworld.api.callbacks.Callback;
import net.risingworld.api.database.Database;
import net.risingworld.api.database.DatabaseType;
import net.risingworld.api.database.WorldDatabase;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.Threading;
import net.risingworld.api.events.general.AddAdminEvent;
import net.risingworld.api.events.general.RemoveAdminEvent;
import net.risingworld.api.events.general.ShutdownEvent;
import net.risingworld.api.events.general.UpdateEvent;
import net.risingworld.api.events.npc.NpcDeathEvent;
import net.risingworld.api.events.npc.NpcSpawnEvent;
import net.risingworld.api.events.player.PlayerBanEvent;
import net.risingworld.api.events.player.PlayerChangeBlockPositionEvent;
import net.risingworld.api.events.player.PlayerChangeGamemodeEvent;
import net.risingworld.api.events.player.PlayerChangePositionEvent;
import net.risingworld.api.events.player.PlayerChatEvent;
import net.risingworld.api.events.player.PlayerChestAccessEvent;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.events.player.PlayerConnectEvent;
import net.risingworld.api.events.player.PlayerCraftItemEvent;
import net.risingworld.api.events.player.PlayerDamageEvent;
import net.risingworld.api.events.player.PlayerDeathEvent;
import net.risingworld.api.events.player.PlayerDisconnectEvent;
import net.risingworld.api.events.player.PlayerElementHitEvent;
import net.risingworld.api.events.player.PlayerElementInteractionEvent;
import net.risingworld.api.events.player.PlayerEnterAreaEvent;
import net.risingworld.api.events.player.PlayerEnterChunkEvent;
import net.risingworld.api.events.player.PlayerEnterWorldpartEvent;
import net.risingworld.api.events.player.PlayerEvent;
import net.risingworld.api.events.player.PlayerHitEvent;
import net.risingworld.api.events.player.PlayerHitNpcEvent;
import net.risingworld.api.events.player.PlayerKeyEvent;
import net.risingworld.api.events.player.PlayerKickEvent;
import net.risingworld.api.events.player.PlayerLeaveAreaEvent;
import net.risingworld.api.events.player.PlayerNpcEvent;
import net.risingworld.api.events.player.PlayerNpcInteractionEvent;
import net.risingworld.api.events.player.PlayerObjectInteractionEvent;
import net.risingworld.api.events.player.PlayerPermissionGroupChangeEvent;
import net.risingworld.api.events.player.PlayerPickupItemEvent;
import net.risingworld.api.events.player.PlayerRespawnEvent;
import net.risingworld.api.events.player.PlayerSetSignTextEvent;
import net.risingworld.api.events.player.PlayerSleepEvent;
import net.risingworld.api.events.player.PlayerSpawnEvent;
import net.risingworld.api.events.player.PlayerStartFlyingEvent;
import net.risingworld.api.events.player.PlayerStopFlyingEvent;
import net.risingworld.api.events.player.gui.PlayerGuiElementClickEvent;
import net.risingworld.api.events.player.gui.PlayerGuiInputEvent;
import net.risingworld.api.events.player.gui.PlayerMapClickEvent;
import net.risingworld.api.events.player.gui.PlayerMapCreateMarkerEvent;
import net.risingworld.api.events.player.gui.PlayerMapRemoveMarkerEvent;
import net.risingworld.api.events.player.gui.PlayerSelectFileEvent;
import net.risingworld.api.events.player.inventory.PlayerChestDropEvent;
import net.risingworld.api.events.player.inventory.PlayerChestToInventoryEvent;
import net.risingworld.api.events.player.inventory.PlayerInventoryAddEvent;
import net.risingworld.api.events.player.inventory.PlayerInventoryToChestEvent;
import net.risingworld.api.events.player.world.PlayerChangeObjectStatusEvent;
import net.risingworld.api.events.player.world.PlayerConstructionEvent;
import net.risingworld.api.events.player.world.PlayerCreateBlueprintEvent;
import net.risingworld.api.events.player.world.PlayerCreativePlaceBlockEvent;
import net.risingworld.api.events.player.world.PlayerCreativePlaceVegetationEvent;
import net.risingworld.api.events.player.world.PlayerCreativeTerrainEditEvent;
import net.risingworld.api.events.player.world.PlayerDestroyBlockEvent;
import net.risingworld.api.events.player.world.PlayerDestroyConstructionEvent;
import net.risingworld.api.events.player.world.PlayerDestroyObjectEvent;
import net.risingworld.api.events.player.world.PlayerDestroyTerrainEvent;
import net.risingworld.api.events.player.world.PlayerDestroyVegetationEvent;
import net.risingworld.api.events.player.world.PlayerObjectEvent;
import net.risingworld.api.events.player.world.PlayerPlaceBlockEvent;
import net.risingworld.api.events.player.world.PlayerPlaceBlueprintEvent;
import net.risingworld.api.events.player.world.PlayerPlaceConstructionEvent;
import net.risingworld.api.events.player.world.PlayerPlaceGrassEvent;
import net.risingworld.api.events.player.world.PlayerPlaceObjectEvent;
import net.risingworld.api.events.player.world.PlayerPlaceTerrainEvent;
import net.risingworld.api.events.player.world.PlayerPlaceVegetationEvent;
import net.risingworld.api.events.player.world.PlayerPlaceWaterEvent;
import net.risingworld.api.events.player.world.PlayerRemoveConstructionEvent;
import net.risingworld.api.events.player.world.PlayerRemoveGrassEvent;
import net.risingworld.api.events.player.world.PlayerRemoveObjectEvent;
import net.risingworld.api.events.player.world.PlayerRemoveVegetationEvent;
import net.risingworld.api.events.player.world.PlayerRemoveWaterEvent;
import net.risingworld.api.events.player.world.PlayerVegetationEvent;
import net.risingworld.api.events.player.world.PlayerWorldEditEvent;
import net.risingworld.api.gui.Font;
import net.risingworld.api.gui.GuiElement;
import net.risingworld.api.gui.GuiFileBrowser;
import net.risingworld.api.gui.GuiImage;
import net.risingworld.api.gui.GuiLabel;
import net.risingworld.api.gui.GuiPanel;
import net.risingworld.api.gui.GuiTextField;
import net.risingworld.api.gui.PivotPosition;
import net.risingworld.api.gui.GuiPaintable;
import net.risingworld.api.objects.Chest;
import net.risingworld.api.objects.Inventory;
import net.risingworld.api.objects.Item;
import net.risingworld.api.objects.MapMarker;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Sign;
import net.risingworld.api.objects.Time;
import net.risingworld.api.objects.Weather;
import net.risingworld.api.objects.WorldItem;
import net.risingworld.api.shader.*;
import net.risingworld.api.utils.Animation;
import net.risingworld.api.utils.Area;
import net.risingworld.api.utils.BoundingInformation;
import net.risingworld.api.utils.Chunk;
import net.risingworld.api.utils.ChunkLOD;
import net.risingworld.api.utils.CollisionShape;
import net.risingworld.api.utils.CollisionType;
import net.risingworld.api.utils.Crosshair;
import net.risingworld.api.utils.Definitions;
import net.risingworld.api.utils.GameImageInformation;
import net.risingworld.api.utils.Gamemode;
import net.risingworld.api.utils.ImageInformation;
import net.risingworld.api.utils.KeyInput;
import net.risingworld.api.utils.ModelInformation;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.RayCastResult;
import net.risingworld.api.utils.SoundInformation;
import net.risingworld.api.utils.Utils;
import net.risingworld.api.utils.Vector2f;
import net.risingworld.api.utils.Vector2i;
import net.risingworld.api.utils.Vector3f;
import net.risingworld.api.utils.Vector3i;
import net.risingworld.api.utils.WorldEditBatch;
import net.risingworld.api.worldelements.Interaction;
import net.risingworld.api.worldelements.World3DModel;
import net.risingworld.api.worldelements.World3DText;
import net.risingworld.api.worldelements.WorldArea;
import net.risingworld.api.worldelements.WorldElement;

public class PluginFunction extends Plugin{
    // private Methoden
    private Server server = getServer();
    private World world = getWorld();
    private DatabaseType databaseTypeRW = Database.class.newInstance().getType();
    private WorldDatabase worldDatabase = WorldDatabase.class.newInstance();
    private Cancellable cancellable = Cancellable.class.newInstance();

    public PluginFunction() throws IllegalAccessException, InstantiationException {
    }

    @Override
    public void onEnable() {
        System.out.println(server);
    }

    @Override
    public void onDisable() {

    }
}
