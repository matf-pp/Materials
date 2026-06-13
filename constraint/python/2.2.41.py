# 2.2.41: MEN * AND = WOMEN
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'D', 'E', 'M', 'N', 'O', 'W']
    leading = ['A', 'M', 'W']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return value('MEN', assignment) * value('AND', assignment) == value('WOMEN', assignment)

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('MEN * AND = WOMEN', solution)


if __name__ == "__main__":
    main()
