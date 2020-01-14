package dw.games.quixx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dw.games.quixx.Dice.Color;
import dw.games.quixx.Dice.Roll;

public class DiceSet {

	List<Dice> dices = new ArrayList<>(6);

	public DiceSet() {
		dices.add(new Dice(Color.White));
		dices.add(new Dice(Color.White));
		dices.add(new Dice(Color.Red));
		dices.add(new Dice(Color.Green));
		dices.add(new Dice(Color.Yellow));
		dices.add(new Dice(Color.Blue));
	}

	public List<Roll> roll() {
		return dices.stream().map(d -> d.roll()).collect(Collectors.toList());
	}

}
