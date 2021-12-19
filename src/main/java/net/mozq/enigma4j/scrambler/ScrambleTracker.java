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

/**
 * Scramble Tracker.
 */
public interface ScrambleTracker {
	
	/**
	 * Track forward and backward scrambling processing.
	 * For forward processing, numBwFrom and numBwTo parameters will be set to 0 (zero).
	 * 
	 * @param machine machine
	 * @param scrambler scrambler
	 * @param position current position of the scrambler 
	 * @param numFwFrom number before forward scrambled
	 * @param numFwTo number after forward scrambled
	 * @param numBwFrom number before backward scrambled
	 * @param numBwTo number after backward scrambled
	 */
	void track(EnigmaMachine machine, Scrambler scrambler, int position, int numFwFrom, int numFwTo, int numBwFrom, int numBwTo);
}