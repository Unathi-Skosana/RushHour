#Rush Hour board solver - written in Java

This program solves a standard 6 by 6 rush hour board game. The solver is
implemented to use a breadth-first search for an efficient solution,
After each successful solve the program outputs the solution in the format
specified in section file formats section.


##Execution

The input to the program is a commnad line argument that can specifies a 
single board file or a directory that contains board files.

     ####examples
     *`java RushHour testCases/`
     *`java RushHour testCases/testCase01`


##Rules

* Properties of the red block are specified foremost.
* The red block should be orientated horizontally.

##File formats

###Board file

The board files are written in block of four lines that represent properties of the vehicles
on that game board. The following text is a template of the properties of a single vehicle.
     
     #####template
     
     `color of vehicle`
     x-coordinate` `y-coordinate`
     `width` `height`
     ` allowed directions of movements`


     #####example

     `RE`
     `2` `2`
     `2` `1`
     `LR`

     This examples represents a red vehicle with upper leftmost edge at the
     coordinates (2,2) and with a width of 2, a height of 1. This vehicle
     can only move to the left or the right.

     LR - represents horizontal movement.
     DU - represents vertical movement.


###Solution file

The solution file basically specifies the movements of the vehicles in the
format that below.

     #####template
     `color of vehicle` `number of spaces to move` `direction of movements`

     #####example

     `RE` `1` `L`

     This examples represents a red vehicle being moved one space to the
     left.


###Color format

Colors are specified in a board file by taking the first two letters of the
color, if a color is a shade another colors. The first letter of the shade
and color are specified. i.e Dark Blue is specified as DB.

####Available colors

     Red - RE
     Purple - PU
     Brown - BR
     Maroon - MA
     Pink - PI
     Orange - OR
     Caustic - CA
     Soda - SO
     Lime - LI
     Yellow - YE
     Royal - RO
     Blue - BL
     Cyan - CY
     Dark Blue - DB
     Dark Green - DG


##Coordinate space
 
The coordinate space is divided by squares of length 1 each, and the coordinate
space of a board is formatted such that the upper left corner of the grid is the 
origin (0, 0). Thus coordinates for each vehicle on a game board specify the coordinates
of it's upper leftmost edge in such a coordinate space. i.e The coordinate (0, 0) would
mean that the upper leftmost edge of the vehicle is at the origin.

