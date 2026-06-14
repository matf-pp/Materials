# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
from constraint import Problem

def main():
    p = Problem(); p.addVariables(['K','Z'], range(19))
    p.addConstraint(lambda k,z: k + z == 18, ['K','Z'])
    p.addConstraint(lambda k,z: 2*k + 4*z == 56, ['K','Z'])
    r = p.getSolution()
    print(f"Kokoske: {r['K']}, Zecevi: {r['Z']}")
if __name__ == '__main__': main()
