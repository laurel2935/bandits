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
output_filename = sys.argv[2] if len(sys.argv) >= 3 else \
                  input_filename.split('.')[0]
plot_title = sys.argv[3] if len(sys.argv) == 4 else output_filename

# Make the plot
gplotter.plot(input_filename, output_filename, title=plot_title)