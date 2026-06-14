# Komentar za studente: ovaj fajl sadrzi rucno pisan deo aplikacije; UI klase su u generisanim ui_*.py fajlovima, a ovde se povezuju signali i obrada dogadjaja.
import sys, os
from PyQt5 import QtWidgets
from PyQt5.QtWidgets import QFileDialog, QTreeWidgetItem
from ui_file_explorer import Ui_FileExplorer

class FileExplorer(QtWidgets.QWidget, Ui_FileExplorer):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        # Povezujemo dugme za izbor putanje
        self.btnBrowse.clicked.connect(self.browse_folder)
        self.linePath.returnPressed.connect(self.load_directory)

        # Dvoklik otvara izabrani direktorijum
        self.treeFiles.itemDoubleClicked.connect(self.on_item_double_clicked)

    def browse_folder(self):
        folder = QFileDialog.getExistingDirectory(self, "Select Folder")
        if folder:
            self.linePath.setText(folder)
            self.load_directory()

    def load_directory(self):
        path = self.linePath.text()
        if not os.path.isdir(path):
            return
        self.treeFiles.clear()

        for item in os.listdir(path):
            full_path = os.path.join(path, item)
            file_type = "Folder" if os.path.isdir(full_path) else "File"
            size = "-" if os.path.isdir(full_path) else f"{os.path.getsize(full_path)} bytes"
            tree_item = QTreeWidgetItem([item, file_type, size])
            self.treeFiles.addTopLevelItem(tree_item)

    def on_item_double_clicked(self, item, column):
        # Ako je dvoklik na direktorijum, prelazimo u njega
        path = os.path.join(self.linePath.text(), item.text(0))
        if os.path.isdir(path):
            self.linePath.setText(path)
            self.load_directory()

if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = FileExplorer()
    window.show()
    sys.exit(app.exec_())
