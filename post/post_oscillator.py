import numpy as np
import math
import argparse
import random as rnd
import statistics
import matplotlib.pyplot as plt
import matplotlib.ticker as mtick
from matplotlib.ticker import (MultipleLocator, FormatStrFormatter, AutoMinorLocator)

# Files with the values
BEEMAN_FILE = "./parsable_files/beeman.txt"
VERLET_FILE = "./parsable_files/verlet.txt"
GEAR_FILE = "./parsable_files/gear.txt"
ANALYTIC_FILE = "./parsable_files/analytic.txt"
FILES = [ANALYTIC_FILE , VERLET_FILE, GEAR_FILE, BEEMAN_FILE]

# Plots the information for each file
def plot_oscillator_graphs():
    file_information = {}
    for file in FILES:
        times, positions = extract_info_for_file(file)
        file_information[file] = [times, positions]

    for file in file_information:
        plain_filename = file.rstrip("\t\n").split("/")[2]
        name = plain_filename.split(".")[0]
        plt.plot(file_information[file][0], file_information[file][1], 'o', label=name, markersize=2)

    plt.legend()
    plt.show()

# Extracting the time and positions for a file
def extract_info_for_file(filename):
    f = open(filename, 'r')
    positions = []
    times = []

    for line in f:
        data = line.rstrip("\t\n").split(" ")
        time = float(data[0])
        x = float(data[1])

        times.append(time)
        positions.append(x)

    return times, positions

# main() function
def main():
    # Command line args are in sys.argv[1], sys.argv[2] ..
    # sys.argv[0] is the script name itself and can be ignored
    # parse arguments
    parser = argparse.ArgumentParser(description="Post processing for the points data to generate data statistics")

    # add arguments
    # parser.add_argument('-t', dest='process_type', required=True)
    args = parser.parse_args()

    plot_oscillator_graphs()

# call main
if __name__ == '__main__':
    main()