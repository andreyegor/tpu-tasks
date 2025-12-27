package lab.core.logic;

import lab.algebra.infra.controller.ScreenController;
import lab.algebra.infra.view.*;
import lab.core.model.Click;
import lab.core.model.Grid;
import lab.core.model.Pos;
import lab.core.model.State.State;
import lab.core.model.State.Bomb;
import lab.core.model.State.Clear;
import lab.core.model.State.Flag;
import lab.core.model.State.Fog;

public class GameLogic {
    private final int DEFAULT_GRID_SIZE = 10;
    private final int DEFAULT_BOMBS_CNT = 25;

    private int gridSize;
    private int bombsCnt;
    private boolean gameOver = false;

    ScreenView scv;
    MessagesView msv;
    SettingsView stn;
    ScreenController scc;

    private Grid<State> stateGrid;
    private Grid<Boolean> bombGrid;

    public GameLogic(ScreenView scv, MessagesView msv, ScreenController scc) {
        this.scv = scv;
        this.msv = msv;
        this.scc = scc;

    }

    public void start(Integer gridSize, Integer bombsCnt) {
        this.gridSize = (gridSize == null) ? DEFAULT_GRID_SIZE : gridSize;
        this.bombsCnt = (bombsCnt == null) ? DEFAULT_BOMBS_CNT : bombsCnt;

        stateGrid = new Grid<State>(this.gridSize, this.gridSize, () -> new Fog());

        scv.init(gridSize);
        scc.init(gridSize);

        for (var pos : stateGrid.iteratePos()) {
            scv.set(pos, new Fog());
        }

        scc.setClickHandler(this::handleClick);
    }

    private void handleClick(Click click) {
        if (click.isRightClick()) {
            toggleFlag(click.pos());
        } else {
            openCell(click.pos());
            if (checkWin()) {
                msv.win("Победа!");
                gameOver = true;
                return;
            }
        }
    }

    private void openCell(Pos pos) {
        if (gameOver) {
            return;
        }

        if (bombGrid == null) {
            generateBombs(pos);
        }
        if (stateGrid.get(pos) instanceof Clear) {
            return;
        }
        if (bombGrid.get(pos)) {
            for (var i : bombGrid.iteratePos()) {
                if (bombGrid.get(i)) {
                    stateGrid.set(i, new Bomb());
                    scv.set(i, stateGrid.get(i));
                }
            }
            msv.loose("Поражение");
            gameOver = true;
            return;
        }
        int bombsAround = countBombsAround(pos);
        stateGrid.set(pos, new Clear(bombsAround));
        scv.set(pos, stateGrid.get(pos));
        if (bombsAround != 0)
            return;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 && dy != 0)
                    continue;
                int nx = pos.x() + dx, ny = pos.y() + dy;
                if (nx < 0 || nx >= gridSize || ny < 0 || ny >= gridSize)
                    continue;
                var nPos = new Pos(nx, ny);
                if (stateGrid.get(nPos) instanceof Fog && !bombGrid.get(nPos)) {
                    openCell(nPos);
                }
            }
        }

    }

    private int countBombsAround(Pos pos) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = pos.x() + dx, ny = pos.y() + dy;
                if (nx >= 0 && nx < gridSize && ny >= 0 && ny < gridSize && bombGrid.get(new Pos(nx, ny))) {
                    count++;
                }
            }
        }
        return count;
    }

    private void toggleFlag(Pos pos) {
        switch (stateGrid.get(pos)) {
            case Fog f ->
                stateGrid.set(pos, new Flag());
            case Flag f ->
                stateGrid.set(pos, new Fog());
            default -> {
            }
        }
        scv.set(pos, stateGrid.get(pos));
    }

    private void generateBombs(Pos firstPos) {
        bombGrid = new Grid<Boolean>(gridSize, gridSize, () -> false);
        java.util.Random rnd = new java.util.Random();
        int placed = 0;
        while (placed < bombsCnt) {
            var pos = new Pos(rnd.nextInt(gridSize), rnd.nextInt(gridSize));

            if (pos.equals(firstPos) || bombGrid.get(pos)) {
                continue;
            }
            bombGrid.set(pos, true);
            placed++;
        }
    }

    private boolean checkWin() {
        for (var pos : stateGrid.iteratePos()) {
            if (!bombGrid.get(pos) && !(stateGrid.get(pos) instanceof Clear))
                return false;
        }
        return true;
    }
}
