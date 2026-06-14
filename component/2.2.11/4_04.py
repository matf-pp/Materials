# Komentar za studente: ovaj fajl sadrzi rucno pisan deo aplikacije; UI klase su u generisanim ui_*.py fajlovima, a ovde se povezuju signali i obrada dogadjaja.
from PyQt5 import QtCore, QtGui, QtWidgets
import ui_4_04
import sys, os, random, string, time

__author__ = 'Milan Cugurovic & Ivan Ristovic, 2020.'

class HackerApp(QtWidgets.QMainWindow, ui_4_04.Ui_MainWindow):
    def __init__(self):
        super(HackerApp, self).__init__()
        self.setupUi(self)

        # Dugme za pokretanje
        self.start.clicked.connect(self.pressed)

    def pressed(self):
        print("engine started...")
        for i in range(0, 100):
            print("...{}...".format(''.join(random.choices(string.ascii_uppercase + string.digits, k=random.randint(20, 40)))))
            self.progressBar.setProperty("value", i)
            if i%2==0:
                self.label_msg.setText(''.join(random.choices(string.ascii_uppercase + string.digits, k=random.randint(20, 40))))
            time.sleep(1)

if __name__=="__main__":
    app = QtWidgets.QApplication(sys.argv)
    hacker = HackerApp()
    hacker.show()
    sys.exit(app.exec_())
