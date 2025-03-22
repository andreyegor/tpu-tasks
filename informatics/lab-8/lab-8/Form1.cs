using System.Reflection.Metadata.Ecma335;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.Rebar;
//оставл€ть на экране исходную матрицу при выводе
namespace lab_8
{
    public partial class Form1 : Form
    {
        int gridSize = 10;
        double[,] grid, resGrid;
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            dataGridView1.RowCount = gridSize;
            dataGridView1.ColumnCount = gridSize;
            grid = new double[gridSize, gridSize]; // »нициализируем массив
            //«аполн€ем матрицу случайными числами
            Random rand = new Random();
            for (int i = 0; i < gridSize; i++)
                for (int j = 0; j < gridSize; j++)
                    grid[i, j] = rand.Next(-10, 10);

            show(dataGridView1, grid);
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

            resGrid = new double[gridSize, gridSize];
            for (int i = 0; i < gridSize; i++)
                for (int j = 0; j < gridSize; j++)
                    resGrid[i, j] = grid[i, j];

            Func<double, double> f = s > 10 ? (b => b + 13.5) : (b => b * b - 1.5);
            for (int ij = 0; ij < gridSize; ij++)
            {
                resGrid[ij, ij] = f(grid[ij, ij]);
            }

            show(dataGridView2, resGrid);
        }

        private bool show(DataGridView dataGridView, double[,] grid) {
            dataGridView.RowCount = gridSize;
            dataGridView.ColumnCount = gridSize;
            if (grid == null)
                return false;
            for (int i = 0; i < gridSize; i++)
                for (int j = 0; j < gridSize; j++)
                    dataGridView.Rows[i].Cells[j].Value =
                    grid[i, j].ToString();

            return true;

        }
    }
}
