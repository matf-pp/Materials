from constraint import Problem

def solve():
    problem = Problem()
    problem.addVariable('X', range(1, 91))
    problem.addVariable('Y', range(2, 61, 2))
    problem.addVariable('Z', [i*i for i in range(1, 11)])
    problem.addConstraint(lambda x, z: x >= z, ['X', 'Z'])
    problem.addConstraint(lambda x, y, z: 2*x + y*x + z <= 34, ['X', 'Y', 'Z'])
    return problem.getSolutions()

def main():
    for r in solve():
        print(f"X = {r['X']}, Y = {r['Y']}, Z = {r['Z']}")

if __name__ == '__main__':
    main()
