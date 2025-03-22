namespace lab_2
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
            xTextBox = new TextBox();
            yTextBox = new TextBox();
            zTextBox = new TextBox();
            label1 = new Label();
            label2 = new Label();
            label3 = new Label();
            label4 = new Label();
            outTextBox = new TextBox();
            button1 = new Button();
            SuspendLayout();
            // 
            // xTextBox
            // 
            xTextBox.Location = new Point(269, 64);
            xTextBox.Name = "xTextBox";
            xTextBox.Size = new Size(155, 31);
            xTextBox.TabIndex = 0;
            // 
            // yTextBox
            // 
            yTextBox.Location = new Point(268, 101);
            yTextBox.Name = "yTextBox";
            yTextBox.Size = new Size(156, 31);
            yTextBox.TabIndex = 1;
            // 
            // zTextBox
            // 
            zTextBox.Location = new Point(268, 138);
            zTextBox.Name = "zTextBox";
            zTextBox.Size = new Size(156, 31);
            zTextBox.TabIndex = 2;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(81, 70);
            label1.Name = "label1";
            label1.Size = new Size(182, 25);
            label1.TabIndex = 3;
            label1.Text = "Введите значение X: ";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(81, 107);
            label2.Name = "label2";
            label2.Size = new Size(181, 25);
            label2.TabIndex = 4;
            label2.Text = "Введите значение Y: ";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(81, 144);
            label3.Name = "label3";
            label3.Size = new Size(181, 25);
            label3.TabIndex = 5;
            label3.Text = "Введите значение Z: ";
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(81, 169);
            label4.Name = "label4";
            label4.Size = new Size(302, 25);
            label4.TabIndex = 6;
            label4.Text = "Результат выполнения программы:";
            // 
            // outTextBox
            // 
            outTextBox.Location = new Point(81, 197);
            outTextBox.Multiline = true;
            outTextBox.Name = "outTextBox";
            outTextBox.Size = new Size(343, 161);
            outTextBox.TabIndex = 7;
            // 
            // button1
            // 
            button1.Location = new Point(312, 364);
            button1.Name = "button1";
            button1.Size = new Size(112, 34);
            button1.TabIndex = 8;
            button1.Text = "Выполнить";
            button1.UseVisualStyleBackColor = true;
            button1.Click += button1_Click;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(10F, 25F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(528, 450);
            Controls.Add(button1);
            Controls.Add(outTextBox);
            Controls.Add(label4);
            Controls.Add(label3);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(zTextBox);
            Controls.Add(yTextBox);
            Controls.Add(xTextBox);
            Name = "Form1";
            Text = "8к43 Андреев лаб 2";
            Load += Form1_Load;
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private TextBox xTextBox;
        private TextBox yTextBox;
        private TextBox zTextBox;
        private Label label1;
        private Label label2;
        private Label label3;
        private Label label4;
        private TextBox outTextBox;
        private Button button1;
    }
}
