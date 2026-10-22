package practice.workingsystem.snakeladder;

public class Ladder {
    private final int bottom;
    private final int top;

    public Ladder(int bottom, int top) {
        if(top <= bottom) {
            throw new IllegalArgumentException("Ladder top must be greater than it's bottom");
        }

        this.bottom = bottom;
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTop() {
        return top;
    }
}
