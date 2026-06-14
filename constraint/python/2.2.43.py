# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.43: FERMAT * S = LAST + THEOREM
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'E', 'F', 'H', 'L', 'M', 'O', 'R', 'S', 'T']
    leading = ['F', 'L', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return value('FERMAT', assignment) * value('S', assignment) == value('LAST', assignment) + value('THEOREM', assignment)

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('FERMAT * S = LAST + THEOREM', solution)


if __name__ == "__main__":
    main()
