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

import java.util.HashMap;

import player.Player;
import table.BetRound;
import table.Table;

/**
 * bot.BotState - Created on 1-9-17
 *
 * Stores the complete state of the bot as it currently is,
 * Contains settings given by the engine, but also stuff that changes
 * each round, such as the board and the score for each player.
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class BotState {
    private int MAX_TIMEBANK;
    private int TIME_PER_MOVE;
    private int INITIAL_BIG_BLIND;
    private int HANDS_PER_BLIND_LEVEL;

    private int roundNumber;
    private BetRound betRound;
    private int timebank;
    private String myName;
    private HashMap<String, Player> players;
    private String onButtonPlayer;
    private int pot;  // Pot that this bot can currently win
    private int amountToCall;  // Amount this bot will bet when calling

    private Table table;

    BotState() {
        this.table = new Table();
        this.players = new HashMap<>();
    }

    public void setTimebank(int value) {
        this.timebank = value;
    }

    public void setMaxTimebank(int value) {
        this.MAX_TIMEBANK = value;
    }

    public void setTimePerMove(int value) {
        this.TIME_PER_MOVE = value;
    }

    public void setInitialBigBlind(int value) {
        this.INITIAL_BIG_BLIND = value;
    }

    public void setHandsPerBlindLevel(int value) {
        this.HANDS_PER_BLIND_LEVEL = value;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setBetRound(BetRound betRound) {
        this.betRound = betRound;
    }

    public void setOnButtonPlayer(String playerName) {
        this.onButtonPlayer = playerName;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public void setAmountToCall(int amount) {
        this.amountToCall = amount;
    }

    public int getTimebank() {
        return this.timebank;
    }

    public int getRoundNumber() {
        return this.roundNumber;
    }

    public BetRound getBetRound() {
        return this.betRound;
    }

    public HashMap<String, Player> getPlayers() {
        return this.players;
    }

    public Table getTable() {
        return this.table;
    }

    public String getMyName() {
        return this.myName;
    }

    public Player getOnButtonPlayer() {
        return this.players.get(this.onButtonPlayer);
    }

    public int getPot() {
        return this.pot;
    }

    public int getAmountToCall() {
        return this.amountToCall;
    }

    public int getMaxTimebank() {
        return this.MAX_TIMEBANK;
    }

    public int getTimePerMove() {
        return this.TIME_PER_MOVE;
    }

    public int getInitialBigBlind() {
        return this.INITIAL_BIG_BLIND;
    }

    public int getHandsPerBlindLevel() {
        return this.HANDS_PER_BLIND_LEVEL;
    }
}
