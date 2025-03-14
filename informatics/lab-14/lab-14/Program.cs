namespace lab_14
{
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
            var ms = InsertionSort(m);
            WriteArray(ms);
            Console.WriteLine("Глубина рекурсии в бинарного(методом дитхомии) поиска: {0}", BinSearch(ms, m[0]));

        }

        private static int[] InsertionSort(int[] m)
        {
            var mc = new int[m.Length];
            m.CopyTo(mc, 0);

            for (int i = 1; i < mc.Length; i++)
            {
                int key = mc[i];
                int j;

                for (j = i - 1; j >= 0 && mc[j] > mc[i]; j--)
                    mc[j + 1] = mc[j];


                mc[j + 1] = key;
            }
            return mc;
        }


        // Пишем очень императивно, не вижу причин не раскрывать рекурсию
        // Индекс элемента не нужен, по этому возвращаю число итераций поиска (глубину рекурсии)
        private static int BinSearch(int[] m, int target)
        {
            int l = 0, r = m.Length - 1;
            int i = 0;

            while (l <= r)
            {
                i++;
                int mid = l + (r - l) / 2;
                if (m[mid] == target)
                {
                    return i;
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

            return i; // Target not found
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
