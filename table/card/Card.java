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

package table.card;

/**
 * table.card.Card - Created on 1-9-17
 *
 * Represents a playing card. Contains the Number and Suit and is created
 * by giving a number 0 - 52.
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class Card {

    private CardHeight height;
    private CardSuit suit;
    private long code;  // Code used in HandEval

    public Card(String input) {
        this.height = CardHeight.fromString(input.charAt(0) + "");
        this.suit = CardSuit.fromString(input.charAt(1) + "");

        this.code = 1L << (16 * this.suit.getNumber() + this.height.getNumber());
    }

    public String toString() {
        return "" + this.height + this.suit;
    }

    public long getCode() {
        return this.code;
    }

    public CardHeight getHeight() {
        return this.height;
    }

    public CardSuit getSuit() {
        return this.suit;
    }
}
