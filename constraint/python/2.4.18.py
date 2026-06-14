# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem

def main():
    p=Problem(); names=['D17','R35','Z','N']
    for n,b in zip(names,[4,31,14,3]): p.addVariable(n, range(b+1))
    p.addConstraint(lambda d,r,z,n: 800*d+1300*r+120*z+9000*n <= 20000, names)
    p.addConstraint(lambda d,r,z,n: 480*d+3980*r+290*z+6600*n <= 20000, names)
    p.addConstraint(lambda d,r,z,n: 84*d+17.28*r+6*z+2739*n <= 3500, names)
    r=max(p.getSolutions(), key=lambda x: 9.3*x['D17']+9.9*x['R35']+2.17*x['Z']+303.5*x['N'])
    print(f"Moze se postici ubrzanje od {9.3*r['D17']+9.9*r['R35']+2.17*r['Z']+303.5*r['N']:.2f}s", r)
if __name__ == '__main__': main()
