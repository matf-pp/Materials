# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem

def main():
    edges={(1,2):3,(1,3):2,(1,4):3,(2,7):5,(3,2):1,(3,6):1,(4,3):2,(4,5):2,(5,8):5,(6,2):4,(6,5):2,(6,8):1,(7,6):2,(7,8):3}
    p=Problem(); names=list(edges)
    for e,c in edges.items(): p.addVariable(e, range(c+1))
    for node in range(2,8):
        incoming=[e for e in names if e[1]==node]; outgoing=[e for e in names if e[0]==node]
        p.addConstraint(lambda *vals, incoming=incoming, outgoing=outgoing: sum(vals[:len(incoming)]) == sum(vals[len(incoming):]), incoming+outgoing)
    r=max(p.getSolutions(), key=lambda x: sum(x[e] for e in names if e[0]==1))
    print(sum(r[e] for e in names if e[0]==1))
if __name__ == '__main__': main()
