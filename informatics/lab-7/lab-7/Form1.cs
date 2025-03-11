using System.Diagnostics;
using System.Numerics;
using System.Text.RegularExpressions;

namespace lab_7
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        int[] d = [], e = [];
        private void button1_Click(object sender, EventArgs ev)
        {
            d = fill(textBox1);
            e = fill(textBox2);
            if (d.Length != e.Length) {
                textBox3.Text = "Массивы должны быть одного размера";
                d = [];
                e = [];
            }
        }

        private int[] fill(TextBox textBox) {
            var regex = new Regex(@"\[(\d+)\]\s*=\s*(\d+)");

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
                    tokens.Add(new Tuple<int, int>(id, val))    ;
                }
            }

            var m = new int[mx+1];
            foreach ((int id, int val) in tokens)
            {
                m[id] = val;
            }
            return m;
        }

        private void button2_Click(object sender, EventArgs ev)
        {
            Debug.Assert(d.Length == e.Length);
            var lines = new string[d.Length];
            for (int i = 0; i < d.Length; i++) {
                int di = d[i], ei = e[i];
                double val = (2 * di + Math.Sin(ei)) / di;
                lines[i] = String.Format("Mas[{0}] = {1}", i, val);
            }
            textBox3.Text = String.Join("\r\n", lines);
        }
    }
}
