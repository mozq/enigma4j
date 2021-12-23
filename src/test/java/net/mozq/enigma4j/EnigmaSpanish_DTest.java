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
package net.mozq.enigma4j;

import org.junit.jupiter.api.Test;

import net.mozq.enigma4j.machine.EnigmaMachine;

import static org.junit.jupiter.api.Assertions.*;

class EnigmaSpanish_DTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.Spanish_D.machine(), Enigma.machine("Spanish-D"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.Spanish_D.ETW, Enigma.Spanish_D.spec().entryWheel("ETW"));
		assertEquals(Enigma.Spanish_D.I, Enigma.Spanish_D.spec().rotor("I"));
		assertEquals(Enigma.Spanish_D.II, Enigma.Spanish_D.spec().rotor("II"));
		assertEquals(Enigma.Spanish_D.III, Enigma.Spanish_D.spec().rotor("III"));
		assertEquals(Enigma.Spanish_D.UKW, Enigma.Spanish_D.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.Spanish_D.machine()
				.reflector(Enigma.Spanish_D.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.Spanish_D.I.ring('A'), 'B')
				.rotor(2, Enigma.Spanish_D.II.ring('B'), 'A')
				.rotor(1, Enigma.Spanish_D.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("QWRYFOIYCIOYA", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	/**
	 * The Spanish Enigma message LRS10
	 * See: https://enigma.hoerenberg.com/index.php?cat=The%20Spanish%20Enigma%20Message
	 */
	@Test void translate_LRS10() {
		EnigmaMachine enigmaMachine = Enigma.Spanish_D.machine()
				.reflector(Enigma.Spanish_D.UKW.ring('A'), 'W')
				.rotor(3, Enigma.Spanish_D.II.ring('A'), 'Y')
				.rotor(2, Enigma.Spanish_D.I.ring('A'), 'A')
				.rotor(1, Enigma.Spanish_D.III.ring('H'), 'O');

		String encrypted = "OHACIPLYXTSIHJSBJCZXWYXPJJMUIWYFGVHTMALUTAEAVXHRXVQWDRVJKIMCYFRODNBXDISWSDIYXFJWVKHQHQMKZT";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("RADIOSDEBENREGRESARAZZESPANAZZTANPRONTOLESSEAPOSIBLEXCONTESTOSURADIOKKQQTKKDEDIEZACTUALXXX", plain);
	}
}
