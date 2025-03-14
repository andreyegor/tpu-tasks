using System.Drawing;
using System.Windows.Forms;

namespace lab_12
{
    public partial class Form1 : Form
    {

        private Point PreviousPoint, point;
        private Bitmap bmp;
        private Pen blackPen;
        private Graphics g;
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            blackPen = new Pen(Color.Black, 4);
        }


        private void button1_Click(object sender, EventArgs e)
        {
            OpenFileDialog dialog = new OpenFileDialog();
            dialog.Filter = "Image files (*.BMP, *.JPG,*.GIF, *.PNG)| *.bmp; *.jpg; *.gif; *.png";
            if (dialog.ShowDialog() == DialogResult.OK)
            {
                // ��������� ����������� �� ���������� �����
                Image image = Image.FromFile(dialog.FileName);
                int width = image.Width;
                int height = image.Height;
                pictureBox1.Width = width;
                pictureBox1.Height = height;
                // ������� � ��������� ����������� � ������� bmp
                bmp = new Bitmap(image, width, height);
                // ���������� ����������� � pictureBox1
                pictureBox1.Image = bmp;
                // �������������� ������ Graphics ��� ���������
                g = Graphics.FromImage(pictureBox1.Image);
            }
        }
        // �������� ��� ������� ����� � pictureBox1
        private void pictureBox1_MouseDown(object sender,
        MouseEventArgs e)
        {
            // ���������� � ���������� ����� ������� ����������
            PreviousPoint.X = e.X;
            PreviousPoint.Y = e.Y;
        }
        // �������� ��� ����������� �����
        private void pictureBox1_MouseMove(object sender,
        MouseEventArgs e)
        {
            // ��������� ������ �� ����� ������ ����
            if (e.Button == MouseButtons.Left)
            {
                // ���������� ������� ��������� ������� ����
                point.X = e.X;
                point.Y = e.Y;
                // ��������� ������ ���������� ����� � �������
                g.DrawLine(blackPen, PreviousPoint, point);
                // ������� ��������� ������� - � PreviousPoint
                PreviousPoint.X = point.X;
                PreviousPoint.Y = point.Y;
                // ������������� �������� �����������
                pictureBox1.Invalidate();
            }
        }
        // �������� ��� ������� ������ ���������� �����
        private void button2_Click(object sender, EventArgs e)
        {
            // ��������� � ��������� ������ savedialog
            SaveFileDialog savedialog = new SaveFileDialog();
            // ������ �������� ��� savedialog
            savedialog.Title = "��������� �������� ���...";
            savedialog.OverwritePrompt = true;
            savedialog.CheckPathExists = true;
            savedialog.Filter =
            "Bitmap File(*.bmp)|*.bmp|" +
            "GIF File(*.gif)|*.gif|" +
            "JPEG File(*.jpg)|*.jpg|" +
            "PNG File(*.png)|*.png";
            // ���������� ������ � ��������� ������ �� ��� �����
            if (savedialog.ShowDialog() == DialogResult.OK)
            {
                string fileName = savedialog.FileName;
                // ������� �� ����� ���������� �����
                string strFilExtn = fileName.Remove(0,
                fileName.Length - 3);
                // ��������� ���� � ������ �������
                switch (strFilExtn)
                {
                    case "bmp":
                        bmp.Save(fileName,
                        System.Drawing.Imaging.ImageFormat.Bmp);
                        break;
                    case "jpg":
                        bmp.Save(fileName,
                         System.Drawing.Imaging.ImageFormat.Jpeg);
                        break;
                    case "gif":
                        bmp.Save(fileName,
                        System.Drawing.Imaging.ImageFormat.Gif);
                        break;
                    case "tif":
                        bmp.Save(fileName,
                         System.Drawing.Imaging.ImageFormat.Tiff);
                        break;
                    case "png":
                        bmp.Save(fileName,
                        System.Drawing.Imaging.ImageFormat.Png);
                        break;
                    default:
                        break;
                }
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (bmp == null) return;

            int w = bmp.Width / 2;
            int h = bmp.Height / 2;

            Bitmap result = new Bitmap(bmp.Width, bmp.Height);

            for (int y = 0; y < h; y++)
            {
                for (int x = 0; x < w; x++)
                {
                    Color pixelRed = bmp.GetPixel(x, y);
                    result.SetPixel(x, y, Color.FromArgb(pixelRed.R, 0, 0));

                    Color pixelGreen = bmp.GetPixel(x + w, y);
                    result.SetPixel(x + w, y, Color.FromArgb(0, pixelGreen.G, 0));

                    Color pixelBlue = bmp.GetPixel(x, y + h);
                    result.SetPixel(x, y + h, Color.FromArgb(0, 0, pixelBlue.B));

                    Color pixelGray = bmp.GetPixel(x + w, y + h);
                    int gray = (int)(pixelGray.R * 0.34 + pixelGray.G * 0.33 + pixelGray.B * 0.33);
                    result.SetPixel(x + w, y + h, Color.FromArgb(gray, gray, gray));
                }
            }
            using (Graphics g = Graphics.FromImage(bmp))
            {
                g.DrawImage(result, 0, 0);
            }

            pictureBox1.Invalidate();
        }
    }
}
