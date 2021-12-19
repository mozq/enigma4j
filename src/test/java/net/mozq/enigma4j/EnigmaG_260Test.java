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

class EnigmaG_260Test {
	
	@Test void namedMachine() {
		assertEquals(Enigma.G_260.machine(), Enigma.machine("G-260"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.G_260.ETW, Enigma.G_260.spec().entryWheel("ETW"));
		assertEquals(Enigma.G_260.I, Enigma.G_260.spec().rotor("I"));
		assertEquals(Enigma.G_260.II, Enigma.G_260.spec().rotor("II"));
		assertEquals(Enigma.G_260.III, Enigma.G_260.spec().rotor("III"));
		assertEquals(Enigma.G_260.UKW, Enigma.G_260.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.G_260.machine()
				.reflector(Enigma.G_260.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.G_260.I.ring('A'), 'B')
				.rotor(2, Enigma.G_260.II.ring('B'), 'A')
				.rotor(1, Enigma.G_260.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("DBVTUYDKOENAV", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
