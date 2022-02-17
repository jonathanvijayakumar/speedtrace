# Speedtrace
A simple Java based traceability app.

# API based access
- createReader() creates a fresh copy of reading handles for excel files based on the current configuration.
- readRequirements() returns a list of requirements from all files and sheets configured for reading. Currently return type is a list of strings as only the ID is returned. In further commits more data can be returned. But that would change the architecture of the app.

# File Access
Multiple XLSX files may be added to the project for reading requirements:
- reader.addFiles() takes a list strings which adds to the internally kept list of files to be read.
- addColumnData() can be used to set the columns to be read. Multiple sheets may be read in a single file. Row and column numbers start at 0.
- setRegexsForValidation() Multiple requirements formats may be read at a time, for every format a regex string is to be provided, as a list of strings containing all the regex strings.

Design files can now be loaded and read, currently supported files types are EA designs. Before running the app, copy the dll files from libraries > EA Java API into your system 'PATH'. Copy the 64bit version is your system is a x86_64 system.

# Reader Object
RequirementsReader may be instantiated as the user wishes and is not a singleton. Hence many instances of the reader may be maintained (for whatever reason)!

# Configuration in JSON
JSON can be used to specify requirements specification and design meta data like paths and columns.
Once config is loaded files can be read repeatedly, even when it's changed. 

Check data.json for a sample configuration

# Info
This app can be used along with your java source and can also be used standalone. The standalone feature is yet to be written.
Refer the class SpeedTraceHome for an example of how the app can be used via API.
