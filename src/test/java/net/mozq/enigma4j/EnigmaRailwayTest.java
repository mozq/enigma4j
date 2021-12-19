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

class EnigmaRailwayTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.Railway.machine(), Enigma.machine("Railway"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.Railway.ETW, Enigma.Railway.spec().entryWheel("ETW"));
		assertEquals(Enigma.Railway.I, Enigma.Railway.spec().rotor("I"));
		assertEquals(Enigma.Railway.II, Enigma.Railway.spec().rotor("II"));
		assertEquals(Enigma.Railway.III, Enigma.Railway.spec().rotor("III"));
		assertEquals(Enigma.Railway.UKW, Enigma.Railway.spec().reflector("UKW"));
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.Railway.machine()
				.reflector(Enigma.Railway.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.Railway.I.ring('A'), 'B')
				.rotor(2, Enigma.Railway.II.ring('B'), 'A')
				.rotor(1, Enigma.Railway.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("JYLAZZQVIBZED", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	/**
	 * Turing's Treatise, 1940
	 * See: http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
	 */
	@Test void translate_TuringsTreatise1940() {
		EnigmaMachine enigmaMachine = Enigma.Railway.machine()
				.reflector(Enigma.Railway.UKW.ring(26), 'J')
				.rotor(3, Enigma.Railway.III.ring(17), 'E')
				.rotor(2, Enigma.Railway.I.ring(16), 'Z')
				.rotor(1, Enigma.Railway.II.ring(13), 'A');
		
		String encrypted = "QSZVI DVMPN EXACM RWWXU IYOTY NGVVX DZ";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("DEUTS QETRU PPENS INDJE TZTIN ENGLA ND", plain);
	}
}
