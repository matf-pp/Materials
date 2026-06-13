# 2.2.42: COGITO = ERGO * SUM
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['C', 'E', 'G', 'I', 'M', 'O', 'R', 'S', 'T', 'U']
    leading = ['C', 'E', 'S']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return value('ERGO', assignment) * value('SUM', assignment) == value('COGITO', assignment)

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('COGITO = ERGO * SUM', solution)


if __name__ == "__main__":
    main()
