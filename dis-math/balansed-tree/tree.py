from typing import Generator, Any
from node import Node


class Tree:
    def __init__(self, key=None) -> None:
        self._root: Node = None
        self.__length = 0
        if key:
            self.add(key)

    def __len__(self) -> int:
        return self.__length

    def __iter__(self) -> any:
        for node in self.__dfs(self._root):
            yield node

    def __getitem__(self, index: int) -> Any:
        if not isinstance(index, int):
            raise TypeError("Index must be an integer")
        for i, node in enumerate(self):
            if i == index:
                return node.key
        raise IndexError("Index out of range")

    def __contains__(self, item) -> bool:
        return self.peek(item)

    def add(self, key) -> None:
        if not self._root:
            self._root = Node(key, height=1)
            return
        new = self._root
        while True:
            if new.key < key:
                if not new._right:
                    new._right = Node(key, new, height=1)
                    break
                new = new._right
            elif new.key > key:
                if not new._left:
                    new._left = Node(key, new, height=1)
                    break
                new = new._left
            else:
                return
        self.__length += 1
        self._root = self.__balance(new)

    def discard(self, key) -> bool:
        node = self._peek(key)
        if node is None:
            return False
        self.__extract_node(node)
        return True

    def peek(self, key) -> bool:
        return self._peek(key) is not None

    def __extract_node(self, extractable: Node) -> Node:
        if extractable._left and extractable._right:
            swap = self.__local_min(extractable._right)
            swap.key, extractable.key = extractable.key, swap.key
            swap, extractable = extractable, swap
            balance_node = extractable._parent
            self.__kill_child(extractable)
            self.__balance(balance_node)
            if self._root == extractable:
                self._root = swap
        elif self._root == extractable:
            self._root = extractable._left if extractable._left else extractable._right
            self.__kill_child(extractable)
        else:
            balance_node = extractable._parent
            self.__kill_child(extractable)
            self.__balance(balance_node)
        self.__length -= 1
        return extractable

    def __dfs(self, node: Node) -> Generator:
        if node is None:
            return
        if node._left:
            yield from self.__dfs(node._left)
        yield node
        if node._right:
            yield from self.__dfs(node._right)

    def _peek(self, key) -> Node | None:
        this = self._root
        while this and this.key != key:
            if this.key > key:
                this = this._left
            elif this.key < key:
                this = this._right
        return this

    def __local_min(self, root) -> Node:
        while root._left:
            root = root._left
        return root

    def __kill_child(self, child: Node) -> None:
        if not (child._left or child._right or child._parent):
            return
        if child._left and child._right:
            raise AttributeError("Too many alive grandsons")
        alive_grandson = child._left if child._left else child._right
        if not child._parent:
            pass
        elif child._parent._left == child:
            child._parent._left = alive_grandson
        else:
            child._parent._right = alive_grandson
        if alive_grandson:
            alive_grandson._parent = child._parent
        child._parent, child._left, child._right = None, None, None

    def __balance(self, node: Node, b_rotate=True) -> Node:
        while True:
            self.__update_height(node)
            diff = self.__diff(node)
            if diff == -2:
                if b_rotate and node._left and self.__diff(node._left) == 1:
                    node = self.__br_rotation(node)
                else:
                    node = self.__sr_rotation(node)
            elif diff == 2:
                if b_rotate and node._right and self.__diff(node._right) == -1:
                    node = self.__bl_rotation(node)
                else:
                    node = self.__sl_rotation(node)
            if node._parent:
                node = node._parent
                continue
            break
        self._root = node
        return node

    def __update_height(self, *nodes: Node) -> None:
        for node in nodes:
            node._height = (
                max(
                    node._right._height if node._right else 0,
                    node._left._height if node._left else 0,
                )
                + 1
            )

    def __diff(self, node: Node) -> int:
        return (node._right._height if node._right else 0) - (
            node._left._height if node._left else 0
        )

    def __rotation(self, root, type) -> Node:
        if type == "sl":
            first, second = "_left", "_right"
        elif type == "sr":
            first, second = "_right", "_left"
        else:
            raise AttributeError("""Attribute 'type' can be "sl" or "sr" only""")
        new_root = root.__getattribute__(second)
        root._parent, new_root._parent = new_root, root._parent

        root.__setattr__(second, new_root.__getattribute__(first))
        new_root.__setattr__(first, root)

        if root.__getattribute__(second):
            root.__getattribute__(second)._parent = root
        self.__update_height(root, new_root)

        if not new_root._parent:
            self._root = new_root
        elif new_root._parent.__getattribute__(first) == root:
            new_root._parent.__setattr__(first, new_root)
        else:
            new_root._parent.__setattr__(second, new_root)

        return new_root

    def __sl_rotation(self, root: Node) -> Node:
        return self.__rotation(root, "sl")

    def __sr_rotation(self, root: Node) -> Node:
        return self.__rotation(root, "sr")

    def __bl_rotation(self, root: Node) -> Node:
        self.__sr_rotation(root._right)
        return self.__sl_rotation(root)

    def __br_rotation(self, root: Node) -> Node:
        self.__sl_rotation(root._left)
        return self.__sr_rotation(root)
