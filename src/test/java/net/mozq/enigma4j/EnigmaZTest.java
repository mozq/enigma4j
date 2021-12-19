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

class EnigmaZTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.Z.machine(), Enigma.machine("Z"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.Z.ETW, Enigma.Z.spec().entryWheel("ETW"));
		assertEquals(Enigma.Z.I, Enigma.Z.spec().rotor("I"));
		assertEquals(Enigma.Z.II, Enigma.Z.spec().rotor("II"));
		assertEquals(Enigma.Z.III, Enigma.Z.spec().rotor("III"));
		assertEquals(Enigma.Z.UKW, Enigma.Z.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.Z.machine()
				.reflector(Enigma.Z.UKW)
				.rotor(3, Enigma.Z.I.ring('1'), '2')
				.rotor(2, Enigma.Z.II.ring('2'), '1')
				.rotor(1, Enigma.Z.III.ring('3'), '0');
		
		String plain = "09876543210987654321";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("35752296038575035600", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
