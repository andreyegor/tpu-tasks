namespace WinFormsApp1
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
            Point center = new Point(ClientSize.Width / 2, ClientSize.Height / 2);
            DrawCircle(g, center, 100, 0);
        }

        private void DrawCircle(Graphics g, Point parent, int radius = 5, int depth = 0)
        {
            g.DrawEllipse(Pens.Blue, parent.X - radius, parent.Y - radius, radius * 2, radius * 2);

            if (depth >= 2)
                return;

           var r = new Random();
            for (int i = 0; i < 2; i++)
            {
                int x = r.Next(ClientSize.Width);
                int y = r.Next(ClientSize.Height);
                Point child = new Point(x, y);
                g.DrawLine(Pens.Blue, parent, child);
                DrawCircle(g, child, radius / 2, depth + 1);
            }
        }
    }
}
