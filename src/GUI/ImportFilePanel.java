package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

class ImportFilePanel extends JPanel {

    private static String fileName;

    ImportFilePanel() throws IOException {
        JFileChooser chooseFile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Excel Files", "xlsx");
        chooseFile.setFileFilter(filter);
        int result = chooseFile.showOpenDialog(this);
        File selectedFile;
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooseFile.getSelectedFile();
            fileName = selectedFile.getName();
            System.out.println("Selected file: " + selectedFile.getName() + "\nPath: " + selectedFile.getAbsolutePath());

            java.nio.file.Files.copy(
                    selectedFile.toPath(),
                    new java.io.File(selectedFile.getName()).toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                    java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                    java.nio.file.LinkOption.NOFOLLOW_LINKS );
        }else if(result == JFileChooser.CANCEL_OPTION){
            JOptionPane.showMessageDialog(null, "you closed with out selecting file, please enter 'RESTART' into the text box");
        }
        add(chooseFile);
        setVisible(true);
    }

    String getFileName(){
        return fileName;
    }
}
