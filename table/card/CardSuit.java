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

import java.util.HashMap;
import java.util.Map;

/**
 * table.card.CardSuit - Created on 1-9-17
 *
 * All the different card Suits
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public enum CardSuit {
    SPADES("s", 0),
    HEARTS("h", 1),
    CLUBS("c", 2),
    DIAMONDS("d", 3);

    private static final Map<String, CardSuit> TYPE_MAP = new HashMap<>();
    private String shorthand;
    private int number;

    static {
        for (CardSuit moveType : values()) {
            TYPE_MAP.put(moveType.toString(), moveType);
        }
    }

    CardSuit(String shorthand, int number) {
        this.shorthand = shorthand;
        this.number = number;
    }

    public static CardSuit fromString(String string) {
        return TYPE_MAP.get(string);
    }

    @Override
    public String toString() {
        return this.shorthand;
    }

    public int getNumber() {
        return this.number;
    }
}
