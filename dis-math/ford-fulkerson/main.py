from collections import deque
from copy import deepcopy
from random import randint
from typing import List


def bfs(graph: List[List[int]], begin: int, end: int) -> List[int]|None:
    parent = [-1] * len(graph)
    visited = [False] * len(graph)
    queue = deque()

    visited[begin] = True
    queue.append(begin)
    while queue:
        now = queue.popleft()
        for next, flow in enumerate(graph[now]):
            if not visited[next] and flow > 0:
                queue.append(next)
                visited[next] = True
                parent[next] = now
                if next == end:
                    now = end
                    reversed_path = list()
                    while now != begin:
                        reversed_path.append(now)
                        now = parent[now]
                    reversed_path.append(begin)
                    return reversed_path
    return None


def ford_fulkerson(graph, source, sink):
    graph = deepcopy(graph)
    # Пока существует путь из источника в сток. (2 и 4)
    max_flow = 0
    while reversed_path := bfs(graph, source, sink):
        # Ищем ребро с минимальной пропускной способностью с_min. (3.1)
        min_edge_len = min(
            graph[reversed_path[i + 1]][reversed_path[i]]
            for i in range(len(reversed_path) - 1)
        )

        # Для каждого ребра на найденном пути увеличиваем поток на c_min
        # а в противоположном ему — уменьшаем на c_min. (3.2)
        # Модификация остаточной сети работает неявно - текущая
        # реализация bfs просто не рассматривает поток<0. (3.3)
        for i in range(len(reversed_path) - 1):
            graph[reversed_path[i]][reversed_path[i + 1]] += min_edge_len
            graph[reversed_path[i + 1]][reversed_path[i]] -= min_edge_len

        max_flow += min_edge_len

    return max_flow


graph = [
    [0, 99, 0, 99, 2],
    [99, 0, 0, 0, 14],
    [0, 99, 0, 0, 99],
    [0, 0, 4, 0, 0],
    [0, 0, 0, 0, 0],
] # -> 20

# graph = [ [randint(0, 20) if i != j and randint(0, 10) > 5 else 0 for i in range(5)] for j in range(5) ]
# [print(" ".join(map(str, row))) for row in graph]

print(ford_fulkerson(graph, 0, len(graph) - 1))
