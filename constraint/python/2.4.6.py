# 2.4.6: ((JE + PENSE) - DONC) + JE = SUIS
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['C', 'D', 'E', 'I', 'J', 'N', 'O', 'P', 'S', 'U']
    leading = ['D', 'J', 'P', 'S']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return value('JE', assignment) + value('PENSE', assignment) - value('DONC', assignment) + value('JE', assignment) == value('SUIS', assignment)

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('((JE + PENSE) - DONC) + JE = SUIS', solution)


if __name__ == "__main__":
    main()
