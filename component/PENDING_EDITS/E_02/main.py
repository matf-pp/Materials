import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QMessageBox, QInputDialog
from PyQt5 import uic


class NotesApp(QMainWindow):
    def __init__(self):
        super().__init__()
        uic.loadUi("main.ui", self)

        self.notes = {}

        self.newButton.clicked.connect(self.new_note)
        self.saveButton.clicked.connect(self.save_note)
        self.deleteButton.clicked.connect(self.delete_note)
        self.notesList.currentTextChanged.connect(self.load_note)

    def new_note(self):
        title, ok = QInputDialog.getText(self, "New Note", "Note title:")
        if ok and title:
            if title in self.notes:
                QMessageBox.warning(self, "Error", "Note already exists.")
                return
            self.notes[title] = ""
            self.notesList.addItem(title)
            self.notesList.setCurrentRow(self.notesList.count() - 1)
            self.editor.clear()

    def load_note(self, title):
        self.editor.setText(self.notes.get(title, ""))

    def save_note(self):
        item = self.notesList.currentItem()
        if item:
            self.notes[item.text()] = self.editor.toPlainText()

    def delete_note(self):
        item = self.notesList.currentItem()
        if not item:
            return
        title = item.text()
        del self.notes[title]
        self.notesList.takeItem(self.notesList.row(item))
        self.editor.clear()


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = NotesApp()
    window.show()
    sys.exit(app.exec_())

