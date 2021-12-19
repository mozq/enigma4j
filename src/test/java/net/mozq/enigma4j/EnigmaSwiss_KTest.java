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

class EnigmaSwiss_KTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.Swiss_K.machine(), Enigma.machine("Swiss-K"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.Swiss_K.ETW, Enigma.Swiss_K.spec().entryWheel("ETW"));
		assertEquals(Enigma.Swiss_K.I, Enigma.Swiss_K.spec().rotor("I"));
		assertEquals(Enigma.Swiss_K.II, Enigma.Swiss_K.spec().rotor("II"));
		assertEquals(Enigma.Swiss_K.III, Enigma.Swiss_K.spec().rotor("III"));
		assertEquals(Enigma.Swiss_K.UKW, Enigma.Swiss_K.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.Swiss_K.machine()
				.reflector(Enigma.Swiss_K.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.Swiss_K.I.ring('A'), 'B')
				.rotor(2, Enigma.Swiss_K.II.ring('B'), 'A')
				.rotor(1, Enigma.Swiss_K.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("NGOHGZKGZIISX", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
