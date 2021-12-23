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

class EnigmaSpanish_FTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.Spanish_F.machine(), Enigma.machine("Spanish-F"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.Spanish_F.ETW, Enigma.Spanish_F.spec().entryWheel("ETW"));
		assertEquals(Enigma.Spanish_F.I, Enigma.Spanish_F.spec().rotor("I"));
		assertEquals(Enigma.Spanish_F.II, Enigma.Spanish_F.spec().rotor("II"));
		assertEquals(Enigma.Spanish_F.III, Enigma.Spanish_F.spec().rotor("III"));
		assertEquals(Enigma.Spanish_F.UKW, Enigma.Spanish_F.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.Spanish_F.machine()
				.reflector(Enigma.Spanish_F.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.Spanish_F.I.ring('A'), 'B')
				.rotor(2, Enigma.Spanish_F.II.ring('B'), 'A')
				.rotor(1, Enigma.Spanish_F.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("UBTMJVKYIFZYH", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
