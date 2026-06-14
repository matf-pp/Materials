# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem

def main():
    p = Problem(); names = ['B','P','J','M','V','N']
    bounds = [10,20,7,5,3,9]
    for n,b in zip(names,bounds): p.addVariable(n, range(b+1))
    p.addConstraint(lambda b,p,j,m,v,n: b+p+j+m+v+n <= 10, names)
    p.addConstraint(lambda b,p,j,m,v,n: 30*b+300*p+50*j+170*m+400*v+450*n <= 1170, names)
    p.addConstraint(lambda b,p,j,m,v,n: 30*b+10*p+150*j+32*m+3*v+15*n < 500, names)
    p.addConstraint(lambda b,p,j,m,v,n: 5*b+30*p+2*j+15*m+45*v+68*n <= 150, names)
    r=max(p.getSolutions(), key=lambda x: 20*x['B']+15*x['P']+70*x['J']+40*x['M']+23*x['V']+7*x['N'])
    print(20*r['B']+15*r['P']+70*r['J']+40*r['M']+23*r['V']+7*r['N'])
if __name__ == '__main__': main()
