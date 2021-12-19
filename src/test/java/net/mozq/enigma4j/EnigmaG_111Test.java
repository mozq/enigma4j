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

class EnigmaG_111Test {
	
	@Test void namedMachine() {
		assertEquals(Enigma.G_111.machine(), Enigma.machine("G-111"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.G_111.ETW, Enigma.G_111.spec().entryWheel("ETW"));
		assertEquals(Enigma.G_111.I, Enigma.G_111.spec().rotor("I"));
		assertEquals(Enigma.G_111.II, Enigma.G_111.spec().rotor("II"));
		assertEquals(Enigma.G_111.V, Enigma.G_111.spec().rotor("V"));
		assertEquals(Enigma.G_111.UKW, Enigma.G_111.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.G_111.machine()
				.reflector(Enigma.G_111.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.G_111.I.ring('A'), 'B')
				.rotor(2, Enigma.G_111.II.ring('B'), 'A')
				.rotor(1, Enigma.G_111.V.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("TNBMSJUCIQIUA", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
