# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem
libs = {'NumPy': ([], ['Keras'], 3, 2), 'Keras': ([], ['NumPy','Pandas'], 5, 3), 'Pandas': ([], ['Keras'], 2, 1), 'Seaborn': (['NumPy'], [], 4, 2), 'OpenCV': (['Keras','Pandas'], [], 3, 3)}
def main():
    p=Problem(); names=list(libs); p.addVariables(names, [0,1])
    for lib,(deps,conflicts,_,_) in libs.items():
        for dep in deps: p.addConstraint(lambda x,y: not x or y, [lib, dep])
        for con in conflicts: p.addConstraint(lambda x,y: not (x and y), [lib, con])
    p.addConstraint(lambda *xs: sum(libs[n][2]*v for n,v in zip(names,xs)) <= 15, names)
    p.addConstraint(lambda *xs: sum(xs) >= 2, names)
    r=max(p.getSolutions(), key=lambda x: sum(libs[n][3]*x[n] for n in names))
    print('Instalirane biblioteke:', ' '.join(n for n in names if r[n]))
if __name__ == '__main__': main()
