# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import AllDifferentConstraint, ExactSumConstraint, Problem

def main():
    p=Problem(); xs=list(range(1,17)); p.addVariables(xs, range(1,10))
    for vars_, total in [([1,2],5),([3,4,5,6],17),([7,8],6),([9,10],4),([11,12,13,14],10),([15,16],3),([3,7],14),([1,4,8,11],11),([2,5],4),([12,15],3),([6,9,13,16],10),([10,14],3)]:
        p.addConstraint(ExactSumConstraint(total), vars_); p.addConstraint(AllDifferentConstraint(), vars_)
    print([p.getSolution()[i] for i in xs])
if __name__ == '__main__': main()
