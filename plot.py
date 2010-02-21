'''
A small script to plot data in gnuplot automatically.  For requirements,
See the docstring for 'gplotter.py'
'''


import gplotter
import sys
import getopt
import os

# Usage statement
usage = 'Usage: python plot.py input_filename [--output output_filename] \
[--title plot_title] [average num_points]'

# Extract command line arguments
try:
    argList, restOfArgs = getopt.gnu_getopt(sys.argv[1:], "", \
                          ["average=", "output=", "title="])    
except getopt.GetoptError, e:
    print e
    exit(1)

average = 0             # Number of points to average around.
output_filename = None  # Output filename
plot_title = None       # Title on plot.

# Parse arguments
for (arg, value) in argList:
    if arg == "--average":
        average = int(value)
    elif arg == "--output":
        output_filename = value
    elif arg == "--title":
        plot_title = value

# Print usage, if necessary
if len(restOfArgs) == 0:
    print usage
    quit()

input_filename = restOfArgs[0]

# Get output filename
if output_filename == None:
    output_filename = input_filename.split('.')[0]

# Get plot title
if plot_title == None:
    plot_title = output_filename

# Do averaging, if necessary
if average != 1:
    # Write averaged numbers to new file
    input_file = open(input_filename, 'r')
    new_filename = gplotter.temp_filename()
    new_input_file = open(new_filename, 'w')
    current_nums = []
    for line in input_file:
        line = line.split()
        # Make sure this is a valid line
        if len(line) != 2:
            continue
        x = int(line[0])
        y = float(line[1])
        current_nums = [y] + current_nums
        # Remove extras, if necessary
        if average != 0 and len(current_nums) > average:
            current_nums.pop()
            assert(len(current_nums) == average)
        val = sum(current_nums) / len(current_nums)
        new_input_file.write(str(x) + " " + str(val) + '\n')
    # Close files and replace filename
    input_file.close()
    new_input_file.close()
    # Make the plot
    gplotter.plot(new_filename, output_filename, title=plot_title)
    os.remove(new_filename)
    
# Otherwise, just plot what's there
else:
    gplotter.plot(input_filename, output_filename, title=plot_title)