from constraint import ExactSumConstraint, Problem

COINS = [('1 din', 1), ('2 din', 2), ('5 din', 5), ('10 din', 10), ('20 din', 20)]

def solve():
    problem = Problem()
    for name, value in COINS:
        problem.addVariable(name, range(51 // value + 1))
    problem.addConstraint(ExactSumConstraint(50, [value for _, value in COINS]), [name for name, _ in COINS])
    return problem.getSolutions()

def main():
    for r in solve():
        for name, _ in COINS:
            print(f'{name}: {r[name]}')
        print('Ukupno:', sum(r[name] * value for name, value in COINS))

if __name__ == '__main__':
    main()
