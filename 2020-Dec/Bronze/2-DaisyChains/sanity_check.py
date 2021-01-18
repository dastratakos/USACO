import os
import subprocess

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

def success(msg):
    return styles.OKGREEN + msg + styles.ENDC

def failure(msg):
    return styles.FAIL + msg + styles.ENDC

def bold(msg):
    return styles.BOLD + msg + styles.ENDC

if __name__ == '__main__':
    programs = [x for x in os.listdir(('/').join(__file__.split('/')[:-1])) if x.lower().endswith('.java')]
    assert len(programs) == 1, 'Please make sure there is only one .java file in the directory. ' + str(programs)
    program = programs[0]
    dir = ('/').join(__file__.split('/')[:-1]) + '/'

    num_tests = int(len(os.listdir(dir + 'tests')) / 2)

    print('\n' + header(f'Running {num_tests} tests on {program}') + '\n')

    num_successes = 0
    for i in range(1, num_tests + 1):
        f_in = open(dir + f'tests/{i}.in', 'r')
        input = f_in.read().strip()
        f_in.close()

        f_in = open(dir + f'tests/{i}.out', 'r')
        expected_output = f_in.read().strip()
        f_in.close()

        java_program = subprocess.run(
            ['java', dir + program],
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
            input=input
            )
        actual_output = java_program.stdout.strip()

        ok = expected_output == actual_output
        
        if ok:
            num_successes += 1
            print(bold(success(f'PASSED test case {i}')))
        else:
            print(bold(failure(f'FAILED test case {i}')))
        
        print(bold('Input') + \
            ('\n' if '\n' in input else '           ') + \
            input)
        print(bold('Expected answer') + \
            ('\n' if '\n' in expected_output else ' ') + \
            success(expected_output))
        print(bold('Your answer') + \
            ('\n' if '\n' in expected_output else '     ') + \
            (success(actual_output) if ok else failure(actual_output)))
        if java_program.stderr.strip():
            print(failure(java_program.stderr.strip()))
        print()

    if (num_tests - num_successes == 0):
        print(bold(success(header(f'PASSED ALL TESTS ({num_tests}/{num_tests})'))))
    else:
        print(bold(failure(header(f'PASSED {num_successes}/{num_tests} TESTS'))))