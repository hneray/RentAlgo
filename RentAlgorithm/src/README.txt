README

This is a basic interface for demonstrating an algorithm for dividing up rent between three roommates. The theory for the algorithm was developed by Hart Neray and Josh Levin who based it off of concepts from Dubins-Spanier's Moving Knife and the Dutch Auction resource division methods. As far as we are aware, this is an original solution.

The algorithm ultimately assigns each player a room with envy-freeness (https://en.wikipedia.org/wiki/Envy-freeness) within a given previously-agreed upon value, denoted as epsilon. That is, in the end, every player has a room and no one player thinks another player got a deal that was more than epsilon better than the deal that the first player got. For example, assume the renters agree to epsilon = $10. Then, if player 1 ultimately receives a room for $450 that he/she was initially willing to pay 
$500 for, he/she has gotten a deal of $500 - $450 = $50. Thus he/she won't see another player receive a room at a deal $10 better than that (e.g., player 2 could not get a room for $50+$10 = $60 less than what player 1 values that room at).

As far as the procedure of the algorithm, it algorithm works by asking players to (silently) state their starting bids for each room, which, for each player, must sum to the total rent of the apartment. The program then runs iteratively, going through each room and selling it to the player who has the highest bid for that room.
A player who has already won a given room may re-enter the bidding and swap his/her room for an open room if his/her bid minus epsilon is still the highest bid. As a player continues to re-enter the bidding, epsilon for that player is continually subtracted (such that the second time he/she re-enters, his/her bid will be considered as his/her original bid - minus 2 * epsilon.
The algorithm terminates once all of the rooms are assigned.  There may be a surplus of rent paid ultimately, in which case the current behavior is to divide the surplus evenly between the renters (although this behavior could be modified).


TODO:
-augmented error checking on user input, in particular name input
-statistical tools to measure average and max envy with varying epsilons
-more complete write-up/proof
-say how this algo guarantees each person gets a room and pays <=what they bid for it
--Integer still needed?
-clean up comments