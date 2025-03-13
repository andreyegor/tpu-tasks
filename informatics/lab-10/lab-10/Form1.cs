namespace lab_10
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
            Random rand = new Random();

            Pen solidPen = new Pen(Color.Blue, 2);
            Pen dashPen = new Pen(Color.Red, 2);
            dashPen.DashStyle = System.Drawing.Drawing2D.DashStyle.Dash;
            Pen dotDashPen = new Pen(Color.Green, 2);
            dotDashPen.DashStyle = System.Drawing.Drawing2D.DashStyle.DashDot;

            SolidBrush blueBrush = new SolidBrush(Color.LightBlue);
            SolidBrush redBrush = new SolidBrush(Color.LightCoral);

            // Прямоугольники
            g.DrawRectangle(solidPen, 
                rand.Next(0, this.Width - 200), 
                rand.Next(0, this.Height - 200), 
                rand.Next(100, 200), 
                rand.Next(100, 200));

            g.FillRectangle(blueBrush, 
                rand.Next(0, this.Width - 200), 
                rand.Next(0, this.Height - 200),
                rand.Next(100, 200), 
                rand.Next(100, 200));

            //Эллипсы
            g.DrawEllipse(solidPen, 
                rand.Next(0, this.Width - 200), 
                rand.Next(0, this.Height - 200),
                rand.Next(100, 200), 
                rand.Next(100, 200));
            g.FillEllipse(redBrush,
                rand.Next(0, this.Width - 100), 
                rand.Next(0, this.Height - 50),
                rand.Next(100, 200),
                rand.Next(100, 200));
            //Правильный пятиугольник
            int ln = rand.Next(100, 201);

            PointF[] points = new PointF[5];
            float a = 72f; // 360 / 5
            float r = ln / (2 * (float)Math.Sin(Math.PI / 5));
            float centerX = rand.Next((int)r, this.Width - (int)r);
            float centerY = rand.Next((int)r, this.Height - (int)r);

            for (int i = 0; i < 5; i++)
            {
                points[i] = new PointF(
                    centerX + r * (float)Math.Cos(i * a * Math.PI / 180f),
                    centerY + r * (float)Math.Sin(i * a * Math.PI / 180f)
                );
            }

            g.FillPolygon(blueBrush, points);
            solidPen.Dispose();
            dashPen.Dispose();
            dotDashPen.Dispose();
            blueBrush.Dispose();
            redBrush.Dispose();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Refresh();
        }
    }
}
