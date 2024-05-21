# Chuck Norris Cipher Encoder (Kotlin)
## Description
Binary with 0 and 1 is good, but binary with only 0 is even better! This encoding has a name — the Chuck Norris Unary Code.

Chuck Norris encoder converts a given text into a sequence of zeros and spaces!

## Objectives
The encoding principle is simple. The input message consists of ASCII characters (7-bit). Chuck Norris technique transforms the text into the sequence of 0 and 1. The encoded output message consists of blocks of 0. A block is separated from another block by a space.

Two consecutive blocks are used to produce a series of the same value bits (only 1 or 0 values):

1. First block: it is always 0 or 00. If it is 0, then the series contains 1, if not, it contains 0
2. Second block: the number of 0 in this block is the number of bits in the series

Let's take a simple example with a message which consists of only one character C. The C symbol in binary is represented as 1000011, so with Chuck Norris technique this gives:

* 0 0 (the first series consists of only a single 1);
* 00 0000 (the second series consists of four 0);
* 0 00 (the third consists of two 1)
* So C is coded as: 0 0 00 0000 0 00

## Examples
### Example 1
```text
Please input operation (encode/decode/exit):
> encode
Input string:
> Hey!
Encoded string:
0 0 00 00 0 0 00 000 0 00 00 00 0 0 00 0 0 00000 00 00 0 0 00 0 0 0 00 0000 0 0

Please input operation (encode/decode/exit):
> decode
Input encoded string:
> 0 0 00 00 0 0 00 000 0 00 00 00 0 0 00 0 0 00000 00 00 0 0 00 0 0 0 00 0000 0 0
Decoded string:
Hey!

Please input operation (encode/decode/exit):
> exit
Bye!
```

### Example 2
```text
Please input operation (encode/decode/exit):
> smile
There is no 'smile' operation

Please input operation (encode/decode/exit):
> decode
Input encoded string:
> 0 0 00 00 0 0 00 000
Decoded string:
H

Please input operation (encode/decode/exit):
> decode
Input encoded string:
> 0 0 1 00 0 0 1 000
Encoded string is not valid.

Please input operation (encode/decode/exit):
> decode
Input encoded string:
> 000 0 00 00 0000 0 00 000
Encoded string is not valid.

Please input operation (encode/decode/exit):
> decode
Input encoded string:
> 0 0 00 00 0 0 00
Encoded string is not valid.

Please input operation (encode/decode/exit):
> decode
Input encoded string:
> 0 0 00 00 0 0 00 00
Encoded string is not valid.

Please input operation (encode/decode/exit):
> exit
Bye!
```