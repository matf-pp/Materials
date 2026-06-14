# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.18: SHE + KNOWS + HOW + IT = WORKS
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['E', 'H', 'I', 'K', 'N', 'O', 'R', 'S', 'T', 'W']
    leading = ['H', 'I', 'K', 'S', 'W']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['SHE', 'KNOWS', 'HOW', 'IT']) == sum(value(word, assignment) for word in ['WORKS'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('SHE + KNOWS + HOW + IT = WORKS', solution)


if __name__ == "__main__":
    main()
