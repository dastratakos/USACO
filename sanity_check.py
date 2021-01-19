"""
file:   sanity_check.py
author: Dean Stratakos
date:   January 17, 2020
------------------------
This script runs a sanity check for USACO contest problems. It allows solutions
to be coded in .java files where the input is taken from System.in, and the
output can be given to System.out. For example, you might write the following:
    Scanner input = new Scanner(System.in);
    // Solution...
    System.out.println(answer);

TODO: add ArgParser to specify command-line arguments
TODO: add time/memory constraints like in USACO
"""

import os
from pathlib import Path
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

def underline(msg):
    return styles.UNDERLINE + msg + styles.ENDC

def format(msg, verbose=False):
    if verbose: return msg

    lines = [(x[:97] + '...') if len(x) > 100 else x for x in msg.split('\n')]
    if len(lines) > 7:
        return ('\n').join(lines[:3]) + '\n...\n' + ('\n').join(lines[-3:])
    return ('\n').join(lines)

def askForInput(files):
    while True:
        try:
            filenum = int(input(f'Choose a file to test (1-{len(files)}): '))
        except ValueError:
            print("Sorry, I didn't understand that.")
            continue

        if filenum < 1 or filenum > len(files):
            print(f'Sorry, you must enter a number between 1 and {len(files)}.')
            continue
        else:
            break
    return filenum

def getProgramAndDir():
    files = sorted([str(x) for x in Path(".").rglob("*.[jJ][aA][vV][aA]")])
    if len(files) == 0:
        print('Error: ' + warning('No .java files found'))
        return None, None
    print(underline(f'Found {len(files)} .java files') + '\n')
    contest = files[0].split('/')[:2]
    for i, file in enumerate(files):
        if file.split('/')[:2] != contest:
            print()
            contest = file.split('/')[:2]
        print(f'{" " if i < 9 else ""}[{i + 1}]: ' + \
            ('\t').join(file.split('/')[:2]) + ('\t') + \
            file.split('/')[-2][0] + ' ' + file.split('/')[-2][2:])
    print()

    filenum = askForInput(files)

    program = files[filenum - 1]
    dir = ('/').join(program.split('/')[:-1]) + '/'
    return program, dir

def getNumTests(dir):
    if not os.path.exists(dir + 'tests'):
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

def runTestCases(program, num_tests, verbose=False):
    print('\n' + header(f'Running {num_tests} tests on {program}') + '\n')

    num_successes = 0
    for i in range(1, num_tests + 1):
        f_in = open(dir + f'tests/{i}.in', 'r')
        input = f_in.read().strip()
        f_in.close()

        f_in = open(dir + f'tests/{i}.out', 'r')
        expected_output = f_in.read().strip()
        f_in.close()

        actual_output, err = runSubprocess(['java', program], input)
        passed = expected_output == actual_output
        
        if passed:
            num_successes += 1
            print(bold(success(f'PASSED test case {i}')) + '\n')
        else:
            print(bold(failure(f'FAILED test case {i}')) + '\n')
        
        print(bold('Input') + \
            ('\n' if '\n' in input else '           ') + \
            format(input, verbose=verbose) + '\n')
        print(bold('Expected answer') + \
            ('\n' if '\n' in expected_output else ' ') + \
            success(format(expected_output, verbose=verbose)) + '\n')
        print(bold('Your answer') + \
            ('\n' if '\n' in expected_output else '     ') + \
            success(format(actual_output, verbose=verbose)) if passed else
            failure(format(actual_output, verbose=verbose)))
        if err:
            print(failure(err))
        print('\n' + cyan('=' * 60) + '\n')

    if (num_tests - num_successes == 0):
        print(bold(success(header(f'PASSED ALL TESTS ({num_tests}/{num_tests})'))))
    else:
        print(bold(failure(header(f'PASSED {num_successes}/{num_tests} TESTS'))))

if __name__ == '__main__':
    print('\n' + cyan(header('USACO SANITY CHECK')) + '\n')

    program, dir = getProgramAndDir()
    num_tests = getNumTests(dir)

    if not program or not num_tests:
        sys.exit()

    runTestCases(program, num_tests, verbose=False)