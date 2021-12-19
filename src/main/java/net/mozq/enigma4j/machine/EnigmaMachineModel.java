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
package net.mozq.enigma4j.machine;

/**
 * Enigma machine model.
 */
public abstract class EnigmaMachineModel {
	
	/** Letters 26 */
	protected static final String LETTERS_26 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/** Letters 28 */
	protected static final String LETTERS_28 = "ABCDEFGHIJKLMNOPQRSTUVXYZÅÄÖ";
	
	/** Letters 10 */
	protected static final String LETTERS_10 = "1234567890";
	
	/**
	 * Constracts.
	 */
	protected EnigmaMachineModel() {
		// NOP
	}
}