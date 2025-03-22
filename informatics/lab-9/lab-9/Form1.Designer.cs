namespace lab_9
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.DataVisualization.Charting.ChartArea chartArea1 = new System.Windows.Forms.DataVisualization.Charting.ChartArea();
            System.Windows.Forms.DataVisualization.Charting.Legend legend1 = new System.Windows.Forms.DataVisualization.Charting.Legend();
            System.Windows.Forms.DataVisualization.Charting.Series series1 = new System.Windows.Forms.DataVisualization.Charting.Series();
            chart1 = new System.Windows.Forms.DataVisualization.Charting.Chart();
            label1 = new Label();
            label2 = new Label();
            label3 = new Label();
            textBox1 = new TextBox();
            textBox2 = new TextBox();
            textBox3 = new TextBox();
            button1 = new Button();
            ((System.ComponentModel.ISupportInitialize)chart1).BeginInit();
            SuspendLayout();
            // 
            // chart1
            // 
            chart1.Anchor = AnchorStyles.Bottom | AnchorStyles.Left;
            chartArea1.Name = "ChartArea1";
            chart1.ChartAreas.Add(chartArea1);
            legend1.Name = "Legend1";
            chart1.Legends.Add(legend1);
            chart1.Location = new Point(0, 0);
            chart1.Name = "chart1";
            //chart1.RightToLeft = RightToLeft.Yes;
            series1.BorderWidth = 4;
            series1.ChartArea = "ChartArea1";
            series1.ChartType = System.Windows.Forms.DataVisualization.Charting.SeriesChartType.Spline;
            series1.Legend = "Legend1";
            series1.Name = "f(x)";
            chart1.Series.Add(series1);
            chart1.Size = new Size(801, 450);
            chart1.TabIndex = 0;
            chart1.Text = "График";
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(0, 456);
            label1.Name = "label1";
            label1.Size = new Size(67, 25);
            label1.TabIndex = 1;
            label1.Text = "xMin= ";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(229, 456);
            label2.Name = "label2";
            label2.Size = new Size(65, 25);
            label2.TabIndex = 2;
            label2.Text = "xMax=";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(456, 456);
            label3.Name = "label3";
            label3.Size = new Size(58, 25);
            label3.TabIndex = 3;
            label3.Text = "step=";
            // 
            // textBox1
            // 
            textBox1.Location = new Point(73, 453);
            textBox1.Name = "textBox1";
            textBox1.Size = new Size(150, 31);
            textBox1.TabIndex = 4;
            // 
            // textBox2
            // 
            textBox2.Location = new Point(300, 453);
            textBox2.Name = "textBox2";
            textBox2.Size = new Size(150, 31);
            textBox2.TabIndex = 5;
            // 
            // textBox3
            // 
            textBox3.Location = new Point(520, 453);
            textBox3.Name = "textBox3";
            textBox3.Size = new Size(150, 31);
            textBox3.TabIndex = 6;
            // 
            // button1
            // 
            button1.Location = new Point(676, 453);
            button1.Name = "button1";
            button1.Size = new Size(125, 34);
            button1.TabIndex = 7;
            button1.Text = "Посчитать";
            button1.UseVisualStyleBackColor = true;
            button1.Click += button1_Click;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(10F, 25F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(812, 541);
            Controls.Add(button1);
            Controls.Add(textBox3);
            Controls.Add(textBox2);
            Controls.Add(textBox1);
            Controls.Add(label3);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(chart1);
            Name = "Form1";
            Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)chart1).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private System.Windows.Forms.DataVisualization.Charting.Chart chart1;
        private Label label1;
        private Label label2;
        private Label label3;
        private TextBox textBox1;
        private TextBox textBox2;
        private TextBox textBox3;
        private Button button1;
    }
}
