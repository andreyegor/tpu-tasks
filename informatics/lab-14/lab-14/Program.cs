namespace lab_14
{
    //Не создавать новый массив для сортировки;
    //Ввод числа для поиска
    //реализовать простой поиск
    internal class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Мой вариант: {0}", 10 % 3 + 1);//+1 тк индексация c 1

            int ln = 100;

            var r = new Random();
            var m = new int[ln];
            for (int i = 0; i < ln; i++)
                m[i] = r.Next(-100, 100);

            WriteArray(m);
            InsertionSort(m);
            WriteArray(m);
            Console.Write("Введите число для поиска: ");
            if (!int.TryParse(Console.ReadLine(), out int target)) {
                Console.WriteLine("Это не число.");
                return;
            }
            var (s, sd) = Search(m, target);
            var (bs, bsd) = BinSearch(m, target);
            Func<int, string> foundNotFound = i => i == -1 ? "элемент не найден" : String.Format("элемент найден на {0} месте", i+1);
            Console.WriteLine("В результате обычного поиска по отсортированному массиву {0}, а в результате бинарного (методом дитхомии) {1}.", foundNotFound(s), foundNotFound(bs));
            Console.WriteLine("Элмент был (не)найден обычным поиском за {0} итераций, а бинарным (методом дитхомии) за {1} итераций.", sd, bsd);

        }

        private static void InsertionSort(int[] m)
        {
            for (int i = 1; i < m.Length; i++)
            {
                int key = m[i];
                int j;

                for (j = i - 1; j >= 0 && m[j] > key; j--)
                    m[j + 1] = m[j];


                m[j + 1] = key;
            }
        }


        // Пишем очень императивно, не вижу причин не раскрывать рекурсию
        // Индекс элемента не нужен, по этому возвращаю число итераций поиска (глубину рекурсии)
        private static (int, int) BinSearch(int[] m, int target)
        {
            int l = 0, r = m.Length - 1;
            int depth = 1;

            while (l <= r)
            {
                depth++;
                int mid = (r + l) / 2;
                if (m[mid] == target)
                {
                    return (mid, depth);
                }
                else if (m[mid] < target)
                {
                    l = mid + 1;
                }
                else
                {
                    r = mid - 1;
                }
            }

            return (-1, depth);
        }

        private static (int, int) Search(int[] m, int target)
        {
            for (int i = 0; i < m.Length; i++)
                if (m[i] == target)
                    return (i, i+1);
            return (-1, m.Length); // Target not found
        }

        private static void WriteArray(int[] m)
        {
            Console.Write("[ ");
            foreach (int e in m)
            {
                Console.Write(e.ToString() + " ");
            }
            Console.Write("]\n");

        }
    }
}
