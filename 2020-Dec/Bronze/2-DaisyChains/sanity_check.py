import os
import subprocess
import sys

class styles:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKCYAN = '\033[96m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

def header(msg):
    return f'+{"-" * (len(msg) + 2)}+\n| {msg} |\n+{"-" * (len(msg) + 2)}+'

def cyan(msg):
    return styles.OKCYAN + msg + styles.ENDC

def success(msg):
    return styles.OKGREEN + msg + styles.ENDC

def failure(msg):
    return styles.FAIL + msg + styles.ENDC

def warning(msg):
    return styles.WARNING + msg + styles.ENDC

def bold(msg):
    return styles.BOLD + msg + styles.ENDC

def format(msg):
    if msg.count('\n') > 7:
        lines = msg.split('\n')
        return ('\n').join(lines[:3]) + '\n...\n' + ('\n').join(lines[-3:])
    return msg

def getProgramAndDir():
    dir = ('/').join(__file__.split('/')[:-1]) + '/'

    programs = [x for x in os.listdir(('/').join(__file__.split('/')[:-1])) if x.lower().endswith('.java')]
    if len(programs) == 0:
        print('Error: ' + warning('Please creata a .java file in the directory.'))
        return None, dir
    elif len(programs) > 1:
        print('Error: ' + warning('Please make sure there is only one .java file in the directory. ' + str(programs)))
        return None, dir
    program = programs[0]

    return program, dir

def getNumTests():
    if not os.path.exists(dir + 'tests') > 0:
        print('Error: ' + warning('Please create a tests/ sub-directory.'))
        return None
    num_tests = int(len(os.listdir(dir + 'tests')) / 2)
    if num_tests == 0:
        print('Error: ' + warning('Please include test files in the tests/ sub-directory.'))
        return None
    return num_tests

def runSubprocess(args, input):
    java_program = subprocess.run(
        args,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
        input=input
        )
    out = java_program.stdout.strip()
    err = java_program.stderr.strip()
    return out, err

if __name__ == '__main__':
    program, dir = getProgramAndDir()
    num_tests = getNumTests()

    if not program or not num_tests:
        sys.exit()

    print('\n' + header(f'Running {num_tests} tests on {program}') + '\n')

    num_successes = 0
    for i in range(1, num_tests + 1):
        f_in = open(dir + f'tests/{i}.in', 'r')
        input = f_in.read().strip()
        f_in.close()

        f_in = open(dir + f'tests/{i}.out', 'r')
        expected_output = f_in.read().strip()
        f_in.close()

        actual_output, err = runSubprocess(['java', dir + program], input)
        passed = expected_output == actual_output
        
        if passed:
            num_successes += 1
            print(bold(success(f'PASSED test case {i}')) + '\n')
        else:
            print(bold(failure(f'FAILED test case {i}')) + '\n')
        
        print(bold('Input') + \
            ('\n' if '\n' in input else '           ') + \
            format(input) + '\n')
        print(bold('Expected answer') + \
            ('\n' if '\n' in expected_output else ' ') + \
            format(success(expected_output)) + '\n')
        print(bold('Your answer') + \
            ('\n' if '\n' in expected_output else '     ') + \
            format((success(actual_output) if passed else failure(actual_output))))
        if err:
            print(failure(err))
        print('\n' + cyan('=' * 60) + '\n')

    if (num_tests - num_successes == 0):
        print(bold(success(header(f'PASSED ALL TESTS ({num_tests}/{num_tests})'))))
    else:
        print(bold(failure(header(f'PASSED {num_successes}/{num_tests} TESTS'))))