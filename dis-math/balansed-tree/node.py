from typing import Any


class Node:
    def __init__(
        self,
        key: Any,
        _parent=None,
        height: int = 0,
        _left=None,
        _right=None,
    ):
        self.key = key
        self._parent = _parent
        self._left = _left
        self._right = _right
        self._height = height
