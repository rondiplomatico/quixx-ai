package dw.games.quixx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Player implements Comparable<Player> {

    @Getter private Card card = new Card(this);
    private final String name;

    public List<Option> computeWhiteDiceOptions(Dice dices) {
        List<Option> res = new ArrayList<>();
        int wsum = dices.getWhite1() + dices.getWhite2();
        for (Color c : Color.values()) {
            addOptionsForValue(res, wsum, c);
        }
        return res;
    }

    public List<Option> computeColorDiceOptions(Dice dices) {
        List<Option> res = new ArrayList<>();
        for (Color c : Color.values()) {
            int sum = dices.getWhite1() + dices.eyes(c);
            addOptionsForValue(res, sum, c);
            sum = dices.getWhite2() + dices.eyes(c);
            addOptionsForValue(res, sum, c);
        }
        return res;
    }

    private void addOptionsForValue(List<Option> res, int value, Color c) {
        if (card.canMark(c, value)) {
            res.add(new Option(c, value, false));
            if (card.canCloseMarking(c, value)) {
                res.add(new Option(c, value, true));
            }
        }
        if (card.canClose(c)) {
            res.add(new Option(c, -1, true));
        }
    }

    public List<Option> choose(List<Option> opt, List<Option> ownOpt) {
        List<Option> res = new ArrayList<>(2);
        Random r = new Random();
        if (r.nextDouble() < .05) {
            return res;
        }
        if (!opt.isEmpty()) {
            res.add(opt.get(r.nextInt(opt.size())));
        }
        if (r.nextDouble() < .05) {
            return res;
        }
        if (!ownOpt.isEmpty()) {
            res.add(ownOpt.get(r.nextInt(ownOpt.size())));
        }
        return res;
    }

    public void apply(Option o) {
        card.markField(o);
        if (o.isClose()) {
            card.close(o.getColor());
        }
    }

    public int getScore() {
        return card.getScore();
    }

    @Override
    public int compareTo(Player o) {
        return o.getScore() - getScore();
    }

    public String toString() {
        return name;
    }

    public void skip() {
        card.markSkipped();
    }

}
