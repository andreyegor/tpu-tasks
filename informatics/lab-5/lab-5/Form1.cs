namespace lab_5
{
    public partial class Form1 : Form
    {
        private List<Panel> panels = [];
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            int side = 100;
            int panelid = panels.Count();
            var that_panel = new Panel();
            that_panel.Size = new Size(side, side);
            that_panel.Location = new Point((side+10) * panels.Count(), 0);
            that_panel.Name = "panel" + panelid.ToString();
            that_panel.BackColor = Color.DarkCyan;
            this.Controls.Add(that_panel);
            panels.Add(that_panel);
            Console.WriteLine(panels.Count());

        }

        private void button2_Click(object sender, EventArgs e)
        {
            foreach (var panel in panels)
            {
                    var that_textBox = new TextBox();
                that_textBox.Location = new Point(0, panel.Controls.Count * 10);
                    that_textBox.Text = panel.Name;
                    panel.Controls.Add(that_textBox);
               
            }
        }
    }
}
