# JOEtool

This is a tool intended to parse, convert, and create JOE files.
This tool is also intended to be usable in other projects.
# ~~Who is Joe?~~

~~Joe Mama.~~
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
There is also a method to convert JOE files to the tool's internal format.
That means it is only currently possible to dump JOE files to JSON.
I'm working on implementing a method that will convert the internal format to JOE.
This will allow for modding.
I am also planning on implementing functions that allow for extracting and recompiling assetbundles in the future.
# Description of the JOE file format

See [JOE_FILE_FORMAT.md](JOE_FILE_FORMAT.md)
