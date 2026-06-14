# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem

def main():
    p = Problem(); p.addVariable('S', range(41)); p.addVariable('K', range(27))
    p.addConstraint(lambda s,k: 2*s + 3*k <= 80, ['S','K'])
    p.addConstraint(lambda s,k: 120*s + 100*k <= 5000, ['S','K'])
    r = max(p.getSolutions(), key=lambda x: 1000*x['S'] + 1500*x['K'])
    print(f"Najveca moguca zarada je {1000*r['S'] + 1500*r['K']} i ona se ostvaruje kada se ocisti {r['S']} stan(ova) i {r['K']} kuca.")
if __name__ == '__main__': main()
