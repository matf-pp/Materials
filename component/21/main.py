import sys
from PyQt5.QtWidgets import QApplication, QMainWindow
from PyQt5 import uic

class CharCounter(QMainWindow):
    def __init__(self):
        super().__init__()
        uic.loadUi("char_counter.ui", self)

        # Povezivanje signala sa funkcijom
        self.te_input.textChanged.connect(self.update_count)

    def update_count(self):
        text = self.te_input.toPlainText()
        char_count = len(text)
        word_count = len(text.split())
        self.lbl_chars.setText(f"Karakteri: {char_count}")
        self.lbl_words.setText(f"Reči: {word_count}")

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = CharCounter()
    window.show()
    sys.exit(app.exec_())

