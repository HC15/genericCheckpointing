# genericCheckpointing
Generic library to serialize and deserialize objects using dynamic proxies and reflection

# To Run Code
Assuming ant is installed and in the same directory as this README
ant -buildfile src/build.xml
ant -buildfile src/build.xml run -Darg0=mode -Darg1=N -Darg2=checkpoint.txt
mode is either "deser" for deserialization or "serdeser" for serialize then deserialize
N refers to number of objects to be deserialized in "deser"
N refers to the number of MyAllTypesFirst and MyAllTypesSecond created in "serdeser"
checkpoint.txt refers to the path of the file that is either being read by "deser" or written to and read by "serdeser"
