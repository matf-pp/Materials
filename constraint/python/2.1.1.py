from constraint import Problem

def solve():
    problem = Problem()
    problem.addVariable('x', ['a', 'b', 'c'])
    problem.addVariable('y', [1, 2, 3])
    problem.addVariable('z', [0.1, 0.2, 0.3])
    problem.addConstraint(lambda y, z: y == int(round(10 * z)), ['y', 'z'])
    return problem.getSolutions()

def main():
    for r in solve():
        print(r['x'], r['y'], r['z'])

if __name__ == '__main__':
    main()
