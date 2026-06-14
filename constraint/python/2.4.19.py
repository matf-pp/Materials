# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem

def main():
    edges=[(1,4),(1,7),(2,3),(2,4),(2,7),(3,4),(3,5),(4,5),(5,6),(6,7)]
    p=Problem(); nodes=range(1,8); p.addVariables(nodes, [0,1,2])
    for a,b in edges: p.addConstraint(lambda x,y: x != y, [a,b])
    p.addConstraint(lambda x: x == 0, [7]); p.addConstraint(lambda x: x != 1, [3]); p.addConstraint(lambda x: x != 1, [4])
    for r in p.getSolutions(): print([r[i] for i in nodes])
if __name__ == '__main__': main()
