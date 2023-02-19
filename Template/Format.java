
String.format("%.5f", x) // print exactly 5 decimal places

String.format("%03d", x) // if has less than 3 digits, adds leading zeros

String.format("%03x", x) // hexadecimal

String.format("%16s", Integer.toBinaryString(x)).replace(' ', '0') // if has less 16 characters, adds leading zeros
