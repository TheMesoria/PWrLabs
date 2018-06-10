import os
import sys


class Mastermind:
    def __init__(self, round_length, player_name_1, player_name_2):
        self.players = [ player_name_1, player_name_2 ]
        self.player_score = [ 0, 0 ]
        self.current_match = 0
        self.round_length = round_length
        self.current_round = 0
        self.active_player = 0
        self.end_game = False


    def set_password(self):
        password_player = 0 if self.active_player == 1 else 1

        self.password = input("Player " + self.players[password_player] + " password: " )
        self.current_round += 1
        self.current_match += 1

        os.system('clear')


    def simulate_move(self, player):
        
        print("Current round: " + str(self.current_round) + ", current Match: " + str(self.current_match) )
        guess = input(" Attempt the code break: ")
        
        if guess == self.password:
            print("Password successfully broken.")
            self.player_score[self.active_player] += 1
            self.print_scoreboard()
            self.current_round = 0

        if self.current_round == self.round_length:
            print("You failed.")
            self.player_score[0 if self.active_player == 1 else 1] += 1
            self.print_scoreboard()
            self.current_round = 0

        self.print_detection(self.detect(guess))
        self.current_round += 1


    def start(self):
        while not self.end_game:
            if self.current_round == 0:
                self.set_password()

            self.simulate_move(self.active_player)


    def detect(self, player_input):
        correct_pins = 0
        good_pins = 0
        taken = []
        

        for item in range( 0, 3 ):
            if self.password[ item ] == player_input[ item ]:
                correct_pins += 1
                taken.append( item )
        
        for item in range( 0, 3 ) :
            if item in taken:
                continue
            else:
                if player_input[item] in self.password:
                    good_pins += 1

        return [ correct_pins, good_pins ]
            

    def print_detection(self, detection):
        print ( "Correct: " + str( detection[0] ) + ", and Good: " + str( detection[1] ) )


    def print_scoreboard(self):
        print( self.players[0] + " have " + str(self.player_score[0]) + " points." )
        print( self.players[1] + " have " + str(self.player_score[1]) + " points." )

