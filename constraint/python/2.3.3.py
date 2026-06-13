from constraint import AllDifferentConstraint, ExactSumConstraint, Problem

LINES = [
    'ABC', 'DEFG', 'HIJKL', 'MNOP', 'QRS',
    'ADH', 'BEIM', 'CFJNQ', 'GKOR', 'LPS',
    'CGL', 'BFKP', 'AEJOS', 'DINR', 'HMQ',
]

def solve():
    problem = Problem()
    letters = 'ABCDEFGHIJKLMNOPQRS'
    problem.addVariables(letters, range(1, 20))
    problem.addConstraint(AllDifferentConstraint(), letters)
    for line in LINES:
        problem.addConstraint(ExactSumConstraint(38), line)
    return problem.getSolutions()

def main():
    for r in solve():
        print(' ', r['A'], r['B'], r['C'])
        print('', r['D'], r['E'], r['F'], r['G'])
        print(r['H'], r['I'], r['J'], r['K'], r['L'])
        print('', r['M'], r['N'], r['O'], r['P'])
        print(' ', r['Q'], r['R'], r['S'])

if __name__ == '__main__':
    main()
