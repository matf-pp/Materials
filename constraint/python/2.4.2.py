# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem

def main():
    p = Problem(); p.addVariable('E', range(251)); p.addVariable('D', range(251))
    p.addConstraint(lambda e,d: e + d <= 250, ['E','D'])
    p.addConstraint(lambda e,d: 100*e + 105*d <= 26000, ['E','D'])
    p.addConstraint(lambda e,d: 150*e + 170*d <= 51200, ['E','D'])
    r = max(p.getSolutions(), key=lambda s: 150*5*s['E'] + 170*6*s['D'] - (100*s['E'] + 105*s['D']))
    print(r)
if __name__ == '__main__': main()
