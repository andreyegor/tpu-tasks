package nonogram;

import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class NonogramGame extends Nonogram {
    protected BitSet refImage = new BitSet(0);
    protected int diffImages = 0;

    public NonogramGame(GridScreen screen) {
        super(screen);
    }

    public void create(BitSet refImage, Integer ox, Integer oy) {
        super.create(new BitSet(refImage.size()), ox, oy);

        this.refImage = (BitSet) refImage.clone();
        this.diffImages = refImage.cardinality();

        List<List<Integer>> xHints = IntStream.range(0, oy)
                .mapToObj(y -> countContinuous(ox, x -> refState(x, y)))
                .collect(Collectors.toList());
        List<List<Integer>> yHints = IntStream.range(0, ox)
                .mapToObj(x -> countContinuous(oy, y -> refState(x, y)))
                .collect(Collectors.toList());
        oxHintsShift = xHints.stream().mapToInt(List::size).max().orElse(0);
        oyHintsShift = yHints.stream().mapToInt(List::size).max().orElse(0);

        screen.blank(ox + oxHintsShift, oy + oyHintsShift);

        drawHints(
                xHints,
                oxHintsShift,
                (Integer line, Integer pos) -> pos,
                (Integer line, Integer pos) -> oyHintsShift + line);
        drawHints(
                yHints,
                oyHintsShift,
                (Integer line, Integer pos) -> oxHintsShift + line,
                (Integer line, Integer pos) -> pos);

        screen.setOnCellClick((Integer x, Integer y) -> swapOrWin(x - oxHintsShift, y - oyHintsShift));
    }

    public void swapOrWin(int x, int y) {
        if (isWon()) {
            wonAnimation();
            return;
        }
        swap(x, y);
        if (isWon()) {
            wonAnimation();
            return;
        }
    }

    public void swap(int x, int y) {
        super.swap(x, y);
        diffImages += state(x, y) == refState(x, y) ? -1 : +1;
    }

    public Boolean isWon() {
        return diffImages == 0;
    }

    private boolean refState(int x, int y) {
        return refImage.get(y * ox + x);
    }

    private void wonAnimation() {
        final double baseDelayMs = 40;
        final double gapABetweenWavesMs = 150;

        Timeline timeline = new Timeline();

        for (int i = 0; i < 4; i++) {
            for (int x = 0; x < ox; x++) {
                for (int y = 0; y < oy; y++) {
                    final int fx = x;
                    final int fy = y;
                    int distance = fx + fy;
                    double delay = baseDelayMs * distance + Math.pow(2, i) * gapABetweenWavesMs;
                    timeline.getKeyFrames().add(
                            new KeyFrame(Duration.millis(delay),
                                    e -> swap(fx, fy)));
                }
            }
        }

        timeline.play();
    }

}
