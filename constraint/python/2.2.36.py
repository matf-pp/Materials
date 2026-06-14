# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.36: TWENTY + TWENTY + THIRTY = SEVENTY
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['E', 'H', 'I', 'N', 'R', 'S', 'T', 'V', 'W', 'Y']
    leading = ['S', 'T']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['TWENTY', 'TWENTY', 'THIRTY']) == sum(value(word, assignment) for word in ['SEVENTY'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('TWENTY + TWENTY + THIRTY = SEVENTY', solution)


if __name__ == "__main__":
    main()
