package com.minegusta.mgracesredone.util;

import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardUtil {
    private static Scoreboard board;
    private static Objective objective;

    public static void setBoard() {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = board.registerNewObjective("racehealth", "health");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective.setDisplayName(ChatColor.RED + "Health");

        for (RaceType type : RaceType.values()) {
            Team team = board.registerNewTeam(type.getName());
            team.setPrefix(type.getTag() + " ");
            team.setDisplayName(type.getTag() + " ");
            team.setNameTagVisibility(NameTagVisibility.ALWAYS);
            team.setCanSeeFriendlyInvisibles(false);
        }
    }

    public static void addScoreBoard(Player p, RaceType race) {
        Team team = board.getTeam(race.getName());
        team.addPlayer(p);
        p.setScoreboard(board);
    }

    public static void removeScoreBoard(Player p) {
        p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
}
