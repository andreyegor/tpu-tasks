namespace lab_9
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            textBox1.Text = "-3,05";
            textBox2.Text = "-2,05";
            textBox3.Text = "0,1";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Func<double, double> f = x => x * Math.Sin(Math.Sqrt(x + 3.4 - 0.0084));
            if (!double.TryParse(textBox1.Text, out double xMin) ||
                !double.TryParse(textBox2.Text, out double xMax) ||
                !double.TryParse(textBox3.Text, out double step))
                return;
            if (xMin >= xMax)
                return;

            int cnt = (int)Math.Ceiling((xMax - xMin) / step) + 1;
            double[] x = new double[cnt];
            double[] y = new double[cnt];
            for (int i = 0; i < cnt; i++)
            {
                x[i] = Math.Round(xMin + step * i, 3);
                y[i] = Math.Round(f(x[i]), 3);
            }

            chart1.ChartAreas[0].AxisX.Minimum = xMin;
            chart1.ChartAreas[0].AxisX.Maximum = xMax;
            chart1.ChartAreas[0].AxisX.MajorGrid.Interval = step;

            chart1.Series[0].Points.DataBindXY(x, y);
        }
    }
}
