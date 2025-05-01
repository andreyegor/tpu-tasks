namespace lab_10
//прям рисунок

{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }


        private void Form1_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = e.Graphics;

            SolidBrush blueBrush = new SolidBrush(Color.LightBlue);
            SolidBrush redBrush = new SolidBrush(Color.LightCoral);
            SolidBrush yellowBrush = new SolidBrush(Color.Yellow);
            SolidBrush blackBrush = new SolidBrush(Color.Black);

            g.FillRectangle(redBrush, 50, 100, 150, 50);
            g.FillRectangle(redBrush, 70, 80, 130, 40);

            g.FillRectangle(blueBrush, 80, 90, 40, 20);
            g.FillRectangle(blueBrush, 140, 90, 40, 20);

            g.FillEllipse(blackBrush, 60, 140, 30, 30);
            g.FillEllipse(blackBrush, 160, 140, 30, 30);

            g.FillEllipse(yellowBrush, 250, 0, 30, 30);
        }
    }
}
