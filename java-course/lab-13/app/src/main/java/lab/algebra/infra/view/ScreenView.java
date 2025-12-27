package lab.algebra.infra.view;

import lab.core.model.Pos;
import lab.core.model.State.State;

public interface ScreenView {
    void init(int size);
    void set(Pos pos, State state);
}
