# 2.2.11: EARTH + AIR + FIRE + WATER = NATURE
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'E', 'F', 'H', 'I', 'N', 'R', 'T', 'U', 'W']
    leading = ['A', 'E', 'F', 'N', 'W']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['EARTH', 'AIR', 'FIRE', 'WATER']) == sum(value(word, assignment) for word in ['NATURE'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('EARTH + AIR + FIRE + WATER = NATURE', solution)


if __name__ == "__main__":
    main()
