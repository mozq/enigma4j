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
import net.mozq.enigma4j.scrambler.Reflector;
import net.mozq.enigma4j.scrambler.WiringPair;

import static org.junit.jupiter.api.Assertions.*;

class EnigmaKDTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.KD.machine(), Enigma.machine("KD"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.KD.ETW, Enigma.KD.spec().entryWheel("ETW"));
		assertEquals(Enigma.KD.I, Enigma.KD.spec().rotor("I"));
		assertEquals(Enigma.KD.II, Enigma.KD.spec().rotor("II"));
		assertEquals(Enigma.KD.III, Enigma.KD.spec().rotor("III"));
		assertEquals(Enigma.KD.UKW, Enigma.KD.spec().reflector("UKW"));
	}
	
	@Test void ukwd() {
		Reflector ukwdBP = Enigma.KD.UKW_D_BP(WiringPair.toPairs("BO AK CT DV EP FN GL HM IJ QW RY SX UZ"));
		Reflector ukwd = Enigma.KD.UKW_D(WiringPair.toPairs("AQ BG CK DI EL FX HZ MW NV OT PU RS"));
		
		assertEquals(new Reflector("UKW-D", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "KOTVPNLMJIAGHFBEWYXCZDQSRU").wiring(), ukwdBP.wiring());
		assertEquals(new Reflector("UKW-D", "A-ZXWVUTSRQPON-MLKIHGFEDCB", "Q-HFMNPORSAUTV-WECDZBXLIKG", null).wiring(), ukwd.wiring());
		
		for (int n = 1; n <= 26; n++) {
			assertEquals(ukwdBP.scrambleForward(n), ukwd.scrambleForward(n));
			assertEquals(ukwdBP.scrambleBackward(n), ukwd.scrambleBackward(n));
		}
	}
	
	@Test void translate() {
		EnigmaMachine enigmaMachine = Enigma.KD.machine()
				.reflector(Enigma.KD.UKW)
				.rotor(3, Enigma.KD.I.ring('A'), 'B')
				.rotor(2, Enigma.KD.II.ring('B'), 'A')
				.rotor(1, Enigma.KD.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("RXWZOOGBLTMYO", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_UKW_D() {
		EnigmaMachine enigmaMachine = Enigma.KD.machine()
				.reflector(Enigma.KD.UKW_D("AZ BC DE FG HI KL MN OP QR ST UV WX"))
				.rotor(3, Enigma.KD.I.ring('A'), 'B')
				.rotor(2, Enigma.KD.II.ring('B'), 'A')
				.rotor(1, Enigma.KD.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("BSQSNCBFOMHZF", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_UKW_D_BP() {
		EnigmaMachine enigmaMachine = Enigma.KD.machine()
				.reflector(Enigma.KD.UKW_D_BP("AC DE FG HI JK LM NP QR ST UV WX YZ"))
				.rotor(3, Enigma.KD.I.ring('A'), 'B')
				.rotor(2, Enigma.KD.II.ring('B'), 'A')
				.rotor(1, Enigma.KD.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("BSQSNCBFOMHZF", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
}
