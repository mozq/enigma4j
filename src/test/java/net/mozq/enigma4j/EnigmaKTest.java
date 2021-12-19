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

class EnigmaKTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.K.machine(), Enigma.machine("K"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.K.ETW, Enigma.K.spec().entryWheel("ETW"));
		assertEquals(Enigma.K.I, Enigma.K.spec().rotor("I"));
		assertEquals(Enigma.K.II, Enigma.K.spec().rotor("II"));
		assertEquals(Enigma.K.III, Enigma.K.spec().rotor("III"));
		assertEquals(Enigma.K.UKW, Enigma.K.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.K.machine()
				.reflector(Enigma.K.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.K.I.ring('A'), 'B')
				.rotor(2, Enigma.K.II.ring('B'), 'A')
				.rotor(1, Enigma.K.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("KFLHZQOPKOSIO", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
