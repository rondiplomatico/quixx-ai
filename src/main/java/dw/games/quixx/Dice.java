package dw.games.quixx;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Dice {

    private static final Random rnd = new Random();

    private Set<Color> colors = new HashSet<>(Arrays.asList(Color.values()));

    @Getter private int white1;
    @Getter private int white2;
    private int r;
    private int g;
    private int b;
    private int y;

    public int eyes(Color col) {
        if (Color.Red == col)
            return r;
        if (Color.Green == col)
            return g;
        if (Color.Blue == col)
            return b;
        return y;
    }

    public void roll() {
        white1 = rand();
        white2 = rand();
        r = colors.contains(Color.Red) ? rand() : -1;
        g = colors.contains(Color.Green) ? rand() : -1;
        b = colors.contains(Color.Blue) ? rand() : -1;
        y = colors.contains(Color.Yellow) ? rand() : -1;
    }

    private int rand() {
        return rnd.nextInt(6) + 1;
    }

    public String toString() {
        return "W1:" + white1 + ", W2:" + white2 + ", R:" + r + ", G:" + g + ", B:" + b + ", Y:" + y;
    }

}
