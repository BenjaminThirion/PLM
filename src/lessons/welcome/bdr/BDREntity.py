def getIndication():
    if isOverMessage():
        return readMessage()[0]
    else:
        return ''

# BEGIN SOLUTION
finished = False
while not finished:
    c = getIndication()
    if c == 'R':
        right()
        forward()
    elif c == 'L':
        left()
        forward()
    elif c == 'I':
        back()
        forward()
    elif c == 'A':
        forward()
    elif c == 'B':
        forward(2)
    elif c == 'C':
        forward(3)
    elif c == 'Z':
        backward()
    elif c == 'Y':
        backward(2)
    elif c == 'X':
        backward(3)
    else:        
        finished = True
# END SOLUTION

