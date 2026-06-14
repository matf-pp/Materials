# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem, MaxSumConstraint

def main():
    p = Problem(); p.addVariable('R', range(30, 71)); p.addVariable('S', range(126))
    p.addConstraint(lambda r,s: 2*r >= 3*s, ['R','S'])
    p.addConstraint(MaxSumConstraint(30000, [360, 240]), ['R','S'])
    p.addConstraint(MaxSumConstraint(14000, [200, 60]), ['R','S'])
    r = max(p.getSolutions(), key=lambda x: 200*x['R'] + 80*x['S'])
    print(f"Maksimalna zarada je: {200*r['R'] + 80*r['S']}, broj racunara: {r['R']}, broj stampaca: {r['S']}")
if __name__ == '__main__': main()
