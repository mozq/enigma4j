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

class EnigmaSpanish_STest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.Spanish_S.machine(), Enigma.machine("Spanish-S"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.Spanish_S.ETW, Enigma.Spanish_S.spec().entryWheel("ETW"));
		assertEquals(Enigma.Spanish_S.I, Enigma.Spanish_S.spec().rotor("I"));
		assertEquals(Enigma.Spanish_S.II, Enigma.Spanish_S.spec().rotor("II"));
		assertEquals(Enigma.Spanish_S.III, Enigma.Spanish_S.spec().rotor("III"));
		assertEquals(Enigma.Spanish_S.UKW, Enigma.Spanish_S.spec().reflector("UKW"));
	}
	
	@Test void translate_rotor123() {
		EnigmaMachine enigmaMachine = Enigma.Spanish_S.machine()
				.reflector(Enigma.Spanish_S.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.Spanish_S.I.ring('A'), 'B')
				.rotor(2, Enigma.Spanish_S.II.ring('B'), 'A')
				.rotor(1, Enigma.Spanish_S.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("YCUPDQEGZGDXV", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_rotor451() {
		EnigmaMachine enigmaMachine = Enigma.Spanish_S.machine()
				.reflector(Enigma.Spanish_S.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.Spanish_S.IV.ring('A'), 'B')
				.rotor(2, Enigma.Spanish_S.V.ring('B'), 'A')
				.rotor(1, Enigma.Spanish_S.I.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("HATKDHCDCGQRP", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_Plugboard() {
		EnigmaMachine enigmaMachine = Enigma.Spanish_S.machine()
				.reflector(Enigma.Spanish_S.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.Spanish_S.I.ring('A'), 'B')
				.rotor(2, Enigma.Spanish_S.II.ring('B'), 'A')
				.rotor(1, Enigma.Spanish_S.III.ring('C'), 'Z')
				.plugboard("AB CD EF GH IJ KL MN OP QR ST");
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("MYNIKBQCAJWCN", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
