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

class EnigmaSonderTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.Sonder.machine(), Enigma.machine("Sonder"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.Sonder.ETW, Enigma.Sonder.spec().entryWheel("ETW"));
		assertEquals(Enigma.Sonder.I, Enigma.Sonder.spec().rotor("I"));
		assertEquals(Enigma.Sonder.II, Enigma.Sonder.spec().rotor("II"));
		assertEquals(Enigma.Sonder.III, Enigma.Sonder.spec().rotor("III"));
		assertEquals(Enigma.Sonder.UKW, Enigma.Sonder.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.Sonder.machine()
				.reflector(Enigma.Sonder.UKW)
				.rotor(3, Enigma.Sonder.I.ring('A'), 'B')
				.rotor(2, Enigma.Sonder.II.ring('B'), 'A')
				.rotor(1, Enigma.Sonder.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("LWZFZEEKQYNXT", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
