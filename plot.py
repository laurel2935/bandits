'''
A small script to plot data in gnuplot automatically.  For requirements,
See the docstring for 'gplotter.py'
'''


import gplotter, sys

# Parse command line arguments in a shoddy way
usage = 'Usage: python plot.py input_filename [output_filename] [title]'
if len(sys.argv) <= 1:
    print usage
    quit()
input_filename = sys.argv[1]
if len(sys.argv) >= 3:
    output_filename = sys.argv[2]
else:
    output_filename = input_filename.split('.')[0]
if len(sys.argv) == 4:
    plot_title = sys.argv[3]
else:
    plot_title = output_filename

# Make the plot
gplotter.plot(input_filename, output_filename, title=plot_title)