import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class upload extends JFrame {
    private JButton uploadButton;
    private JButton openButton;
    private JTextArea logArea;

    public upload() {
        setTitle("File Uploader");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        uploadButton = new JButton("Upload Files");
        uploadButton.addActionListener(new UploadButtonListener());
        uploadButton.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));

        openButton = new JButton("Open Files");
        openButton.addActionListener(new OpenButtonListener());
        openButton.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(uploadButton);
        buttonPanel.add(openButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private class UploadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);

            int result = fileChooser.showOpenDialog(upload.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = fileChooser.getSelectedFiles();
                for (File file : selectedFiles) {
                    try {
                        File destinationDirectory = new File("uploaded_files");
                        if (!destinationDirectory.exists()) {
                            destinationDirectory.mkdir();
                        }

                        File destinationFile = new File(destinationDirectory, file.getName());
                        Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        logArea.append("Uploaded: " + file.getName() + "\n");
                    } catch (IOException ex) {
                        logArea.append("Failed to upload: " + file.getName() + "\n");
                        ex.printStackTrace();
                    }
                }
            } else if (result == JFileChooser.CANCEL_OPTION) {
                logArea.append("File selection canceled.\n");
            }
        }
    }

    private class OpenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(new File("uploaded_files"));
            int result = fileChooser.showOpenDialog(upload.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    Desktop.getDesktop().open(selectedFile);
                    logArea.append("Opened: " + selectedFile.getName() + "\n");
                } catch (IOException ex) {
                    logArea.append("Failed to open: " + selectedFile.getName() + "\n");
                    ex.printStackTrace();
                }
            } else if (result == JFileChooser.CANCEL_OPTION) {
                logArea.append("File selection canceled.\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            upload fileUploader = new upload();
            fileUploader.setVisible(true);
        });
    }
}
