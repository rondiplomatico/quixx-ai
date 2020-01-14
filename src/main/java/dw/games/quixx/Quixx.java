package dw.games.quixx;

import java.util.ArrayList;
import java.util.List;

import dw.games.quixx.Dice.Roll;

public class Quixx {

	public static final int MAX_FAILED_MOVES = 4;
	private final Dice dice = new Dice();

	List<Player> players = new ArrayList<>(5);
	private int currentPlayer = 0;

	public Quixx() {

	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public void play() {
		while (true) {
			// Roll dices
			dice.roll();
			/*
			 * Let players know the result & act. The current player get's two possible
			 * moves (2 white dice plus colored ones)
			 */
			for (int i = currentPlayer; i < players.size() + currentPlayer; i++) {
				int pos = i % players.size();
				Player p = players.get(pos);
				List<Option> opt = p.computeGeneralOptions(dice);
				List<Option> ownOpt = null;
				if (i == currentPlayer) {
					ownOpt = p.computeOwnOptions(dice);
				}
				List<Option> selected = p.choose(opt, ownOpt);

				for (Option o : selected) {
					p.apply(o);
				}

			}

			// Check for end of game
			
			if (done()) {
				break;
			}
			currentPlayer++;
		}
		// Sieger ausgeben.
	}

	private boolean done() {
		return false;
	}

}
