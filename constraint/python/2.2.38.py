# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.38: FIVE + FIVE + TEN + TEN + TEN + TEN + THIRTY = EIGHTY
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['E', 'F', 'G', 'H', 'I', 'N', 'R', 'T', 'V', 'Y']
    leading = ['E', 'F', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['FIVE', 'FIVE', 'TEN', 'TEN', 'TEN', 'TEN', 'THIRTY']) == sum(value(word, assignment) for word in ['EIGHTY'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('FIVE + FIVE + TEN + TEN + TEN + TEN + THIRTY = EIGHTY', solution)


if __name__ == "__main__":
    main()
