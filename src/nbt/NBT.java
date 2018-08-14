package nbt;

import de.tr7zw.itemnbtapi.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;

public class NBT extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 2);

            item.setDurability((short) 500);

            NBTItem nbtItem = new NBTItem(item);
            

/*
        NBTList nbtList = nbtItem.getList("AttributeModifiers", NBTType.NBTTagCompound);

        NBTListCompound mod1 = nbtList.addCompound();
        mod1.setString("AttributeName", "generic.attackDamage");
        mod1.setString("Name", "generic.attackDamage");
        mod1.setDouble("Amount", 10);
        mod1.setInteger("Operation", 0);
        mod1.setInteger("UUIDLeast", 59764);
        mod1.setInteger("UUIDMost", 31483);

        NBTListCompound mod2 = nbtList.addCompound();
        mod2.setString("AttributeName", "generic.attackSpeed");
        mod2.setString("Name", "generic.attackSpeed");
        mod2.setInteger("Amount", 1024);
        mod2.setInteger("UUIDLeast", 59764);
        mod2.setInteger("UUIDMost", 31483);
*/
            nbtItem.setBoolean("Unbreakable", true);

            NBTCompound display = nbtItem.addCompound("display");
            display.setString("Name", "曙光的劍");
            NBTList nbtList2 = display.getList("Lore", NBTType.NBTTagString);
            nbtList2.addString("測試用訊息１");
            nbtList2.addString("測試用訊息２");
            nbtList2.addString("測試用訊息３");

            NBTList nbtList3 = nbtItem.getList("ench", NBTType.NBTTagCompound);
            NBTListCompound ench1 = nbtList3.addCompound();
            ench1.setInteger("id", 16);
            ench1.setInteger("lvl", 10);
            NBTListCompound ench2 = nbtList3.addCompound();
            ench2.setInteger("id", 20);
            ench2.setInteger("lvl", 10);

            item = nbtItem.getItem();

            Player p = (Player) sender;

            p.getInventory().addItem(item);
            return true;
        } else if (args.length == 1) {
            Player p = (Player) sender;

            ItemStack item = p.getInventory().getItemInMainHand();
            NBTItem nbtItem = new NBTItem(item);

            NBTCompound display = nbtItem.addCompound("display");
            display.setString("Name", "曙光的劍");
            NBTList nbtList2 = display.getList("Lore", NBTType.NBTTagString);
            nbtList2.addString("測試用訊息１");
            nbtList2.addString("測試用訊息２");
            nbtList2.addString("測試用訊息３");

            NBTList nbtList3 = nbtItem.getList("ench", NBTType.NBTTagCompound);
            NBTListCompound ench1 = nbtList3.addCompound();
            ench1.setInteger("id", 16);
            ench1.setInteger("lvl", 10);
            NBTListCompound ench2 = nbtList3.addCompound();
            ench2.setInteger("id", 20);
            ench2.setInteger("lvl", 10);

            item = nbtItem.getItem();

            p.getInventory().setItemInHand(item);

            return true;
        } else if (args.length == 2) {
            Player p = (Player) sender;

            ItemStack stack = p.getInventory().getItemInMainHand();

            saveFile(stack, "binary");

            return true;
        } else if (args.length == 3) {
            Player p = (Player) sender;

            getFile<ItemStack> stack = new getFile<>();
            ItemStack item = stack.loadFile(p, "binary");

            p.getInventory().setItemInHand(item);

            return true;
        } else if (args.length == 4) {
            Player p = (Player) sender;

//            Inventory inv = Bukkit.createInventory(null, 54, "擴增背包");
//            inv.addItem(new ItemStack(Material.DIAMOND, 1));

            Inventory inv = b64ToInv("w");

            p.openInventory(inv);
            return true;
        }
        return true;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);

        NBTItem nbtItem = new NBTItem(item);

        NBTList nbtList = nbtItem.getList("AttributeModifiers", NBTType.NBTTagCompound);

        NBTListCompound mod1 = nbtList.addCompound();
        mod1.setString("AttributeName", "generic.attackDamage");
        mod1.setString("Name", "generic.attackDamage");
        mod1.setDouble("Amount", 10);
        mod1.setInteger("Operation", 0);
        mod1.setInteger("UUIDLeast", 59764);
        mod1.setInteger("UUIDMost", 31483);

        NBTListCompound mod2 = nbtList.addCompound();
        mod2.setString("AttributeName", "generic.attackSpeed");
        mod2.setString("Name", "generic.attackSpeed");
        mod2.setInteger("Amount", 1024);
        mod2.setInteger("UUIDLeast", 59764);
        mod2.setInteger("UUIDMost", 31483);

        nbtItem.setBoolean("Unbreakable", true);

        NBTCompound display = nbtItem.addCompound("display");
        display.setString("Name", "曙光的劍");
        NBTList nbtList2 = display.getList("Lore", NBTType.NBTTagString);
        nbtList2.addString("測試用訊息１");
        nbtList2.addString("測試用訊息２");
        nbtList2.addString("測試用訊息３");

        item = nbtItem.getItem();

        Player p = e.getPlayer();

        p.getInventory().addItem(item);

        p.sendMessage(item.getAmount() + "");
        p.sendMessage(item.getDurability() + "");
        p.sendMessage(item.getItemMeta() + "");
    }

    @EventHandler
    public void onInventoryInteract(InventoryCloseEvent e) {
        if (!e.getInventory().getName().equals("擴增背包")) return;

        Player p = (Player) e.getPlayer();
        p.sendMessage("哈囉");

        toBase64(e.getInventory());

    }

    boolean saveFile(Object o, String name) {
        try {
            FileOutputStream fo = new FileOutputStream(name + ".txt");
            BukkitObjectOutputStream bos = new BukkitObjectOutputStream(fo);
            bos.writeObject(o);

            bos.close();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    class getFile<T> {
        T loadFile(Player p, String name) {
            try {
                FileInputStream fi = new FileInputStream(name + ".txt");
                byte[] str = new byte[fi.available()];
                fi.read(str);

                String s = new String(str);

                ByteArrayInputStream is = new ByteArrayInputStream(str);
                ObjectInputStream ois = new BukkitObjectInputStream(is);
                T object = (T) ois.readObject();

                return object;

            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }

            return (T) Bukkit.createInventory(null, 54, "擴增背包");
        }
    }

    String toBase64(Inventory inv) {
        try {
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            BukkitObjectOutputStream outStream = new BukkitObjectOutputStream(s);

            outStream.writeInt(inv.getSize());
            //outStream.writeChars(inv.getName());

            for (int i = 0; i < inv.getSize(); i++) {
                outStream.writeObject(inv.getItem(i));
            }

            File f = new File("inv.txt");

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(Base64Coder.encodeLines(s.toByteArray()).getBytes());

            outStream.close();
            fos.close();

            return Base64Coder.encodeLines(s.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    Inventory b64ToInv(String b64) {
        File f = new File("inv.txt");

        if (!f.exists()) {
            Bukkit.getServer().broadcastMessage("檔案不存在");
            return Bukkit.createInventory(null, 54, "擴增背包");
        }

        try {
            FileInputStream fis = new FileInputStream(f);
            byte[] b = new byte[fis.available()];

            fis.read(b);
            fis.close();

            String str = new String(b);

//            ByteArrayInputStream s = new ByteArrayInputStream(Base64Coder.decodeLines(b64));
            ByteArrayInputStream s = new ByteArrayInputStream(Base64Coder.decodeLines(str));
            BukkitObjectInputStream inStream = new BukkitObjectInputStream(s);

            Inventory inv = Bukkit.createInventory(null, inStream.readInt(),"擴增背包");

            for (int i = 0; i < inv.getSize(); i++) {
                inv.setItem(i, (ItemStack) inStream.readObject());
            }

            inStream.close();
            s.close();

            return inv;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
