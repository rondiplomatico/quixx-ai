package dw.games.quixx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class Card {

    public static final Map<Integer, Integer> POINTS = new HashMap<>(12);

    static {
        POINTS.put(0, 0);
        POINTS.put(1, 1);
        POINTS.put(2, 3);
        POINTS.put(3, 6);
        POINTS.put(4, 10);
        POINTS.put(5, 15);
        POINTS.put(6, 21);
        POINTS.put(7, 28);
        POINTS.put(8, 36);
        POINTS.put(9, 46);
        POINTS.put(10, 55);
        POINTS.put(11, 66);
        POINTS.put(12, 78);
    }

    public static class Row {

        @Getter private final boolean reverse;

        @Getter @Setter private boolean closed = false;

        private List<Integer> markedFields = new ArrayList<>(12);
        private List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        public Row(boolean reverse) {
            this.reverse = reverse;
            if (reverse) {
                Collections.reverse(values);
            }
        }

        public void mark(int field) {
            assert field < 13;
            assert field > 0;
            assert canMark(field);

            markedFields.add(field);
        }

        public int rightMostField() {
            return markedFields.get(markedFields.size() - 1);
        }

        public int numMarkers() {
            return markedFields.size() + (closed ? 1 : 0);
        }

        public boolean canMark(int field) {
            return !closed && (markedFields.isEmpty() || (reverse ? rightMostField() > field : rightMostField() < field));
        }

        public boolean canClose() {
            return markedFields.size() > 5 && markedFields.contains(reverse ? 2 : 12);
        }

        /***
         * 
         * Determines if the row can be closed after marking the specified value.
         * 
         * This is true, if all of the following requirements are met:
         * 1. There are >= 5 values marked already
         * 2. The specified value is 12 (red, yellow rows) or 2 (blue, green rows)
         * 3. The field is not marked yet.
         *
         * @param field
         * @return
         */
        public boolean canCloseMarking(int field) {
            return markedFields.size() >= 5 && canMark(field) && field == (reverse ? 2 : 12);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < values.size(); i++) {
                sb.append(values.get(i) + (markedFields.contains(values.get(i)) ? "X" : ""));
                if (i < values.size() - 1) {
                    sb.append("|");
                }
            }
            sb.append("]");
            if (closed) {
                sb.append("X");
            }
            return sb.toString();
        }

    }

    Map<Color, Row> rows = new HashMap<>();
    private final Player player;

    @Getter private int skipped = 0;

    public Card(Player p) {
        player = p;
        rows.put(Color.Red, new Row(false));
        rows.put(Color.Green, new Row(false));
        rows.put(Color.Blue, new Row(true));
        rows.put(Color.Yellow, new Row(true));
    }

    public boolean canMark(Color c, int field) {
        return rows.get(c).canMark(field);
    }

    public void markField(Option o) {
        Row r = rows.get(o.getColor());
        System.out.println(player + " tries to mark " + o.getValue() + " on " + o.getColor() + ": " + r);
        r.mark(o.getValue());
        System.out.println(player + " marks " + o.getValue() + " on " + o.getColor() + ": " + r);
        if (o.isClose()) {
            r.setClosed(true);
        }
    }

    public void markSkipped() {
        assert skipped < Quixx.MAX_FAILED_MOVES;
        skipped++;
    }

    public int getScore() {
        int score = 0;
        for (Color c : Color.values()) {
            score += POINTS.get(rows.get(c).numMarkers());
        }
        score -= skipped * 5;
        return score;
    }

    public boolean canClose(Color c) {
        return rows.get(c).canClose();
    }

    public boolean canCloseMarking(Color c, int value) {
        return rows.get(c).canCloseMarking(value);
    }

    public void close(Color color) {
        assert !rows.get(color).isClosed();
        rows.get(color).setClosed(true);
    }

    public int numClosedRows() {
        return rows.values().stream().mapToInt(r -> r.closed ? 1 : 0).sum();
    }

}
