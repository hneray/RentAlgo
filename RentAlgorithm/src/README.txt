README

This is a basic interface for demonstrating an algorithm for dividing up rent between three roommates. The theory for the algorithm was developed by Hart Neray and Josh Levin who based it off of concepts from Dubins-Spanier's Moving Knife and the Dutch Auction resource division methods. As far as we are aware, this is an original solution.

The algorithm ultimately assigns each renter a room at a price less than or equal to what he/she initially offers to pay for it. In addition—and more notably—this division is envy-free(https://en.wikipedia.org/wiki/Envy-freeness) within a previously-agreed upon value, denoted as epsilon. That is, in the end, every renter has a room and no one renter thinks another renter got a deal that was more than epsilon better than the deal that the first renter got.

For example, assume the renters agree to epsilon = $10. Assume renter 1 initially bids $500 for room 'a' and ultimately acquires it at $450. This means that renter 1 has gotten a "deal" of $500 - $450 = $50 and will not another room be sold at a deal that is more than $50 + $10 = $60 less than renter 1's initial bid.

As far as the procedure of the algorithm, it algorithm works by asking renters to (silently) state their starting bids for each room. For each renter, these starting bids must sum to the total rent of the apartment. The program then runs iteratively, going through each room and selling it to the renter who has the highest bid for that room.
A renter who has already won a given room may re-enter the bidding and swap his/her room for an open room if his/her bid minus epsilon is still the highest bid. As a renter continues to re-enter the bidding, epsilon for that renter is continually subtracted (such that the second time he/she re-enters, his/her bid will be considered as his/her original bid - 2 * epsilon.)

The algorithm terminates once all of the rooms are assigned. There may be a surplus of rent paid ultimately, in which case the surplus is divided evenly between the renters (although this behavior could be modified).


TODO:
-augmented error checking on user input, in particular name input
-statistical tools to measure average and max envy with varying epsilons
-add conceptual proof of both termination and envy-freeness to README
