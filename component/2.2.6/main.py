import sys
from PyQt5.QtWidgets import QApplication, QMainWindow
from ui_char_counter import Ui_CharCounter

class CharCounter(QMainWindow, Ui_CharCounter):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

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
