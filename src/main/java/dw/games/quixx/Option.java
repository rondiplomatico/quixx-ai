package dw.games.quixx;

import lombok.Data;

@Data
public class Option {

	public static final Option SKIP = new Option(null, -1, false);

	private final Color color;
	private final int value;
	private final boolean close;

}
