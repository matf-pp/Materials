from constraint import Problem

def solve():
    problem = Problem()
    problem.addVariable('X', range(1, 111))
    problem.addVariable('Y', range(1, 52, 2))
    problem.addVariable('Z', range(10, 101, 10))
    problem.addVariable('W', [i**3 for i in range(1, 11)])
    problem.addConstraint(lambda x, w: x >= 2*w, ['X', 'W'])
    problem.addConstraint(lambda y, z: 3 + y <= z, ['Y', 'Z'])
    problem.addConstraint(lambda x, y, z, w: x - 11*w + y + 11*z <= 100, ['X', 'Y', 'Z', 'W'])
    return problem.getSolutions()

def main():
    for r in solve():
        print(f"X:{r['X']}, Y:{r['Y']}, Z:{r['Z']}, W:{r['W']}")

if __name__ == '__main__':
    main()
