/*!
 * enigma4j
 * Copyright 2021 Mozq
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.mozq.enigma4j.scrambler;

import net.mozq.enigma4j.machine.EnigmaMachine;
import net.mozq.enigma4j.machine.TurnoverMechanism;

/**
 * Scrambler chain.
 */
public class ScramblerChain {
	
	/** Machine */
	private EnigmaMachine machine;
	
	/** Scrambler */
	private Scrambler scrambler;
	
	/** Scrambler position */
	private int position;
	
	/** Next scrambler chain */
	private ScramblerChain next;
	
	/** Scramble tracker */
	private ScrambleTracker tracker;
	
	/**
	 * Constracts with the scrambler and the next scrambler chain.
	 * 
	 * @param machine machine
	 * @param scrambler scrambler
	 * @param next next scrambler chain
	 */
	public ScramblerChain(EnigmaMachine machine, Scrambler scrambler, ScramblerChain next) {
		this(machine, scrambler, 1, next);
	}
	
	/**
	 * Constracts with the scrambler, the scrambler position and the next scrambler chain.
	 * 
	 * @param machine machine
	 * @param scrambler scrambler
	 * @param position scrambler position
	 * @param next next scrambler chain
	 */
	public ScramblerChain(EnigmaMachine machine, Scrambler scrambler, int position, ScramblerChain next) {
		if (scrambler == null) {
			throw new IllegalArgumentException();
		}
		if (position < 1 || scrambler.size() < position) {
			throw new IllegalArgumentException();
		}
		
		this.machine = machine;
		this.scrambler = scrambler;
		this.position = position;
		this.next = next;
		this.tracker = null;
	}
	
	/**
	 * Set the tracker to this scrambler chain.
	 * 
	 * @param tracker tracker
	 */
	public void setTracker(ScrambleTracker tracker) {
		this.tracker = tracker;
	}
	
	/**
	 * Set the tracker to all scrambler chain.
	 * 
	 * @param tracker tracker
	 */
	public void setTrackerAll(ScrambleTracker tracker) {
		this.tracker = tracker;
		if (this.next != null) {
			this.next.setTrackerAll(tracker);
		}
	}
	
	/**
	 * Turns this scrambler if it is turnable.
	 * If it is in the turnover position, turns the next scrambler as well.
	 * 
	 * This method should be called every time BEFORE calling {@link #scramble(int)}.
	 * 
	 * @param turnoverMechanism turnover mechanism
	 */
	public void turn(TurnoverMechanism turnoverMechanism) {
		turn(turnoverMechanism, true);
	}
	
	/**
	 * Turns this scrambler if it is turnable.
	 * If it is in the turnover position, turns the next scrambler as well.
	 * 
	 * @param turnoverMechanism turnover mechanism
	 * @param forceTurn force to turn the scrambler even if it is not in the turnover position
	 */
	private void turn(TurnoverMechanism turnoverMechanism, boolean forceTurn) {
		if (this.scrambler.isTurnable()) {
			boolean turn = forceTurn;
			if (turnoverMechanism == TurnoverMechanism.COG_WHEEL) {
				// The cog-wheel driven wheel turnover mechanism
				
				if (this.scrambler.isTurnoverPosition(this.position)) {
					turn = true;
					
					// Turnover
					if (this.next != null) {
						this.next.turn(turnoverMechanism, true);
					}
				}
			} else {
				// The regular wheel turnover mechanism
				
				// Double stepping
				//   https://www.cryptomuseum.com/crypto/enigma/working.htm#double
				//   http://users.telenet.be/d.rijmenants/en/enigmatech.htm#steppingmechanism
				
				if ((this.next == null || (this.next != null && this.next.scrambler.isTurnable())) // UKW or Millde/Right rotor
						&& this.scrambler.isTurnoverPosition(this.position)) {
					turn = true;
					
					// Turnover
					if (this.next != null) {
						this.next.turn(turnoverMechanism, true);
					}
				} else {
					// Forward checking
					if (this.next != null) {
						this.next.turn(turnoverMechanism, false);
					}
				}
			}
			
			if (turn) {
				// Turn
				this.position = normalizeOneToBase(this.position, 1, this.scrambler.size());
			}
		} else {
			// Skip non turnable slot
			if (this.next != null) {
				this.next.turn(turnoverMechanism, forceTurn);
			}
		}
	}
	
	/**
	 * Scramble the value.
	 * 
	 * @param number number
	 * @return scrambled number
	 */
	public int scramble(int number) {
		if (number == -1) {
			return -1;
		}
		
		int offset = this.position - 1;
		
		// Forward
		int numFwFrom = number;
		int numFwTo = normalizeOneToBase(numFwFrom, offset, this.scrambler.size());
		numFwTo = this.scrambler.scrambleForward(numFwTo);
		numFwTo = normalizeOneToBase(numFwTo, -offset, this.scrambler.size());
		
		int scrambled;
		if (this.next == null) {
			// Reflector
			
			if (this.tracker != null) {
				this.tracker.track(this.machine, this.scrambler, this.position, numFwFrom, numFwTo, numFwFrom, numFwTo);
			}
			
			scrambled = numFwTo;
		} else {
			// Next slot
			
			if (this.tracker != null) {
				this.tracker.track(this.machine, this.scrambler, this.position, numFwFrom, numFwTo, 0, 0);
			}
			
			int numBwFrom = this.next.scramble(numFwTo);
			
			// Backward
			int numBwTo = normalizeOneToBase(numBwFrom, offset, this.scrambler.size());
			numBwTo = this.scrambler.scrambleBackward(numBwTo);
			numBwTo = normalizeOneToBase(numBwTo, -offset, this.scrambler.size());
			
			if (this.tracker != null) {
				this.tracker.track(this.machine, this.scrambler, this.position, numFwFrom, numFwTo, numBwFrom, numBwTo);
			}
			
			scrambled = numBwTo;
		}
		
		return scrambled;
	}
	
	private static int normalizeOneToBase(int n, int offset, int base) {
		if (n == -1) {
			return -1;
		}
		
		n = (n + offset) % base;
		
		if (n <= 0) {
			n += base;
		}
		
		return n;
	}
	
	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return this.scrambler.toString() + "; position=" + this.position + "(" + this.scrambler.letterOf(this.position) + ")";
	}
}
