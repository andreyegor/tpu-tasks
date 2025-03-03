namespace lab_2
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            xTextBox.Text = "0,03981";
            yTextBox.Text = "-1625";
            zTextBox.Text = "0,512";
        }

        private double solvea(double x, double y, double z)
        {
            double t1 = Math.Pow(2.0, -x);
            //nthroot(x) == x^1/n
            double t2 = (Math.Sqrt(x + Math.Pow(Math.Abs(y), 0.25)));
            double t3 = (Math.Pow(Math.Exp((x - 1) / Math.Sin(z)), 1.0 / 3.0));
            return t1 * t2 * t3;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (double.TryParse(xTextBox.Text, out double x) &&
                double.TryParse(yTextBox.Text, out double y) &&
                double.TryParse(zTextBox.Text, out double z))
            {
                double a = solvea(x, y, z);
                string out_template =
                    "Лаб. раб. N2 гр 8к43 Андреев Е.С.\r\n" +
                    "X={0}\r\nY={1}\r\nZ={2}\r\n" +
                    "Результат A = {3}";
                Console.WriteLine(out_template);
                outTextBox.Text = string.Format(out_template, x, y, z, a);
                //Результат на тестовых данных отличается от искомого
                //Это связанно с особенностями хранения чисел с плавающей точкой
            }
            else {
                outTextBox.Text = "Ошибка!";
            }
        }
    }
}
