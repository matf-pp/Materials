import sys
from PyQt5.QtWidgets import QApplication, QWidget
from PyQt5 import uic


class TemperatureConverter(QWidget):
    def __init__(self):
        super().__init__()

        uic.loadUi("temperature_converter.ui", self)

        self.buttonConvert.clicked.connect(self.convert_temperature)

    def convert_temperature(self):
        try:
            celsius = float(self.lineEditCelsius.text())
        except ValueError:
            self.labelResult.setText("Unesi ispravan broj!")
            return

        fahrenheit = celsius * 9 / 5 + 32
        self.labelResult.setText(f"{fahrenheit:.2f} °F")


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = TemperatureConverter()
    window.show()
    sys.exit(app.exec_())

