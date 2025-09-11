package figures;

import javafx.scene.canvas.GraphicsContext;
import serialize.Serializable;

public interface Figure extends Serializable {
    public Figure move(Vector to);

    public void draw(GraphicsContext grc);

    public boolean equals(Object other);

    public int hashCode();
}
