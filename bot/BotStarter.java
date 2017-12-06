/*
 *  Copyright 2017 riddles.io (developers@riddles.io)
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 *      For the full copyright and license information, please view the LICENSE
 *      file that was distributed with this source code.
 */

package bot;
import java.util.*;
import com.stevebrecher.HandEval;

import java.util.ArrayList;
import java.util.stream.Stream;

import move.Move;
import move.MoveType;
import table.BetRound;
import table.card.Card;

/**
 * bot.BotStarter - Created on 1-9-17
 *
 * Magic happens here. You should edit this file, or more specifically
 * the doMove() method to make your bot do more than random moves.
 *
 * @author Jim van Eeden - jim@riddles.io
 * 
 * Updated by GoodBoye 3-12-17
 */
public class BotStarter {
	ArrayList<Card> hand;
	ArrayList<Card> table;
	int strength;
	boolean imOnButton;
	BotState state;
    /**
     * Implement this method to make the bot smarter.
     *
     * Not every move will be perfectly legal in all cases, but the engine will
     * transform the move to the logical alternative and output a warning if illegal.
     * @param state The current bot state
     * @return The move the bot wants to make
     */
    public Move doMove(BotState state) {
    	hand = state.getPlayers().get(state.getMyName()).getHand();
        table = state.getTable().getTableCards();
        this.state = state;
        strength = getHandStrength(hand, table);
        
        imOnButton = (state.getOnButtonPlayer().getName().equals(state.getMyName()));


        
       	
        if (state.getBetRound() == BetRound.PREFLOP) {
        	return preflopLogic();
        } else {
        	return simpleStrat();
        }
        	
        
        
    }
    
    /*
     * 
     * Preflop strat first betting round 
     * TODO: Explain in comment once finalised
     *
     */
    private Move preflopLogic() {
    	if (imOnButton) { //if im on the button
    			
 			if (strength >= HandEval.PAIR || rawStrength()>11) {
 				return new Move(MoveType.RAISE, state.getTable().getBigBlind() * 2);
 			} else {
 				//fold
 				return new Move(MoveType.CHECK);
 			}
    				
 
    	} else { //if im not on the button
 			
    		if ((strength >= HandEval.PAIR && rawStrength()>11) || rawStrength()>19) {
    			return new Move(MoveType.RAISE, state.getTable().getBigBlind() * 2);
 			} else if (strength >= HandEval.PAIR || rawStrength()>13) {
 				//call
 				return new Move(MoveType.CALL);
 			} else {
 				//fold
 				return new Move(MoveType.CHECK);
 			}
    				
    	}
    }
//    private Move flopLogic() {
//    	if (imOnButton) {
//			//
//    	} else {
//			//
//    	}
//    }
//    private Move turnLogic() {
//    	if (imOnButton) {
//			//
//    	} else {
//			//
//    	}
//    }
//    private Move riverLogic() {
//    	if (imOnButton) {
//			//
//    	} else {
//			//
//    	}
//    }
    

    /**
     * Calculates the bot's hand strength with 0, 3, 4, or 5 cards on the table.
     * This used the com.stevebrecher package to calculate the strength.
     * @param hand 2 cards in the hand
     * @param table 0, 3, 4, or 5 cards on the table
     * @return A number that indicates the hand strength. Higher numbers always
     * represent a stronger hand than lower numbers.
     */
    private int getHandStrength(ArrayList<Card> hand, ArrayList<Card> table) {
        if (hand.size() != 2) {
            throw new RuntimeException("Hand must contain exactly 2 cards.");
        }

        // Sum the codes of each card in hand and on the table to get the hand code
        long handCode = Stream.concat(hand.stream(), table.stream())
                .mapToLong(Card::getCode)
                .sum();

        switch (table.size()) {
            case 0:
                return hand.get(0).getHeight() == hand.get(1).getHeight() ? HandEval.PAIR : 0;
            case 3:
                return HandEval.hand5Eval(handCode);
            case 4:
                return HandEval.hand6Eval(handCode);
            case 5:
                return HandEval.hand7Eval(handCode);
        }

        return 0; // Never reached
    }

    /**
     * Small method to convert strength to a more readable enum called HandCategory
     * @param strength Strength value of a hand
     * @return Enum with different possible hands
     */
    private HandEval.HandCategory rankToCategory(int strength) {
        return HandEval.HandCategory.values()[strength >> HandEval.VALUE_SHIFT];
    }

    public static void main(String[] args) {
        BotParser parser = new BotParser(new BotStarter());
        parser.run();
    }
    
    //Constructs an ArrayList<Card> object from a string in the format of
    // height,suit such as "AH" = Ace of hearts, 4C = 4 of clubs etc
    private ArrayList<Card> createHandFromStr (String handStr) {
    	ArrayList<Card> minHand = new ArrayList<Card>();
    	minHand.add(new Card(handStr.substring(0,1)));
    	minHand.add(new Card(handStr.substring(2,3)));
    	return minHand;
    }
    

/*
 * Code below from starter bot, added other logic to take its place in some parts, but calling this
 * for non completed parts during development
 */
    private Move simpleStrat() {
    if (strength < HandEval.PAIR) {  // We only have a high card
        if (state.getBetRound() == BetRound.RIVER) {  // Check if we're on the river with high card
                    return new Move(MoveType.CHECK);
                }
  
                return new Move(MoveType.CALL);
            }
  
    if (strength < HandEval.STRAIGHT) {  // We have pair, two pair, or three of a kind
                return new Move(MoveType.CALL);
            }
  
    // We have a straight or higher
    return new Move(MoveType.RAISE, state.getTable().getBigBlind() * 2);  // Raise by minimum
    
    }
    
    /*
     * takes the strength of each card and sums the values, then adds 3 if they are suited
     * to get a single raw strength number.
     */
    private int rawStrength() {
    	Card card1 = hand.get(0);
    	Card card2 = hand.get(1);
    	int strength;
    	strength = card1.getHeight().getNumber() + card2.getHeight().getNumber();
    	if (card1.getSuit().getNumber() == card2.getSuit().getNumber()) {
    		strength +=3;
    	}
    	return strength;
    	
    }
}
