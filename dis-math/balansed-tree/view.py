from time import sleep
from node import Node


def show(root: Node):
    __print_tree(root)
    sleep(1)


def __print_tree(root: Node):
    """Print a binary tree to the terminal.

    Given the root node, print a binary tree using the string
    representation of the node values.

    Examples:
               _a_
              b   c

             ___a___
           _b_      c_
          d   e       f

    The trick is to process the level order traversal backwards,
    centering the parents of the child nodes. At then end, reverse
    the order of the strings.

    h   i   j   k   l   m   n   o
     _d_     _e_     _f_     _g_
       ___b___         ___c___
           _______a______

           _______a______
       ___b___         ___c___
     _d_     _e_     _f_     _g_
    h   i   j   k   l   m   n   o
    """
    levels = __level_list(root)
    lines = []
    prev_level = []
    prev_level_pos = []
    curr_level_pos = []
    for level in reversed(levels):
        line = ""
        if not prev_level:
            # Deepest level of tree
            for node in level:
                if node is not None and node.key is not None:
                    val = str(node.key)
                else:
                    val = " "
                # midpoint of string representation of val
                val_midpoint = len(val) // 2
                # If not the first element, add a separation.
                if len(line) > 0:
                    line += "   "
                # Absolute position of the node, with string centered.
                node_pos = val_midpoint + len(line)
                line += val
                # Store child's position
                curr_level_pos.append(node_pos)
        else:
            # These nodes have children.
            for i, node in enumerate(level):
                if node is not None and node.key is not None:
                    val = str(node.key)
                else:
                    val = " "
                # midpoint of string representation of val
                val_midpoint = len(val) // 2
                left_child = prev_level[i * 2]
                right_child = prev_level[i * 2 + 1]
                left_child_pos = prev_level_pos[i * 2]
                right_child_pos = prev_level_pos[i * 2 + 1]
                # Set position of parent as midpoint of the children's positions.
                node_pos = (left_child_pos + right_child_pos) // 2
                #
                while len(line) <= left_child_pos:
                    line += " "
                while len(line) < node_pos - val_midpoint:
                    if left_child is None:
                        line += " "
                    else:
                        line += "_"
                line += val
                while len(line) < right_child_pos:
                    if right_child is None:
                        line += " "
                    else:
                        line += "_"
                curr_level_pos.append(node_pos)
        lines.append(line)
        prev_level = level
        prev_level_pos = curr_level_pos
        curr_level_pos = []
    for line in reversed(lines):
        print(line)


def __level_list(root: Node):
    """Get full level order traversal of binary tree nodes.

    Example:
        >>> from leetcode_trees import binarytree
        >>> tree = binarytree.tree_from_list([5,1,4,None,None,3,6])
        >>> binarytree.print_tree(tree)
           ___5___
          1      _4_
                3   6
        >>> print(binarytree.level_representation(tree))
        [[5], [1, 4], [None, None, 3, 6]]

    The list of lists contain TreeNode objects.
    """
    curr_level = [root]
    levels = [curr_level]
    next_level = []
    while True:
        for node in curr_level:
            if node is not None:
                next_level.append(node._left)
                next_level.append(node._right)
            else:
                next_level.append(None)
                next_level.append(None)
        if not all(n is None for n in next_level):
            levels.append(next_level)
            curr_level = next_level
            next_level = []
        else:
            break
    return levels


"""
This code includes portions of the original work by Cedric Flamant,
licensed under the MIT License (see below).

MIT License

Copyright (c) 2022 Cedric Flamant

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
"""
