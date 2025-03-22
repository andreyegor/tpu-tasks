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

        //click event для всех кнопок
        private void anyButton_Click(object sender, EventArgs e) {
            //пробуем корректно привести object к button
            //if (!(sender is Button))
            //    return;
            textBox1.Text = (sender as Button).Text;
        }
    }
}
