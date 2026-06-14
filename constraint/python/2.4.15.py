# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem

def main():
    p=Problem(); names=['B','C','D','MG','S','ZN']; bounds=[9,19,6,4,2,8]
    for n,b in zip(names,bounds): p.addVariable(n, range(b+1))
    p.addConstraint(lambda *x: sum(x) <= 7, names)
    p.addConstraint(lambda b,c,d,mg,s,zn: 130*b+800*c+150*d+370*mg+490*s+150*zn <= 11800, names)
    p.addConstraint(lambda b,c,d,mg,s,zn: 15*b+11*c+10*d+22*mg+1*s+13*zn <= 100, names)
    p.addConstraint(lambda b,c,d,mg,s,zn: 33*b+31*c+20*d+18*mg+21*s+16*zn <= 200, names)
    r=max(p.getSolutions(), key=lambda x: 92.5*x['B']+155.5*x['C']+79.6*x['D']+156.2*x['MG']+413*x['S']+137.7*x['ZN'])
    print(f"{92.5*r['B']+155.5*r['C']+79.6*r['D']+156.2*r['MG']+413*r['S']+137.7*r['ZN']:.2f}")
if __name__ == '__main__': main()
