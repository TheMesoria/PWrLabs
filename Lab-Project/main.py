from mastermind import Mastermind


player_1 = "Adam"
player_2 = "John"
# player1 = input("Player 1 name: ")
# player2 = input("Player 2 name: ")

x = Mastermind(10, player_1, player_2)
x.start()
