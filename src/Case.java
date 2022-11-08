import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Case extends JPanel{
    //Member Variables...
    private String m_content;                   //Content of the case.
    private int m_xCoordinate;

    public int getXCoordinate() {
        return m_xCoordinate;
    }

    public void setXCoordinate(int m_xCoordinate) {
        this.m_xCoordinate = m_xCoordinate;
    }

    public int getYCoordinate() {
        return m_yCoordinate;
    }

    public void setYCoordinate(int m_yCoordinate) {
        this.m_yCoordinate = m_yCoordinate;
    }

    private int m_yCoordinate;   //
    private JLabel m_label;
    public static int m_count = 0;              //Counter that keeps track of the number of case generated.

    public String getStringContent() {
        return this.m_content;
    }

    public char getCharContent() {
        return this.m_content.charAt(0);
    }

    public void setContent(String m_content) {
        this.m_content = m_content;
        this.m_label.setText(m_content);
    }

    public Case(int p_xCaseCoordinate, int p_yCaseCoordinate){
       this.m_content = " ";
       this.m_xCoordinate = p_xCaseCoordinate;
       this.m_yCoordinate = p_yCaseCoordinate;
       this.m_label = new JLabel(m_content);
       this.setBorder(BorderFactory.createMatteBorder(
               1, 1, 1, 1, Color.gray));
        this.m_label.setFont(new Font(Font.DIALOG, Font.PLAIN, 125));
        this.addComponentListener(new CaseResized());
        this.setLayout(new GridBagLayout());
        this.add(m_label);
    }

    @Override
    public Dimension getMaximumSize()
    {
        return super.getSize();
    }

   private void setLabelFontSize(){

       Font labelFont = this.m_label.getFont();
       String labelText = this.m_label.getText();

       int stringWidth = this.m_label.getFontMetrics(labelFont).stringWidth(labelText);
       int componentWidth = this.getWidth();

        // Find out how much the font can grow in width.
       double widthRatio = (double)componentWidth / (double)stringWidth;

       int newFontSize = (int)(labelFont.getSize() * widthRatio);
       int componentHeight = this.getHeight() - 30;

        // Pick a new font size, so it will not be larger than the height of label.
       int fontSizeToUse = Math.min(newFontSize, Math.max(componentHeight,12));

        // Set the label's font size to the newly determined size.
       this.m_label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
   }

    public boolean isEmpty() {
        return this.m_content == " ";
    }

    private static class CaseResized extends ComponentAdapter{
       @Override
       public void componentResized(ComponentEvent e) {
           super.componentResized(e);
           if (e.getSource() instanceof Case){
               Case resizedCase = (Case) e.getSource();
               resizedCase.setPreferredSize(resizedCase.getMaximumSize());
               resizedCase.setLabelFontSize();
           }
       }
   }
}
