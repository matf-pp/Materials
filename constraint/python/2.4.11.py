# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import AllDifferentConstraint, Problem

def main():
    p = Problem(); p.addVariables('ABCDEFG', range(1,8)); p.addConstraint(AllDifferentConstraint(), 'ABCDEFG')
    for line in ['ABED','BEGD','EGFD','GFCD','FCAD','CABD']:
        p.addConstraint(lambda *x: sum(x) <= 15, line)
    r = max(p.getSolutions(), key=lambda x: (x['C']+x['D']+x['E'], -(x['C']*x['D']*x['E'])))
    print(r)
if __name__ == '__main__': main()
