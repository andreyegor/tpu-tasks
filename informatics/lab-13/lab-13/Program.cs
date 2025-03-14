namespace lab_13
{
    internal class Program
    {
        static void Main(string[] args)
        {
            foreach (int n in Enumerable.Range(-10, 21).Select(f)) {
                Console.Write(n);
                Console.Write(' ');
            }
        }

        private static int f(int n) {
            return n >= 0 ? n * n : n * n * n;
        }
    }
}
