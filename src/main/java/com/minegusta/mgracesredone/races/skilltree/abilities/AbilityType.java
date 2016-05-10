package com.minegusta.mgracesredone.races.skilltree.abilities;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel.*;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora.*;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon.*;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf.*;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf.*;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn.*;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.vampire.*;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.werewolf.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public enum AbilityType {
    CANIS(new Canis()),
    CLAWS(new Claws()),
    CARNIVORE(new Carnivore()),
    GOLDRESISTANCE(new GoldResistance()),
    HOWL(new Howl()),
    LUNA(new Luna()),
    NOCTURNAL(new Nocturnal()),
    POUNCE(new Pounce()),
    DIG(new Dig()),
    COLDBLOODED(new ColdBlooded()),
    ENDERSHIELD(new EnderShield()),
    ENDRIFT(new EndRift()),
    PREDATOR(new Predator()),
    OTHERWORLDLY(new OtherWorldly()),
    PEARLPOWER(new PearlPower()),
    SHADOW(new Shadow()),
    TELEKINESIS(new Telekinesis()),
    WATERRESISTANCE(new WaterResistance()),
    BATTLEAXE(new BattleAxe()),
    BATTLECRY(new BattleCry()),
    COMBATANT(new Combatant()),
    EARTHQUAKE(new Earthquake()),
    MINER(new Miner()),
    PROJECTILEPROTECTION(new ProjectileProtection()),
    SPIRITAXE(new SpiritAxe()),
    STONESHAPE(new StoneShape()),
    TUNNLER(new Tunnler()),
    AQUAMAN(new AquaMan()),
    DROWNINGPOOL(new DrowningPool()),
    Fish(new Fish()),
    FROST(new Frost()),
    GLACIOUS(new Glacious()),
    HEATTOLLERANCE(new HeatTollerance()),
    ICEBARRAGE(new IceBarrage()),
    KHIONESBLESSING(new KhionesBlessing()),
    TIDALWAVE(new TidalWave()),
    GLIDE(new Glide()),
    HOLINESS(new Holiness()),
    HOLYRAIN(new HolyRain()),
    JUSTICE(new Justice()),
    NYCTOPHOBIA(new Nyctophobia()),
    //PRAYER(new Prayer()), //Remove prayer for now as it's replaced by DIVINEBLADE
    DIVINEBLADE(new DivineBlade()), //Replaced Prayer
    PURGE(new Purge()),
    STEELSKIN(new SteelSkin()),
    WHIRLWIND(new WhirlWind()),
    FIREPROOF(new FireProof()),
    HELLRIFT(new HellRift()),
    HELLISHTRUCE(new HellishTruce()),
    HELLSPAWN(new Hellspawn()),
    LAVALOVER(new LavaLover()),
    METEORSTORM(new MeteorStorm()),
    MINIONMASTER(new MinionMaster()),
    UNHOLYRAIN(new UnholyRain()),
    ENVIRONMENTALIST(new Environmentalist()),
    RANGER(new Ranger()),
    FORESTFRIEND(new ForestFriend()),
    ARROWRAIN(new ArrowRain()),
    ARROWNADO(new ArrowNado()),
    POINTYSHOOTY(new PointyShooty()),
    ANIMALRIDER(new AnimalRider()),
    NATURALIST(new Naturalist()),
    FLAMERESISTANCE(new FlameResistance()),
    BLOODLUST(new BloodLust()),
    REGENERATE(new Regenerate()),
    DARKBLOOD(new DarkBlood()),
    BATSHIELD(new BatShield()),
    MONSTERMASH(new MonsterMash()),
    VAMPIRICGRASP(new VampiricGrasp()),
    THRALL(new Thrall()),
    WOODBANE(new WoodBane()),
    BLINK(new Blink()),
    FRUITFANATIC(new FruitFanatic());


    private IAbility ability;

    AbilityType(IAbility ability) {
        this.ability = ability;
    }

    public String[] getDescriontion(int level) {
        return ability.getDescription(level);
    }

    public IAbility getAbility() {
        return ability;
    }

    public String getName() {
        return ability.getName();
    }

    public boolean canBind() {
        return ability.canBind();
    }

    public List<RaceType> getRaces() {
        return ability.getRaces();
    }

    /**
     * Get the cost
     *
     * @param level            The level to get the cost for.
     * @param totalPerksPoints Add this value divided by a number to the price.
     * @return The baseprice +
     */
    public int getCost(int level, int totalPerksPoints) {
        return ability.getPrice(level) + (totalPerksPoints / 13);
    }

    public int getBaseCost(int level) {
        return ability.getPrice(level);
    }

    public int getID() {
        return ability.getID();
    }

    public String getBindDescription() {
        return ability.getBindDescription();
    }

    public int getMaxLevel() {
        return ability.getMaxLevel();
    }

    public int getCooldown(int level) {
        return ability.getCooldown(level);
    }

    public IAbility.AbilityGroup getGroup() {
        return ability.getGroup();
    }

    public boolean run(Player player) {
        return ability.run(player);
    }

    public void run(Event event) {
        ability.run(event);
    }
}
