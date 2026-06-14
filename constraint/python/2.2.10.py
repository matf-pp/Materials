# Komentar za studente: promenljive predstavljaju nepoznate vrednosti, domeni dozvoljene kandidate, a ogranicenja opisuju uslove zadatka.
# 2.2.10: WE + WANT + NO + NEW + ATOMIC = WEAPON
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'C', 'E', 'I', 'M', 'N', 'O', 'P', 'T', 'W']
    leading = ['A', 'N', 'W']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['WE', 'WANT', 'NO', 'NEW', 'ATOMIC']) == sum(value(word, assignment) for word in ['WEAPON'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('WE + WANT + NO + NEW + ATOMIC = WEAPON', solution)


if __name__ == "__main__":
    main()
