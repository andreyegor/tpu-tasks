using System;
using System.Collections.Generic;
using Timer = System.Windows.Forms.Timer;

namespace lab_11
{
    public partial class Form1 : Form
    {
        private Timer timer;
        private List<Star> stars;
        public Form1()
        {
            InitializeComponent();
            stars = GenerateStars(StarsToExist());


            timer = new Timer();
            timer.Interval = 35;
            timer.Tick += new EventHandler(timer_Tick);
            timer.Start();
        }

        private class Star
        {
            public int x;
            public int y;
            public int speed;
            public int size;
            public Color color;
        }

        private void Form1_Paint(object sender, PaintEventArgs e)
        {
            foreach (var star in stars)
            {
                using (Brush brush = new SolidBrush(star.color))
                {
                    e.Graphics.FillEllipse(brush, star.x, star.y, star.size, star.size);
                }
            }
        }

        private void timer_Tick(object sender, EventArgs e)
        {
            var r = new Random();
            foreach (Star star in stars)
            {
                star.y += star.speed;
            }
            stars = stars.Where(star => star.x > 0 && star.y > 0&& star.x<ClientSize.Width&&star.y<ClientSize.Height&& r.Next(1,100)>1).ToList();
            stars.AddRange(GenerateStars(Math.Max(0, StarsToExist() - stars.Count)));
            this.Refresh();
        }

        private List<Star> GenerateStars(int count)
        {
            var stars = new List<Star>();
            var r = new Random();
            for (int i = 0; i < count; i++)
            {
                stars.Add(new Star
                {
                    x = r.Next(ClientSize.Width),
                    y = r.Next(ClientSize.Height),
                    speed = r.Next(1, 5),
                    size = r.Next(2, 5),
                    color = Color.White
                });
            }
            return stars;
        }

        private int  StarsToExist() {
            return ClientSize.Width / 10;
        }
    }
}
