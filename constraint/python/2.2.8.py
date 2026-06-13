# 2.2.8: CROSS + ROADS = DANGER
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'C', 'D', 'E', 'G', 'N', 'O', 'R', 'S']
    leading = ['C', 'D', 'R']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['CROSS', 'ROADS']) == sum(value(word, assignment) for word in ['DANGER'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('CROSS + ROADS = DANGER', solution)


if __name__ == "__main__":
    main()
