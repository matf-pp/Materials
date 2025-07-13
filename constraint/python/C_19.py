from constraint import Problem

libraries = {
    'NumPy': {'depends_on': [], 'conflicts_with': ['Keras'], 'cost': 3, 'priority': 2},
    'Keras': {'depends_on': [], 'conflicts_with': ['NumPy', 'Pandas'], 'cost': 5, 'priority': 3},
    'Pandas': {'depends_on': [], 'conflicts_with': ['Keras'], 'cost': 2, 'priority': 1},
    'Seaborn': {'depends_on': ['NumPy'], 'conflicts_with': [], 'cost': 4, 'priority': 2},
    'OpenCV': {'depends_on': ['Keras', 'Pandas'], 'conflicts_with': [], 'cost': 3, 'priority': 3}
}

problem = Problem()

for lib in libraries:
    problem.addVariable(lib, [True, False])

def dependency_constraint(lib, dep):
    return not lib or dep

for lib, props in libraries.items():
    for dep in props['depends_on']:
        problem.addConstraint(dependency_constraint, (lib, dep))

def conflict_constraint(lib, con):
    return not (lib and con)

for lib, props in libraries.items():
    for con in props['conflicts_with']:
        problem.addConstraint(conflict_constraint, (lib, con))

def resource_constraint(*args):
    return sum(libraries[lib]['cost'] for lib, installed in zip(libraries, args) if installed) <= 15

problem.addConstraint(resource_constraint, libraries.keys())

def min_libraries_constraint(*args):
    return sum(args) >= 2

problem.addConstraint(min_libraries_constraint, libraries.keys())

solutions = problem.getSolutions()

max_priority_solution = None
max_priority_sum = 0

for solution in solutions:
    priority_sum = sum(libraries[lib]['priority'] for lib, installed in solution.items() if installed)
    if priority_sum > max_priority_sum:
        max_priority_solution = solution
        max_priority_sum = priority_sum

if max_priority_solution:
    print('Instalirane biblioteke:', end = ' ')
    for lib, installed in max_priority_solution.items():
        if installed:
            print(lib, end = ' ')
else:
    print("Nema rešenja koja zadovoljavaju sve zadate uslove.")
