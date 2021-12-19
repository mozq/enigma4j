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

class EnigmaA_133Test {
	
	@Test void namedMachine() {
		assertEquals(Enigma.A_133.machine(), Enigma.machine("A-133"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.A_133.ETW, Enigma.A_133.spec().entryWheel("ETW"));
		assertEquals(Enigma.A_133.I, Enigma.A_133.spec().rotor("I"));
		assertEquals(Enigma.A_133.II, Enigma.A_133.spec().rotor("II"));
		assertEquals(Enigma.A_133.III, Enigma.A_133.spec().rotor("III"));
		assertEquals(Enigma.A_133.UKW, Enigma.A_133.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.A_133.machine()
				.reflector(Enigma.A_133.UKW)
				.rotor(3, Enigma.A_133.I.ring('A'), 'B')
				.rotor(2, Enigma.A_133.II.ring('B'), 'A')
				.rotor(1, Enigma.A_133.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGEÅÄÖ";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("DTLGXFRILENLHVFV", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
