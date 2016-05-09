package com.minegusta.mgracesredone.playerdata;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.files.FileManager;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.manager.AbilityFileManager;
import com.minegusta.mgracesredone.util.BindUtil;
import com.minegusta.mgracesredone.util.ScoreboardUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class MGPlayer {

    private String uuid;
    private String name;
    private RaceType raceType;
    private FileConfiguration conf;
    private Binds binds;
    private double health;
    private int perkpoints;
    private ConcurrentMap<AbilityType, Integer> abilities = Maps.newConcurrentMap();

    private void buildMGPlayer(String uuid, FileConfiguration f) {
        this.uuid = uuid;
        this.name = Bukkit.getPlayer(UUID.fromString(uuid)).getName();
        this.conf = f;
        this.raceType = RaceType.valueOf(conf.getString("racetype", "HUMAN"));
        this.perkpoints = conf.getInt("perkpoints", 0);
        this.health = conf.getDouble("health", getHealth());

        List<Bind> binds = Lists.newArrayList();
        if (conf.isSet("binds")) {
            ConfigurationSection bindConf = conf.getConfigurationSection("binds");

            for (String s : bindConf.getKeys(false)) {
                try {
                    Material item = Material.valueOf(bindConf.getString(s + ".item"));
                    short data = (short) bindConf.getInt(s + ".data");
                    AbilityType abilityType = AbilityType.valueOf(bindConf.getString(s + ".ability"));
                    Bind bind = new Bind(abilityType, item, data);
                    binds.add(bind);
                } catch (Exception ignored) {
                }
            }
        }
        this.binds = new Binds(binds);

        AbilityFileManager.loadAbilities(this);

        updateAttributes();
    }

    public MGPlayer(Player p, FileConfiguration f) {
        buildMGPlayer(p.getUniqueId().toString(), f);
    }

    public MGPlayer(UUID uuid, FileConfiguration f) {
        buildMGPlayer(uuid.toString(), f);
    }

    public MGPlayer(String uuid, FileConfiguration f) {
        buildMGPlayer(uuid, f);
    }

    //BINDS//
    //------------------------------------------------------------------------------//

    public List<Bind> getBinds() {
        return binds.getBinds();
    }

    public boolean isBind(Material item, short data, boolean ignoreData) {
        return binds.isBind(item, data, ignoreData);
    }

    public Optional<AbilityType> getBindForItem(Material item, short data, boolean ignoreData) {
        return binds.getAbilityForItem(item, data, ignoreData);
    }

    public void addBind(Material item, AbilityType type, short data) {
        removeBind(item, data, BindUtil.ignoreItemData(item));
        Bind bind = new Bind(type, item, data);
        binds.addBind(bind);
    }

    public List<Bind> getBindForAbility(AbilityType type) {
        return binds.getBindForAbility(type);
    }

    public void resetBinds() {
        binds.getBinds().clear();
    }

    public void removeBind(Material item, short data, boolean ignoreData) {
        binds.getBinds().stream().filter(b -> b.getItem() == item && (b.getData() == data || ignoreData)).forEach(b -> {
            binds.removeBind(b);
        });
    }


    //------------------------------------------------------------------------------//
    public void addAbility(AbilityType type, int level) {
        abilities.put(type, level);
    }

    public void removeAbility(AbilityType type) {
        if (abilities.containsKey(type)) ;
    }

    public void clearAbilities() {
        abilities.clear();
    }

    public boolean hasAbility(AbilityType ability) {
        return abilities.containsKey(ability);
    }

    public ConcurrentMap<AbilityType, Integer> getAbilities() {
        return abilities;
    }

    public int getAbilityLevel(AbilityType ability) {
        try {
            return abilities.get(ability);
        } catch (Exception Ignored) {
            return 0;
        }
    }

    public int getPerkPoints() {
        return perkpoints;
    }

    public void setPerkPoints(int newPoints) {
        this.perkpoints = newPoints;
        if (perkpoints > 1000) perkpoints = 1000;
    }

    public void addPerkPoints(int added) {
        perkpoints = perkpoints + added;
        if (perkpoints > 1000) perkpoints = 1000;
    }

    //Only removes points when you can afford it (wont drop below 0).
    public boolean removePerkPoints(int removed) {
        if (perkpoints - removed >= 0) {
            perkpoints = perkpoints - removed;
            if (perkpoints > 1000) perkpoints = 1000;
            return true;
        }
        return false;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(UUID.fromString(uuid));
    }

    public RaceType getRaceType() {
        return raceType;
    }

    public void setRaceType(RaceType raceType) {
        this.raceType = raceType;
        updateHealth();
        updateScoreboard();
        //3 perkpoints to start with.
        perkpoints = 3;
        abilities.clear();
        binds.getBinds().clear();
        saveFile();
    }

    public UUID getUniqueId() {
        return UUID.fromString(uuid);
    }

    public String getUniqueIdAsString() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setHealth() {
        getRaceType().setHealth(getPlayer(), health);
    }

    public void restoreHealth() {
        health = getHealth();
        getPlayer().setHealthScaled(true);
        getPlayer().setHealthScale(20);
        getPlayer().setMaxHealth(20);
    }

    public double getStoredHealth() {
        return health;
    }

    public double getHealth() {
        return getPlayer().getHealth();
    }

    //Update all the values here
    public void updateConfig() {
        conf.set("racetype", raceType.name());
        conf.set("perkpoints", perkpoints);
        conf.set("health", getHealth());

        if (conf.isSet("binds")) {
            conf.set("binds", null);
        }

        if (!binds.getBinds().isEmpty()) {
            for (Bind b : binds.getBinds()) {
                conf.set("binds." + b.getAbilityType().name().toLowerCase() + ".item", b.getItem().name());
                conf.set("binds." + b.getAbilityType().name().toLowerCase() + ".data", b.getData());
                conf.set("binds." + b.getAbilityType().name().toLowerCase() + ".ability", b.getAbilityType().name());
            }
        }


        AbilityFileManager.saveAbilities(this);
    }

    public FileConfiguration getConfig() {
        return conf;
    }

    public void saveFile() {
        updateConfig();
        FileManager.save(uuid, conf);
    }

    public void updateHealth() {
        if (WorldCheck.isEnabled(getPlayer().getWorld())) {
            setHealth();
            return;
        }
        restoreHealth();
    }

    public boolean isRace(RaceType race) {
        return raceType == race;
    }

    public void updateAttributes() {
        updateHealth();
        updateScoreboard();
    }

    //Display above head

    public void updateScoreboard() {
        if (WorldCheck.isEnabled(getPlayer().getWorld())) {
            ScoreboardUtil.addScoreBoard(getPlayer(), getRaceType());
        } else {
            ScoreboardUtil.removeScoreBoard(getPlayer());
        }
    }
}
