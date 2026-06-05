import sys
from PyQt5 import QtWidgets, uic, QtGui, QtCore
from PyQt5.QtWidgets import QFileDialog

class FilePreview(QtWidgets.QWidget):
    def __init__(self):
        super().__init__()
        uic.loadUi('file_preview.ui', self)

        # Initially hide both previews
        self.textPreview.hide()
        self.imagePreview.hide()

        # Connect button
        self.btnLoad.clicked.connect(self.load_file)

    def load_file(self):
        file_path, _ = QFileDialog.getOpenFileName(self, "Select File")
        if file_path:
            self.labelFileName.setText(file_path.split('/')[-1])
            ext = file_path.split('.')[-1].lower()

            if ext in ['txt', 'py', 'md', 'csv', 'log']:
                # Show text preview
                self.imagePreview.hide()
                self.textPreview.show()
                with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
                    content = f.read()
                    self.textPreview.setPlainText(content)

            elif ext in ['png', 'jpg', 'jpeg', 'bmp', 'gif']:
                # Show image preview
                self.textPreview.hide()
                self.imagePreview.show()
                pixmap = QtGui.QPixmap(file_path)
                self.imagePreview.setPixmap(pixmap.scaled(self.imagePreview.width(),
                                                          self.imagePreview.height(),
                                                          QtCore.Qt.KeepAspectRatio,
                                                          QtCore.Qt.SmoothTransformation))
            else:
                # Unsupported file
                self.textPreview.hide()
                self.imagePreview.hide()
                self.labelFileName.setText(f"Cannot preview file: {file_path.split('/')[-1]}")

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = FilePreview()
    window.show()
    sys.exit(app.exec_())

