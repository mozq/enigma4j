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

class EnigmaGTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.G.machine(), Enigma.machine("G"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.G.ETW, Enigma.G.spec().entryWheel("ETW"));
		assertEquals(Enigma.G.I, Enigma.G.spec().rotor("I"));
		assertEquals(Enigma.G.II, Enigma.G.spec().rotor("II"));
		assertEquals(Enigma.G.III, Enigma.G.spec().rotor("III"));
		assertEquals(Enigma.G.UKW, Enigma.G.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.G.machine()
				.reflector(Enigma.G.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.G.I.ring('A'), 'B')
				.rotor(2, Enigma.G.II.ring('B'), 'A')
				.rotor(1, Enigma.G.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("KDJWGYSJWZPKZ", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
