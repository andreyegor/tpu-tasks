namespace lab_4
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            textBox1.Text = "-2,05";
            textBox2.Text = "-3,05";
            textBox3.Text = "-0,2";
            textBox4.Text = "3,4";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            textBox5.Clear();
            float x0, xk, dx, b;
            if (!float.TryParse(textBox1.Text, out x0) ||
                !float.TryParse(textBox2.Text, out xk) ||
                !float.TryParse(textBox3.Text, out dx) ||
                !float.TryParse(textBox4.Text, out b))
            {
                textBox5.Text = "Ошибка ввода!";
                return;
            }

            textBox5.AppendText("Выполнил Андреев Е\r\n");
            double y;
            string ans;
            for (float x = x0; Math.Round(Math.Abs(x), 5) <= Math.Abs(xk); x = x + dx) {
                y = x * Math.Sin(Math.Sqrt(x+b-0.0084));
                ans = string.Format("x={0} y={1}\r\n",x,y);
                textBox5.AppendText(ans);
            }
        }
    }
}
