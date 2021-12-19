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

class EnigmaTest {
	
	@Test void tracker_Letter() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_A)
				.rotor(3, Enigma.I.II.ring(24), 'A')
				.rotor(2, Enigma.I.I.ring(13), 'B')
				.rotor(1, Enigma.I.III.ring(22), 'L')
				.plugboard("AM FI NV PS TU WZ");
		enigmaMachine.tracker((m, s, p, ff, ft, bf, bt) -> {
			if (bf == 0 && bt == 0) {
				System.out.println("" + m.letterOf(ff) + " -> " + m.letterOf(ft) + "  : " + s.name() + " [" + s.letterOf(p) + " (" + p + ")]");
			} else {
				System.out.println("" + m.letterOf(bf) + " -> " + m.letterOf(bt) + "  : " + s.name() + " [" + s.letterOf(p) + " (" + p + ")]");
			}
		});
		
		String plain = "A";
		System.out.println();
		System.out.println(plain);
		String encrypted = enigmaMachine.translate(plain);
		System.out.println(encrypted);
		System.out.println();
		
		assertEquals("U", encrypted);
	}
	
	@Test void tracker_RingLetters() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_A)
				.rotor(3, Enigma.I.II.ring(24), 'A')
				.rotor(2, Enigma.I.I.ring(13), 'B')
				.rotor(1, Enigma.I.III.ring(22), 'L')
				.plugboard("AM FI NV PS TU WZ");
		enigmaMachine.tracker((m, s, p, ff, ft, bf, bt) -> {
			int offset = (p - 1);
			String letters = s.letters().substring(offset) + s.letters().substring(0, offset);
			
			if (bf != 0 && bt != 0) {
				System.out.println(letters + "  : " + s.name() + " [" + s.letterOf(p) + " (" + p + ")]");
				StringBuilder sb = new StringBuilder(" ".repeat(s.size()));
				sb.replace(ff - 1, ff, "+");
				sb.replace(bt - 1, bt, "-");
				System.out.println(sb);
			}
		});
		
		String plain = "A";
		System.out.println();
		String encrypted = enigmaMachine.translate(plain);
		System.out.println(plain + " -> " + encrypted);
		System.out.println();
		
		assertEquals("U", encrypted);
	}
}
