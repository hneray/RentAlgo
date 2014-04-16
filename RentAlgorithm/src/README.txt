README

This is a basic interface for demonstrating an algorithm for dividing up rent between three roommates. The theory for the algorithm was developed by Hart Neray and Josh Levin who based it off of concepts from Dubins-Spanier's Moving Knife and the Dutch Auction resource division methods. As far as we are aware, this is an original solution.

The algorithm ultimately assigns each player a room with "envy-freeness", where epsilon is a measure of envy. That is, in the end, every player has a room and no player thinks another player got a deal that was more than epsilon better than the deal she got. For example, if player 1 ultimately receives a room for $450 that she was willing to pay 
$500 for, she won't see another player receive a room for more than $500 - $450 = $50 less than what she values it at. 

Conceptually, the algorithm works by having players state their starting bids for each room (which, for each player, must sum to the total rent of the apartment). The program then runs iteratively, going through each room and selling it to the player who has the highest bid for that room.
A player who has already won a given room may re-enter the bidding and swap his room for an open room if her bid minus epsilon is still the highest bid. As a player continues to re-enter the bidding, epsilon for that player is continually subtracted (such that the second time she re-enters her bid minus two epsilon must be the highest bid for the room in order for her to win it.)
It stops once all of the rooms are assigned.  

TODO:
-augmented error checking on user input 
-statistical tools to measure average and max envy with varying epsilons
Ñmore complete write-up/proof

