"""This module provides functionality for plotting the metrics using
gnuplot.

REQUIRES: gnuplot
Specifically, one should be able to run 'gnuplot' in the terminal and have
it run."""

import os, string, random

default_terminal = "postscript"
default_extension = ".ps"


def plot(input_filename, output_filename, xlabel="Round",
         ylabel="Average Regret", title="Plot Title",
         terminal=default_terminal, extension=default_extension,
         extra_commands=[]):
    """Creates a plot specified by the input data and filename.   Writes to
    file.  'data' should be a list of 2D points."""

    # Get the name for a file of commands
    command_filename = temp_filename()

    # Put neccessary commands in command file
    command_file = open(command_filename, "w")
    command_file.write("set terminal " + terminal + "\n")
    command_file.write("set output \"" + output_filename + extension + "\"\n")
    command_file.write("unset key\n")
    command_file.write("set xlabel \"" + xlabel + "\"\n")
    command_file.write("set ylabel \"" + ylabel + "\"\n")
    command_file.write("set title \"" + title + "\"\n")
    for command in extra_commands:
        command_file.write(command + "\n")
    command_file.write("plot \"" + input_filename + "\" with lines\n")
    command_file.close()

    os.system("gnuplot " + command_filename)
    os.remove(command_filename)

def random_filename(length=20, chars=string.letters):
    """Generates a random filename of the given length, taking characters
    from 'chars'."""
    filename = ""
    for _ in range(length):
        filename += random.choice(chars)
    return filename

def temp_filename(length=20, chars=string.letters):
    """Generates a random filename of the given length, taking characters
    from 'chars', ensuring that that file doesn't exist yet."""
    while True:
        temp_filename = random_filename(length, chars)
        if not os.path.isfile(temp_filename): # File doesn't exist
            break
    return temp_filename
