using System.Text;

namespace lab_6
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            listBox1.Items.AddRange("012345678","012 345 678", "012     345");
        }

        private void button1_Click(object sender, EventArgs e)
        {
            int i, j;
            int selected = listBox1.SelectedIndex;
            if (!int.TryParse(textBox1.Text, out i) ||
                !int.TryParse(textBox2.Text, out j) ||
                selected == -1 || i==j
                )
                return;

            String line = listBox1.Items[selected]?.ToString() ?? "";

            int real_i = -1, real_j = -1;
            int k = 0;
            for (int real_k=0; real_k<line.Length; real_k++)
            {
                if (line[real_k] == ' ')
                    continue;
                else if (k == i)
                    real_i = real_k;
                else if (k == j)
                    real_j = real_k;
                k++;
            }
            if (real_i == -1 || real_j == -1)
                return;
            var res = new StringBuilder(line);
            res[real_i] = line[real_j];
            res[real_j] = line[real_i];
            listBox1.Items[selected] = res.ToString();
        }
    }
}
