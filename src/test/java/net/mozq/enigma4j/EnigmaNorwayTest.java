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

class EnigmaNorwayTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.Norway.machine(), Enigma.machine("Norway"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.Norway.ETW, Enigma.Norway.spec().entryWheel("ETW"));
		assertEquals(Enigma.Norway.I, Enigma.Norway.spec().rotor("I"));
		assertEquals(Enigma.Norway.II, Enigma.Norway.spec().rotor("II"));
		assertEquals(Enigma.Norway.III, Enigma.Norway.spec().rotor("III"));
		assertEquals(Enigma.Norway.IV, Enigma.Norway.spec().rotor("IV"));
		assertEquals(Enigma.Norway.V, Enigma.Norway.spec().rotor("V"));
		assertEquals(Enigma.Norway.UKW, Enigma.Norway.spec().reflector("UKW"));
	}
	
	@Test void translate_rotor123() {
		EnigmaMachine enigmaMachine = Enigma.Norway.machine()
				.reflector(Enigma.Norway.UKW)
				.rotor(3, Enigma.Norway.I.ring('A'), 'B')
				.rotor(2, Enigma.Norway.II.ring('B'), 'A')
				.rotor(1, Enigma.Norway.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("IDMTGAQPTPQLJ", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_rotor451() {
		EnigmaMachine enigmaMachine = Enigma.Norway.machine()
				.reflector(Enigma.Norway.UKW)
				.rotor(3, Enigma.Norway.IV.ring('A'), 'B')
				.rotor(2, Enigma.Norway.V.ring('B'), 'A')
				.rotor(1, Enigma.Norway.I.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("BYYNGQBOQQNBC", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
