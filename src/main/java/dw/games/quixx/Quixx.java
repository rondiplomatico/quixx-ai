package dw.games.quixx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quixx {

    public static final int MAX_FAILED_MOVES = 4;
    public static final int MAX_ROUNDS = 10000;
    private final Dice dice = new Dice();

    List<Player> players = new ArrayList<>(5);

    public static void main(String[] args) {
        Quixx q = new Quixx();
        q.addPlayer(new Player("Alfons"));
        q.addPlayer(new Player("Mark"));
        q.addPlayer(new Player("Judy"));
        q.addPlayer(new Player("Josh"));
        q.play();
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void play() {
        int round = 0;
        while (round < MAX_ROUNDS) {
            int currentPlayer = round % players.size();
            // Roll dices
            dice.roll();

            System.out.println("Round " + round + ": " + players.get(currentPlayer) + " rolls " + dice);

            /*
             * Let players know the result & act. The current player get's two possible
             * moves (2 white dice plus colored ones)
             */
            for (int i = currentPlayer; i < players.size() + currentPlayer; i++) {
                int pos = i % players.size();
                Player p = players.get(pos);
                int numOpts = 0;
                List<Option> opt = p.computeWhiteDiceOptions(dice);
                numOpts += opt.size();
                List<Option> ownOpt = new ArrayList<>(1);
                if (i == currentPlayer) {
                    ownOpt = p.computeColorDiceOptions(dice);
                    numOpts += ownOpt.size();
                }
                if (numOpts > 0) {
                    List<Option> selected = p.choose(opt, ownOpt);
                    if (selected.isEmpty()) {
                        p.skip();
                        System.out.println(p + " chooses to do nothing. Number of skips: " + p.getCard().getSkipped());
                        continue;
                    }
                    if (selected.size() > 2) {
                        p.skip();
                        System.out.println(p + " tries to cheat and returned more than 2 moves. Skipping. Number of skips: " + p.getCard().getSkipped());
                        continue;
                    }
                    p.apply(selected.get(0));
                    if (selected.size() == 1) {
                        continue;
                    }
                    if (!selected.get(1).equals(selected.get(0))) {
                        p.apply(selected.get(1));
                    } else {
                        System.out.println(p + " tries to mark the same field again. Poor choice - no move executed.");
                    }
                } else {
                    p.skip();
                    System.out.println(p + " has no options. Number of skips: " + p.getCard().getSkipped());
                }
            }

            if (done()) {
                break;
            }
            round++;
            System.out.println(" -- ");
        }
        Collections.sort(players);
        for (Player p : players) {
            System.out.println(p + ": " + p.getScore());
        }
    }

    private boolean done() {
        int closed = 0;
        for (Player p : players) {
            closed += p.getCard().numClosedRows();
            if (p.getCard().getSkipped() == Quixx.MAX_FAILED_MOVES) {
                System.out.println("Game finished, Player " + p + " has reached " + Quixx.MAX_FAILED_MOVES + " skipped moves.");
                return true;
            }
        }
        if (closed > 1) {
            System.out.println("Game done, " + closed + " rows closed.");
            return true;
        }
        return false;
    }

}
