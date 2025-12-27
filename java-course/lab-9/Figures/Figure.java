package figures;

import Serialize.Serializable;

public interface Figure extends Serializable {
    public Figure move(Vector to);

    public void draw();

    public boolean equals(Object other);

    public int hashCode();
}
