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
 */
public class BotStarter {

    /**
     * Implement this method to make the bot smarter.
     * Currently will check the strength of the hand and will Check, Call, or Raise.
     *
     * Not every move will be perfectly legal in all cases, but the engine will
     * transform the move to the logical alternative and output a warning if illegal.
     * @param state The current bot state
     * @return The move the bot wants to make
     */
    public Move doMove(BotState state) {
        ArrayList<Card> hand = state.getPlayers().get(state.getMyName()).getHand();
        ArrayList<Card> table = state.getTable().getTableCards();

        int strength = getHandStrength(hand, table);

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
}
