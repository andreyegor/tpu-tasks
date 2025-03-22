namespace lab_1
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
            starButton = new Button();
            plusButton = new Button();
            zeroButton = new Button();
            textBox1 = new TextBox();
            SuspendLayout();
            // 
            // starButton
            // 
            starButton.Location = new Point(12, 194);
            starButton.Name = "starButton";
            starButton.Size = new Size(112, 34);
            starButton.TabIndex = 0;
            starButton.Text = "*****";
            starButton.UseVisualStyleBackColor = true;
            starButton.Click += anyButton_Click;
            // 
            // plusButton
            // 
            plusButton.Location = new Point(130, 194);
            plusButton.Name = "plusButton";
            plusButton.Size = new Size(112, 34);
            plusButton.TabIndex = 1;
            plusButton.Text = "+++++";
            plusButton.UseVisualStyleBackColor = true;
            plusButton.Click += anyButton_Click;
            // 
            // zeroButton
            // 
            zeroButton.Location = new Point(248, 194);
            zeroButton.Name = "zeroButton";
            zeroButton.Size = new Size(112, 34);
            zeroButton.TabIndex = 2;
            zeroButton.Text = "00000";
            zeroButton.UseVisualStyleBackColor = true;
            zeroButton.Click += anyButton_Click;
            // 
            // textBox1
            // 
            textBox1.Location = new Point(12, 113);
            textBox1.Name = "textBox1";
            textBox1.Size = new Size(348, 31);
            textBox1.TabIndex = 3;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(10F, 25F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(384, 366);
            Controls.Add(textBox1);
            Controls.Add(zeroButton);
            Controls.Add(plusButton);
            Controls.Add(starButton);
            Name = "Form1";
            Text = "8к43 Андреев лаб 1";
            Load += Form1_Load;
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Button starButton;
        private Button plusButton;
        private Button zeroButton;
        private TextBox textBox1;
    }
}
