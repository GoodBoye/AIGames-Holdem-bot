package move;

/**
 * move.Move - Created on 1-9-17
 *
 * Stores a move. Can parse it from string and also convert to string
 * to for output to the engine.
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class Move {

    private MoveType moveType;
    private Integer amount;  // Only used with raise

    public Move(MoveType moveType, int amount) {
        this.moveType = moveType;
        this.amount = amount;
    }

    public Move(MoveType moveType) {
        this.moveType = moveType;
    }

    public Move(String input) {
        String[] split = input.split("_");

        this.moveType = MoveType.fromString(split[0]);

        if (split.length > 1) {
            this.amount = Integer.parseInt(split[1]);
        }
    }

    public String toString() {
        if (this.moveType == MoveType.RAISE) {
            return String.format("%s_%s", this.moveType, this.amount);
        }

        return this.moveType.toString();
    }

    public MoveType getMoveType() {
        return this.moveType;
    }

    public Integer getAmount() {
        return this.amount;
    }
}
