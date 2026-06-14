# Komentar za studente: ovaj fajl sadrzi rucno pisan deo aplikacije; UI klase su u generisanim ui_*.py fajlovima, a ovde se povezuju signali i obrada dogadjaja.
import sys
from PyQt5.QtWidgets import QApplication, QWidget
from datetime import datetime
from ui_main import Ui_Form


# ----------------------------
# Osnovna klasa
# ----------------------------
class BaseWindow(QWidget, Ui_Form):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        self.click_count = 0  # promenljiva instance

        self.setWindowTitle("Base Window")
        self.button.clicked.connect(self.on_button_clicked)

    # -------- Metoda instance --------
    def on_button_clicked(self):
        """
        Metoda instance:
        - koristi self
        - menja stanje objekta
        """
        self.click_count += 1
        time_str = self.get_current_time()

        self.label.setText(
            f"Clicked {self.click_count} times\nTime: {time_str}"
        )

    # -------- Staticka metoda --------
    @staticmethod
    def format_time(dt: datetime) -> str:
        """
        Staticka metoda:
        - nema pristup self
        - sadrzi pomocnu logiku bez stanja
        """
        return dt.strftime("%H:%M:%S")

    # -------- Metoda instance koja koristi staticku metodu --------
    def get_current_time(self) -> str:
        """
        Metoda instance koja poziva staticku metodu
        """
        now = datetime.now()
        return self.format_time(now)


# ----------------------------
# Izvedena klasa kroz nasledjivanje
# ----------------------------
class MainWindow(BaseWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Main Window (Child Class)")

    # Redefinisanje metode iz osnovne klase
    def on_button_clicked(self):
        self.click_count += 1
        time_str = self.get_current_time()

        self.label.setText(
            f"Clicks: {self.click_count}\nTime: {time_str}"
        )


# ----------------------------
# Ulazna tacka aplikacije
# ----------------------------
if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())
