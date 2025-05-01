using System.Diagnostics;
using System.Numerics;
using System.Text;
using System.Text.RegularExpressions;
using System.Xml.Linq;
namespace lab_7
{
    public partial class Form1 : Form
    {
        public Form1()
        {

            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs ev)
        {
            fill(textBox1, "D");
            fill(textBox2, "E");
        }

        private void fill(TextBox textBox, string name)
        {
            Random rand = new Random();
            var m = new double[15];
            for (int i = 0; i < m.Length; i++)
            {
                m[i] = rand.Next(-100, 101);
            }
            show(textBox, name, m);
        }

        private void show(TextBox textBox, string name, double[] m)
        {
            var sb = new StringBuilder();
            for (int i = 0; i < m.Length; i++)
            {
                sb.AppendFormat("{0}[{1}] = {2}\r\n", name, i, m[i]);
            }
            textBox.Text = sb.ToString();
        }

        private int[] parse(TextBox textBox)
        {
            var regex = new Regex(@"\[(\d+)\]\s*=\s*(-?\d+)");

            string[] lines = textBox.Text.Split(new string[] { "\r\n", "\r", "\n" }, StringSplitOptions.None);
            var tokens = new List<Tuple<int, int>>();
            int mx = -1;
            for (int i = 0; i < lines.Length; i++)
            {
                var match = regex.Match(lines[i]);
                if (match.Success)
                {
                    int id = Int32.Parse(match.Groups[1].Value);
                    int val = Int32.Parse(match.Groups[2].Value);
                    mx = Math.Max(mx, id);
                    tokens.Add(new Tuple<int, int>(id, val));
                }
            }

            var m = new int[mx + 1];
            foreach ((int id, int val) in tokens)
            {
                m[id] = val;
            }
            return m;
        }

        private void button2_Click(object sender, EventArgs ev)
        {
            int[] d = [], e = [];
            d = parse(textBox1);
            e = parse(textBox2);
            if (d.Length != e.Length)
            {
                textBox3.Text = "Длина массивов должна быть одинаковой!";
                return;
            }

            var f = new double[d.Length];
            var sb = new StringBuilder();
            for (int i = 0; i < d.Length; i++)
            {
                f[i] = (2 * d[i] + Math.Sin(e[i])) / d[i];
                if (f[i]>1 && f[i]<3)
                    sb.AppendFormat("{0}[{1}] = {2}\r\n", "f", i, Math.Round(f[i], 3));
            }


            textBox3.Text = sb.ToString();
        }
    }
}
