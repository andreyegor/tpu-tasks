from tree import Tree
from random import randint
from view import show

if __name__ == "__main__":
    tree = Tree()
    for i in range(25):
        if len(tree) and randint(1, 100) > 75:
            d = tree[randint(0, len(tree) - 1)]
            print(f"!discard {d}")
            tree.discard(d)
        else:
            a = randint(0, 100)
            print(f"!    add {a}")
            tree.add(a)
show(tree._root)
