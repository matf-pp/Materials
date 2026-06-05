import sys
from PyQt5.QtWidgets import QApplication, QMainWindow
from PyQt5 import uic
from PyQt5.QtGui import QTextCharFormat, QFont


class FormatterApp(QMainWindow):
    def __init__(self):
        super().__init__()
        uic.loadUi("main.ui", self)

        self.boldButton.clicked.connect(self.toggle_bold)
        self.italicButton.clicked.connect(self.toggle_italic)
        self.underlineButton.clicked.connect(self.toggle_underline)
        self.fontCombo.currentFontChanged.connect(self.set_font)
        self.fontSizeSpin.valueChanged.connect(self.set_font_size)

    def merge_format(self, fmt):
        cursor = self.textEdit.textCursor()
        if not cursor.hasSelection():
            cursor.select(cursor.WordUnderCursor)
        cursor.mergeCharFormat(fmt)
        self.textEdit.mergeCurrentCharFormat(fmt)

    def toggle_bold(self):
        fmt = QTextCharFormat()
        weight = QFont.Bold if not self.textEdit.fontWeight() == QFont.Bold else QFont.Normal
        fmt.setFontWeight(weight)
        self.merge_format(fmt)

    def toggle_italic(self):
        fmt = QTextCharFormat()
        fmt.setFontItalic(not self.textEdit.fontItalic())
        self.merge_format(fmt)

    def toggle_underline(self):
        fmt = QTextCharFormat()
        fmt.setFontUnderline(not self.textEdit.fontUnderline())
        self.merge_format(fmt)

    def set_font(self, font):
        fmt = QTextCharFormat()
        fmt.setFontFamily(font.family())
        self.merge_format(fmt)

    def set_font_size(self, size):
        fmt = QTextCharFormat()
        fmt.setFontPointSize(size)
        self.merge_format(fmt)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    win = FormatterApp()
    win.show()
    sys.exit(app.exec_())

