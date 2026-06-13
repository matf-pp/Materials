# 2.2.6: COMPLEX + LAPLACE = CALCULUS
from constraint import AllDifferentConstraint, Problem


def value(word, assignment):
    total = 0
    for letter in word:
        total = total * 10 + assignment[letter]
    return total


def solve():
    letters = ['A', 'C', 'E', 'L', 'M', 'O', 'P', 'S', 'U', 'X']
    leading = ['C', 'L']
    problem = Problem()
    problem.addVariables(letters, range(10))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for letter in leading:
        problem.addConstraint(lambda digit: digit != 0, [letter])

    def equation(*digits):
        assignment = dict(zip(letters, digits))
        return sum(value(word, assignment) for word in ['COMPLEX', 'LAPLACE']) == sum(value(word, assignment) for word in ['CALCULUS'])

    problem.addConstraint(equation, letters)
    return problem.getSolutions()


def main():
    for solution in solve():
        print('COMPLEX + LAPLACE = CALCULUS', solution)


if __name__ == "__main__":
    main()
