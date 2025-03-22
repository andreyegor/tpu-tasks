namespace lab_3
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
            textBoxX = new TextBox();
            textBoxQ = new TextBox();
            textBoxAns = new TextBox();
            label1 = new Label();
            label2 = new Label();
            buttonRun = new Button();
            buttonClear = new Button();
            radioButton1 = new RadioButton();
            radioButton2 = new RadioButton();
            groupBox1 = new GroupBox();
            radioButton3 = new RadioButton();
            groupBox1.SuspendLayout();
            SuspendLayout();
            // 
            // textBoxX
            // 
            textBoxX.Location = new Point(164, 36);
            textBoxX.Name = "textBoxX";
            textBoxX.Size = new Size(150, 31);
            textBoxX.TabIndex = 0;
            // 
            // textBoxQ
            // 
            textBoxQ.Location = new Point(164, 73);
            textBoxQ.Name = "textBoxQ";
            textBoxQ.Size = new Size(150, 31);
            textBoxQ.TabIndex = 1;
            // 
            // textBoxAns
            // 
            textBoxAns.Location = new Point(56, 285);
            textBoxAns.Multiline = true;
            textBoxAns.Name = "textBoxAns";
            textBoxAns.Size = new Size(258, 221);
            textBoxAns.TabIndex = 2;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(56, 42);
            label1.Name = "label1";
            label1.Size = new Size(93, 25);
            label1.TabIndex = 3;
            label1.Text = "Введите X";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(56, 79);
            label2.Name = "label2";
            label2.Size = new Size(96, 25);
            label2.TabIndex = 4;
            label2.Text = "Введите Q";
            // 
            // buttonRun
            // 
            buttonRun.Location = new Point(56, 512);
            buttonRun.Name = "buttonRun";
            buttonRun.Size = new Size(112, 34);
            buttonRun.TabIndex = 6;
            buttonRun.Text = "Пуск";
            buttonRun.UseVisualStyleBackColor = true;
            buttonRun.Click += buttonRun_Click;
            // 
            // buttonClear
            // 
            buttonClear.Location = new Point(202, 512);
            buttonClear.Name = "buttonClear";
            buttonClear.Size = new Size(112, 34);
            buttonClear.TabIndex = 7;
            buttonClear.Text = "Отчистить";
            buttonClear.UseVisualStyleBackColor = true;
            buttonClear.Click += buttonClear_Click;
            // 
            // radioButton1
            // 
            radioButton1.AutoSize = true;
            radioButton1.Location = new Point(6, 30);
            radioButton1.Name = "radioButton1";
            radioButton1.Size = new Size(73, 29);
            radioButton1.TabIndex = 8;
            radioButton1.TabStop = true;
            radioButton1.Text = "sh(x)";
            radioButton1.UseVisualStyleBackColor = true;
            // 
            // radioButton2
            // 
            radioButton2.AutoSize = true;
            radioButton2.Location = new Point(6, 65);
            radioButton2.Name = "radioButton2";
            radioButton2.Size = new Size(67, 29);
            radioButton2.TabIndex = 9;
            radioButton2.TabStop = true;
            radioButton2.Text = "x^2";
            radioButton2.UseVisualStyleBackColor = true;
            // 
            // groupBox1
            // 
            groupBox1.Controls.Add(radioButton3);
            groupBox1.Controls.Add(radioButton1);
            groupBox1.Controls.Add(radioButton2);
            groupBox1.Location = new Point(56, 110);
            groupBox1.Name = "groupBox1";
            groupBox1.Size = new Size(258, 141);
            groupBox1.TabIndex = 10;
            groupBox1.TabStop = false;
            groupBox1.Text = "Выберите f(x)";
            // 
            // radioButton3
            // 
            radioButton3.AutoSize = true;
            radioButton3.Location = new Point(6, 100);
            radioButton3.Name = "radioButton3";
            radioButton3.Size = new Size(66, 29);
            radioButton3.TabIndex = 10;
            radioButton3.TabStop = true;
            radioButton3.Text = "e^x";
            radioButton3.UseVisualStyleBackColor = true;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(10F, 25F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(368, 659);
            Controls.Add(groupBox1);
            Controls.Add(buttonClear);
            Controls.Add(buttonRun);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(textBoxAns);
            Controls.Add(textBoxQ);
            Controls.Add(textBoxX);
            Name = "Form1";
            Text = "8к43 Андреев лаб 3";
            Load += Form1_Load;
            groupBox1.ResumeLayout(false);
            groupBox1.PerformLayout();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private TextBox textBoxX;
        private TextBox textBoxQ;
        private TextBox textBoxAns;
        private Label label1;
        private Label label2;
        private Button buttonRun;
        private Button buttonClear;
        private RadioButton radioButton1;
        private RadioButton radioButton2;
        private GroupBox groupBox1;
        private RadioButton radioButton3;
    }
}
