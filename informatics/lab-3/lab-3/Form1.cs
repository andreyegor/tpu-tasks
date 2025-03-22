using System.Xml;

namespace lab_3
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void buttonRun_Click(object sender, EventArgs e)
        {
            if (double.TryParse(textBoxX.Text, out double x) &&
            double.TryParse(textBoxQ.Text, out double q))
            {
                double fx = -1;
                string fx_text = "";
                if (radioButton1.Checked)
                {
                    fx = Math.Sinh(x);
                    fx_text = "sh(x)";
                }
                else if (radioButton2.Checked)
                {
                    fx = x * x;
                    fx_text = "x^2";
                }
                else if (radioButton3.Checked)
                {
                    fx = Math.Exp(x);
                    fx_text = "e^x";
                }
                else
                {
                    textBoxAns.Text = "Выберите f(x)";
                    return;
                }

                double xq = Math.Abs(x * q);
                double ans = -1;
                if (xq > 10)
                {
                    ans = Math.Log(Math.Abs(fx) * Math.Abs(q));
                }
                else if (xq < 10)
                {
                    ans = Math.Exp(fx + q);
                }
                else
                {
                    ans = fx + q;
                }

                string res_template =
                    "Результат работы программы Андреева \r\n" +
                    "При x = {0}\r\nПри q={1}\r\nПри f(x) = {2}\r\n" +
                    "k = {3}";
                textBoxAns.Text = string.Format(res_template, x, q, fx_text, ans);
            }
            else 
            {
                textBoxAns.Text = "Ошибка";
            }
        }

        private void buttonClear_Click(object sender, EventArgs e)
        {
            textBoxAns.Clear();
        }
    }
}
