# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.27: ELEVEN + NINE + FIVE + FIVE = THIRTY
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['E', 'F', 'H', 'I', 'L', 'N', 'R', 'T', 'V', 'Y']
    leading = ['E', 'F', 'N', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['ELEVEN', 'NINE', 'FIVE', 'FIVE']) == sum(value(word, assignment) for word in ['THIRTY'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('ELEVEN + NINE + FIVE + FIVE = THIRTY', solution)


if __name__ == "__main__":
    main()
