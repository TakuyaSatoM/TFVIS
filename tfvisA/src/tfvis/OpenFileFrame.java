package tfvis;

import java.awt.Container;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class OpenFileFrame extends JFrame{
	private static final long serialVersionUID = 7162638898840178320L;	//	classÇÃå›ä∑ê´Ç…ä÷Ç∑ÇÈÇ‡ÇÃÅBEclipseä¬ã´Ç»ÇÁèüéËÇ…Ç¬ÇØÇƒÇ≠ÇÍÇÈÅB

	private String fileName;
	
	public OpenFileFrame() {
		JFileChooser fDialog = new JFileChooser();
		fDialog.setDialogType(JFileChooser.OPEN_DIALOG);
		fDialog.setDialogTitle("Open Java File");
		fDialog.addChoosableFileFilter(new Filter());
		fDialog.setCurrentDirectory(new File("."));
		fDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		Container cont = getContentPane();
		if(fDialog.showOpenDialog(cont) != JFileChooser.APPROVE_OPTION)
			return;

		fileName = fDialog.getSelectedFile().getAbsolutePath();
	}
	
	
	public String getFileName() {
		return fileName;
	}
	
	
	class Filter extends javax.swing.filechooser.FileFilter {
		public boolean accept(File f) {
			String name = f.getName();
			return (name.endsWith(".java") || name.endsWith(".JAVA") || f.isDirectory());
		}
		
		public String getDescription() {
			return "*.java, *.JAVA";
		}
	}
}
