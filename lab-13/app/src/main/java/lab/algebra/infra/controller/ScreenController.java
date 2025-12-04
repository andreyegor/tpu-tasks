package lab.algebra.infra.controller;

import java.util.function.Consumer;

import lab.core.model.Click;

public interface ScreenController {
    public void init(int size);
    public void setClickHandler(Consumer<Click> handler);
}
