from constraint import AllDifferentConstraint, Problem

def main():
    p = Problem(); p.addVariables('ABCDEFG', range(1,8)); p.addConstraint(AllDifferentConstraint(), 'ABCDEFG')
    for line in ['ACD','ADB','DBE','CDF','FDG','GDE']:
        p.addConstraint(lambda *x: sum(x) < 11, line)
    for line in ['CDBA','DABE','FCDG','FGDE']:
        p.addConstraint(lambda *x: sum(x) <= 16, line)
    for r in p.getSolutions(): print(r)
if __name__ == '__main__': main()
