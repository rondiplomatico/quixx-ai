package dw.games.quixx;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Option {

    private final Color color;
    private final int value;
    private final boolean close;

}
