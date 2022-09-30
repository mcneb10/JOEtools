# Introduction
JOE files are basically binary spreadsheet files.

Before I get in to the file format description, I must explain **Variable Length Numbers**.
This format of numbers is used a lot in this format.

Note: All non-variable length numbers are little endian

Integers and Floats are 4 bytes in size
# Variable Length Numbers
This is a big endian number split into bytes.
Each byte is split like this

#7(MSB) b:zero if last byte

#6-#0 data bits

The JOE parser removes the last bit and leftshifts the final value by 7
If bit #7 is zero, the method to parse variable length numbers returns.

# File Format Description
0-2: ASCII string "JOE"

3: Variable length with version (always zero)

4-7: Integer of String Count

After this is a string table formatted like this

1 variable length for length of string

The string

This is repeated for every thing

After this is an integer of the Integer Count

After this is a list of integers

After this is an integer of the Float Count

After this is a list of floats

After this is an integer of the String Array Count

A String array is formatted like this

Variable length of the amount of strings

For each string is a variable length that is an index to the list of strings

After this is a variable length with the sheet count

After this is a variable length with a index to the stings list. This is a name for a sheet.

After this is a repeating sheet data structure formatted like this

(
    Variable Length: Column Count
    
)

# TODO: Clean this up