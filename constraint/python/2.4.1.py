# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem

def main():
    p = Problem(); p.addVariable('H', range(121)); p.addVariable('K', range(168))
    p.addConstraint(lambda h,k: 10*h + 12*k <= 1200, ['H','K'])
    p.addConstraint(lambda h,k: 300*h + 120*k <= 20000, ['H','K'])
    r = max(p.getSolutions(), key=lambda s: 7*s['H'] + 9*s['K'])
    print(f"Maksimalna zarada je {7*r['H'] + 9*r['K']} dinara, za nju je potrebno {r['H']} hlebova i {r['K']} kifli.")
if __name__ == '__main__': main()
