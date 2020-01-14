package dw.games.quixx;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private Card card = new Card();

	public List<Option> computeGeneralOptions(Dice dices) {
		List<Option> res = new ArrayList<>();
		int wsum = dices.getWhite1() + dices.getWhite2();
		for (Color c : Color.values()) {
			if (card.canMark(c, wsum))
				res.add(new Option(c, wsum));
		}
		return res;
	}

	public List<Option> computeOwnOptions(Dice dices) {
		List<Option> res = new ArrayList<>();
		for (Color c : Color.values()) {
			int sum = dices.getWhite1() + dices.eyes(c);
			if (card.canMark(c, sum))
				res.add(new Option(c, sum));
			sum = dices.getWhite2() + dices.eyes(c);
			if (card.canMark(c, sum))
				res.add(new Option(c, sum));
		}
		return res;
	}

	public List<Option> choose(List<Option> opt, List<Option> ownOpt) {
		List<Option> res = new ArrayList<>(2);
		return res;
	}

	public void apply(Option o) {
		if (Option.SKIP.equals(o)) {
			card.markSkipped();
			return;
		}
		card.mark(o);
	}

//	if (card.getFailedPlacements() <= Quixx.MAX_FAILED_MOVES) {
//	res.add(Action.Skip);
//}

}
