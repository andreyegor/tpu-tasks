namespace lab_1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            textBox1.AppendText("Готов к работе");
        }

        private void anyButton_Click(object sender, EventArgs e) {
            var btn = sender as Button;
            if (btn == null)
                return;
            textBox1.Clear();
            textBox1.AppendText(btn.Text);
        }
    }
}
