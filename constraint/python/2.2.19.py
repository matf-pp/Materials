# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.19: COPY + PASTE + SAVE = TOOLS
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'C', 'E', 'L', 'O', 'P', 'S', 'T', 'V', 'Y']
    leading = ['C', 'P', 'S', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['COPY', 'PASTE', 'SAVE']) == sum(value(word, assignment) for word in ['TOOLS'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('COPY + PASTE + SAVE = TOOLS', solution)


if __name__ == "__main__":
    main()
