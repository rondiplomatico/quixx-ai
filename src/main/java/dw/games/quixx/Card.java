package dw.games.quixx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class Card {

	@RequiredArgsConstructor
	public static class Row {

		@Getter
		private final boolean reverse;

		@Getter
		@Setter
		private boolean closed = false;

		private List<Integer> crossed = new ArrayList<>(12);

		public void mark(int number) {
			assert number < 13;
			assert number > 0;
			assert crossed.isEmpty()
					|| (reverse ? crossed.get(crossed.size() - 1) > number : crossed.get(crossed.size() - 1) < number);
			assert !closed;

			crossed.add(number);
		}

		public int rightMostValue() {
			return crossed.get(crossed.size() - 1);
		}

		public int numMarked() {
			return crossed.size() + (closed ? 1 : 0);
		}

		public boolean canClose() {
			return rightMostValue() == (reverse ? 2 : 12);
		}

	}

	Map<Color, Row> rows = new HashMap<>();

	@Getter
	private int skipped = 0;

	public Card() {
		rows.put(Color.Red, new Row(false));
		rows.put(Color.Green, new Row(false));
		rows.put(Color.Blue, new Row(true));
		rows.put(Color.Yellow, new Row(true));
	}

	public boolean canMark(Color c, int value) {
		Row r = rows.get(c);
		return r.reverse ? r.rightMostValue() > value : r.rightMostValue() < value;
	}

	public boolean canClose(Color color) {
		return rows.get(color).canClose();
	}

	public void mark(Option o) {
		Row r = rows.get(o.getColor());
		r.mark(o.getValue());
		if (o.isClose()) {
			r.setClosed(true);
		}
	}

	public void markSkipped() {
		assert skipped < Quixx.MAX_FAILED_MOVES;
		skipped++;
	}
	
	public int getScore() {
		return 0;
	}

}
