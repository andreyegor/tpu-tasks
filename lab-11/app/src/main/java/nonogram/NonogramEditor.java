// package nonogram;

// import java.util.function.BiFunction;

// import javafx.animation.KeyFrame;
// import javafx.animation.Timeline;
// import javafx.util.Duration;

// public class NonogramEditor {
//     private GridScreen screen;

//     private Nonogram field;
//     private int ox, oy;
//     private int oxHintsShift, oyHintsShift;

//     public NonogramEditor(GridScreen screen) {
//         this.screen = screen;
//     }

//     public void create(Nonogram nonogram) {
//         if (nonogram == null) {
//             throw new IllegalArgumentException("nonogarm can not be empty");
//         }
//         field = new BitGrid(ref.getOx(), ref.getOy());
//         ox = ref.getOx();
//         oy = ref.getOy();

//         var hintsShift = nonogram.getLongestHint();
//         oxHintsShift = hintsShift.getKey();
//         oyHintsShift = hintsShift.getValue();

//         screen.blank(ox + oxHintsShift, oy + oyHintsShift);

//         var hints = nonogram.getHints();
//         drawAllHints(hints.getKey(), hints.getValue());

//         screen.setOnCellClick((Integer x, Integer y) -> flipOnClickAlg(x - oxHintsShift, y - oyHintsShift));
//     }

//     public void flipOnClickAlg(int x, int y) {
//         throwWhenGameNotCreated();
//         if (x < 0 || y < 0 || x >= ox || x >= oy) {
//             return;
//         }
//         if (lock.get(x, y) || globalLock) {
//             return;
//         }
//         if (isWon()) {
//             wonAnimation();
//             return;
//         }
//         flip(x, y);
//         if (isWon()) {
//             wonAnimation();
//             return;
//         }
//     }

//     public void flip(int x, int y) {
//         throwWhenGameNotCreated();
//         var newState = !field.get(x, y);
//         field.set(x, y, newState);
//         diff += newState == ref.get(x, y) ? -1 : +1;
//         screen.set(oxHintsShift + x, oyHintsShift + y, newState);
//     }

//     public Boolean isWon() {
//         return diff == 0;
//     }

//     private void throwWhenGameNotCreated() {
//         if (ref == null) {
//             throw new IllegalStateException("Game not created");
//         }
//     }

//     private void drawAllHints(int[][] oxHints, int[][] oyHints) {
//         drawHints(
//                 oxHints,
//                 oyHintsShift,
//                 (Integer line, Integer pos) -> oxHintsShift + line,
//                 (Integer line, Integer pos) -> pos);
//         drawHints(
//                 oyHints,
//                 oxHintsShift,
//                 (Integer line, Integer pos) -> pos,
//                 (Integer line, Integer pos) -> oyHintsShift + line);
//     }

//     private void drawHints(
//             int[][] hints,
//             int primaryShift,
//             BiFunction<Integer, Integer, Integer> screenXFunc,
//             BiFunction<Integer, Integer, Integer> screenYFunc) {
//         for (int i = 0; i < hints.length; i++) {
//             var line = hints[i];
//             if (line == null)
//                 continue;
//             for (int j = 0; j < line.length; j++) {
//                 int pos = primaryShift - line.length + j;
//                 int sx = screenXFunc.apply(i, pos);
//                 int sy = screenYFunc.apply(i, pos);
//                 screen.hint(sx, sy, line[j]);
//             }
//         }
//     }

//     private void wonAnimation() {
//         final double baseDelayMs = 40;
//         final double gapABetweenWavesMs = 150;

//         globalLock = true;
//         Timeline timeline = new Timeline();
//         for (int i = 0; i < 4; i++) {
//             for (int x = 0; x < ox; x++) {
//                 for (int y = 0; y < oy; y++) {
//                     final int fx = x;
//                     final int fy = y;
//                     int distance = fx + fy;
//                     double delay = baseDelayMs * distance + Math.pow(2, i) * gapABetweenWavesMs;
//                     timeline.getKeyFrames().add(
//                             new KeyFrame(Duration.millis(delay),
//                                     e -> flip(fx, fy)));
//                 }
//             }
//         }
//         timeline.setOnFinished(e -> globalLock = false);
//         timeline.play();
//     }
// }
