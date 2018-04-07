import math

# quadraticpy
# quadratic formula: (-b + or - sqrt(b^2 - 4ac)) / 2a
# define constant
# output is limited to 2 decimal places, can be changed below

dp = "%.2f"


def quadratic():
    # get the coefficients from the user
    a = float(input("Please enter coefficient a: "))
    b = float(input("Please enter coefficient b: "))
    c = float(input("Please enter coefficient c: "))
    if ((b * b) - (4 * a * c)) < 0:
        print("Delta is under 0")
        return
    discRoot = math.sqrt((b * b) - 4 * a * c)  # first pass

    root1 = (-b + discRoot) / (2 * a)  # solving positive
    root2 = (-b - discRoot) / (2 * a)  # solving negative

    print("done")
    print("The solutions are:", dp % root1, dp % root2)


if __name__ == '__main__':
    quadratic()
else:
    pass