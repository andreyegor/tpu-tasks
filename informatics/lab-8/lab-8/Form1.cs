using System.Reflection.Metadata.Ecma335;

namespace lab_8
{
    public partial class Form1 : Form
    {
        int gridSize = 10;
        double[,] grid;
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            dataGridView1.RowCount = gridSize;
            dataGridView1.ColumnCount = gridSize;
            grid = new double[gridSize, gridSize]; // Инициализируем массив
            //Заполняем матрицу случайными числами
            Random rand = new Random();
            for (int i = 0; i < gridSize; i++)
                for (int j = 0; j < gridSize; j++)
                    grid[i, j] = rand.Next(-100, 100);

            show();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (grid == null)
                return;
            double s = 0;
            for (int ij = 0; ij < gridSize; ij++) {
                s += grid[ij,ij];
            }
            textBox1.Text = s.ToString();

            Func<double, double> f = s > 10 ? (b => b + 13.5) : (b => b * b - 1.5);
            for (int ij = 0; ij < gridSize; ij++)
            {
                grid[ij, ij] = f(grid[ij, ij]);
            }

            show();
        }

        private bool show() {
            if (grid == null)
                return false;
            for (int i = 0; i < gridSize; i++)
                for (int j = 0; j < gridSize; j++)
                    dataGridView1.Rows[i].Cells[j].Value =
                    grid[i, j].ToString();

            return true;

        }
    }
}
