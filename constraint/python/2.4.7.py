# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.4.7: MANET + MATISSE + MIRO + MONET + RENOIR = ARTISTS
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'E', 'I', 'M', 'N', 'O', 'R', 'S', 'T']
    leading = ['A', 'M', 'R']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['MANET', 'MATISSE', 'MIRO', 'MONET', 'RENOIR']) == sum(value(word, assignment) for word in ['ARTISTS'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('MANET + MATISSE + MIRO + MONET + RENOIR = ARTISTS', solution)


if __name__ == "__main__":
    main()
