from tree import Tree
from random import randint
from view import show

if __name__ == "__main__":
    tree = Tree()
    reference = set()
    for i in range(25):
        if len(tree) and randint(1, 100) > 75:
            d = tree[randint(0, len(tree) - 1)]
            print(f"!discard {d}")
            tree.discard(d)
            assert d not in tree
            reference.discard(d)
        else:
            a = randint(0, 100)
            print(f"!    add {a}")
            tree.add(a)
            reference.add(a)
    for e in reference:
        assert e in tree
    for e in tree:
        assert e in reference
show(tree._root)
