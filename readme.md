# JOEtool

This is a tool intended to parse, convert, and create JOE files.
This tool is also intended to be usable in other projects.
# ~~Who is Joe?~~

~~Joe Mama~~
# What is JOE?

JOE is an encoded JSON format used by Star Wars Commander.
I'm pretty sure JOE is an acronym for "JsOn Encoded".
JOE does not represent JSON, it instead is structured similar to a spreadsheet file.
# What can JOE be used for?

JOE may allow us to control how the Star Wars Commander client behaves.
For example, a JOE file contains a value that controls faction flipping.
I'm pretty sure it is also capable of adding content to the game.
# What works right now

Currently, there are methods to convert JSON to and from the tool's internal format.
There are also methods to convert the internal JOE format to a JOE file.
This means you have the ability decode a JOE file, modify it, and re-encode it.
I am also planning on implementing functions that allow for extracting and recompiling asset bundles in the future.
# How to use this program
```
java -jar JOEtools.jar [options]
```
use the --help option to get a summary of commands
# Description of the JOE file format

**WIP**
See [JOE_FILE_FORMAT.md](JOE_FILE_FORMAT.md)
# SWC Modding
Coming Soon...

# Note

When dumping JOEs, I realized that some JOEs have different contents than their (original) JSON counterparts.
An example is base.json.joe and base.json.
The base.json file has 2 empty sheets that are not in the joe file.