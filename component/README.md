# Priručnik za instalaciju Python 3 + PyQt5 (Linux)

Koristimo:
- jezik **Python** (verzija 3.7) – <https://www.python.org/>
- radni okvir **Qt** (verzije 5) - <https://www.qt.io/>  
    - radni okvir **PyQt** koji omogućava kreiranje *Qt* grafičkog korisničkog interfejsa u jeziku *Python*
    - alat **Qt Designer** za dizajn korisničkog interfejsa
    - alat `pyuic` za kompiliranje korisničkog interfejsa u *Python* kod

## 1. Instalacija Python 3 i pip-a

Instalirati `Python 3` i `pip`:
```bash
$ sudo apt install python3 python3-pip python3-venv -y
```

Provera instalacije:
```bash
$ python3 --version
$ pip3 --version
```

## 2. Kreiranje virtuelnog okruženja (preporučeno)

Virtuelno okruženje drži PyQt i druge biblioteke odvojene od sistema.

Kreiranje okruženja:
```bash
$ python3 -m venv venv
```

Aktivacija okruženja:
```bash
$ source venv/bin/activate
```

## 3. Instalacija PyQt5

Instalacija putem `pip`-a:
```bash
$ pip install PyQt5 PyQt5-tools
```

Ukoliko nije korišćeno virtuelno okruženje: 
```bash
$ pip install --break-system-packages PyQt5 PyQt5-tools
```

**Napomena:** Neke Linux distribucije pružaju sistemski paket `python-pyqt5` koji se može instalirati putem sistemskog menadžera paketa.

## 4. Instalacija Qt Designer-a

### Opcija A (preporučeno – sistemska instalacija)

Instalacija (Ubuntu, slično za ostale distribucije):
```bash
$ sudo apt install qttools5-dev-tools qttools5-dev -y
```

Pokretanje:
```bash
$ designer
```

### Opcija B (pip + venv)

Instalacija (u virtuelnom okruženju):
```bash
$ pip install pyqt5-tools
```

Pokretanje (putanja može varirati):
```bash
$ ~/.local/bin/qt5-tools designer
# ili
$ qt5-tools designer
```

## 5. Test PyQt5 instalacije

Minimalni primer, fajl `test.py`:
```python
import sys
from PyQt5.QtWidgets import QApplication, QLabel

app = QApplication(sys.argv)
label = QLabel("PyQt5 radi ispravno!")
label.show()
sys.exit(app.exec_())
```

Pokretanje:
```bash
$ python test.py
```

## 6. Korišćenje Qt Designer-a

Pokretanje:
```bash
$ designer
```

Kreiranje UI fajla:
1. Izabrati **Main Window** ili **Widget**
2. Izabrati opciju **Create**
3. Dodati dugmad, labele, input polja (drag & drop)
4. Sačuvati fajl, npr. `main.ui`

## 7. Pretvaranje `.ui` fajla u Python kod

Opcije (`man pyuic5`):
- `-o ime_fajla.py` – specificira ime generisanog fajla  
- `-p` – samo prikazuje UI, bez prevoda u Python kod  
- `-x` – dodatno generiše kod koji dozvoljava pokretanje generisanog Python fajla  

Često ćemo pokretati `pyuic5` na sledeći način:
```bash
$ pyuic5 ui_fajl.ui -x -o ui_fajl.py
```

Qt Designer fajl (`.ui`) mora se konvertovati u `.py` fajl:
```bash
$ pyuic5 main.ui -o main_ui.py
```

Ako `pyuic5` nije pronađen:
```bash
$ python -m PyQt5.uic.pyuic main.ui -o main_ui.py
```

## 8. Korišćenje generisanog UI fajla

Primer `main.py`:
```python
import sys
from PyQt5.QtWidgets import QApplication, QMainWindow
from main_ui import Ui_MainWindow

class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.ui = Ui_MainWindow()
        self.ui.setupUi(self)

app = QApplication(sys.argv)
window = MainWindow()
window.show()
sys.exit(app.exec_())
```

Pokretanje:
```bash
python main.py
```

## 9. Dodatak: Visual Studio Code integracija

<https://marketplace.visualstudio.com/items?itemName=zhoufeng.pyqt-integration>

Potrebno je podesiti lokaciju Qt Designer izvršnog fajla u podešavanjima dodatka.
Ako je izabrana podrazumevana lokacija prilikom instalacije Qt5, Qt alati se nalaze na putanji:
```sh
/usr/lib/qt5/bin
```

Dakle, putanja do Qt Designer‑a bi u tom slučaju bila:
```sh
/usr/lib/qt5/bin/designer
```
