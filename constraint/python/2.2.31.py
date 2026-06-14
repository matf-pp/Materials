# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.31: FOURTEEN + TEN + TEN + SEVEN = FORTYONE
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['E', 'F', 'N', 'O', 'R', 'S', 'T', 'U', 'V', 'Y']
    leading = ['F', 'S', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['FOURTEEN', 'TEN', 'TEN', 'SEVEN']) == sum(value(word, assignment) for word in ['FORTYONE'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('FOURTEEN + TEN + TEN + SEVEN = FORTYONE', solution)


if __name__ == "__main__":
    main()
