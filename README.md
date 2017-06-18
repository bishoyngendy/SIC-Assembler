# SIC-Assembler
Systems programming final project. A full, dynamic, two-pass assembler for the SIC machine. Implemented in Java. The program took source code in assembly, and produced loadable object code and a listing file. It report if there are any error in the assembly code.

### Assumptions
+ This is restricted on fixed fomrmat but can be easily extended for dynamic format by editing the parser which is done using regex 
+ Start statement is optional, if not provided the starting address will be “0000”. 
+ Start statement takes a hexadecimal operand without “0X’ while end statement takes a decimal operand. For Example: 
PROG	START	1000
	.
	.
	. 
	END	4096
 + Literals have three formats: =X'....' for hexadecimal values, =0000 for decimal values, =C'.....' for characters.
