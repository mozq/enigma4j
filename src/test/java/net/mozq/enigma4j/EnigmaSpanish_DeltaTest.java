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

class EnigmaSpanish_DeltaTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.Spanish_Delta.machine(), Enigma.machine("Spanish-Delta"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.Spanish_Delta.ETW, Enigma.Spanish_Delta.spec().entryWheel("ETW"));
		assertEquals(Enigma.Spanish_Delta.I, Enigma.Spanish_Delta.spec().rotor("I"));
		assertEquals(Enigma.Spanish_Delta.II, Enigma.Spanish_Delta.spec().rotor("II"));
		assertEquals(Enigma.Spanish_Delta.III, Enigma.Spanish_Delta.spec().rotor("III"));
		assertEquals(Enigma.Spanish_Delta.UKW, Enigma.Spanish_Delta.spec().reflector("UKW"));
	}
	
	@Test void translate_rotor123() {
		EnigmaMachine enigmaMachine = Enigma.Spanish_Delta.machine()
				.reflector(Enigma.Spanish_Delta.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.Spanish_Delta.I.ring('A'), 'B')
				.rotor(2, Enigma.Spanish_Delta.II.ring('B'), 'A')
				.rotor(1, Enigma.Spanish_Delta.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("TPQVFAOMCWZKK", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_rotor451() {
		EnigmaMachine enigmaMachine = Enigma.Spanish_Delta.machine()
				.reflector(Enigma.Spanish_Delta.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.Spanish_Delta.IV.ring('A'), 'B')
				.rotor(2, Enigma.Spanish_Delta.V.ring('B'), 'A')
				.rotor(1, Enigma.Spanish_Delta.I.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("GRHEDWYDGQZAI", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
