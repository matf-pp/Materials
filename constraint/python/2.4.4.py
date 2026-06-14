# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem

def main():
    p = Problem(); p.addVariable('S', range(201)); p.addVariable('T', range(401))
    p.addConstraint(lambda s,t: 6*s + 3*t <= 1200, ['S','T'])
    p.addConstraint(lambda s,t: 75*s + 100*t <= 250000, ['S','T'])
    r = max(p.getSolutions(), key=lambda x: 2*x['S'] + 1.5*x['T'])
    print(f"Najveca moguca zarada je {2*r['S'] + 1.5*r['T']:.2f} i ona se ostvaruje kada se napravi {r['S']} solja i {r['T']} tanjira.")
if __name__ == '__main__': main()
