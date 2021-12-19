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

class EnigmaDTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.D.machine(), Enigma.machine("D"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.D.ETW, Enigma.D.spec().entryWheel("ETW"));
		assertEquals(Enigma.D.I, Enigma.D.spec().rotor("I"));
		assertEquals(Enigma.D.II, Enigma.D.spec().rotor("II"));
		assertEquals(Enigma.D.III, Enigma.D.spec().rotor("III"));
		assertEquals(Enigma.D.UKW, Enigma.D.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.D.machine()
				.reflector(Enigma.D.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.D.I.ring('A'), 'B')
				.rotor(2, Enigma.D.II.ring('B'), 'A')
				.rotor(1, Enigma.D.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("KFLHZQOPKOSIO", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
