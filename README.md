# Streams
Visual Designer for Java Streams API

This project might be used to learn about the Java Stream API by playing around with it.

![Screenshot](doc/screen.png "Screenshot") 

Thanks to [qualitype](http://qualitype.de) for inspiration.

## Sample Usages

### Count lines in project

1. Choose **Find Files** as the data source and configure the path to your source files.
2. Add **Read Text File** as an intermediate operation.
3. Choose **Count** as the terminal operation.

You should see the total number of the lines in all files.
You might want to use **Regular Expression Filter** as an additional intermediate operation to skip certain lines (e.g. blanks or comments).

### Generate a file with sorted numbers

1. Choose **Random Integer Generator** and configure the number of values and their range.
2. Add **Distinct** as an intermediate operation to avoid duplicates.
3. Add **Sorted** as an intermediate operation.
4. Add **String Formatter** as an intermediate operation to convert the numbers into a format of your choice.
5. Use **Text File Writer** to write the numbers into a file.
