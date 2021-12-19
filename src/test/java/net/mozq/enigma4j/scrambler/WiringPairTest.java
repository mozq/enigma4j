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
package net.mozq.enigma4j.scrambler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WiringPairTest {
	
	@Test void of_chars() {
		WiringPair wp = WiringPair.of('A', 'V');
		assertEquals('A', wp.letter1());
		assertEquals('V', wp.letter2());
	}
	
	@Test void of_str() {
		WiringPair wp = WiringPair.of("AV");
		assertEquals('A', wp.letter1());
		assertEquals('V', wp.letter2());
	}
	
	@Test void pairsToWiring_Blank() {
		assertEquals(
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
				WiringPair.pairsToWiring("ABCDEFGHIJKLMNOPQRSTUVWXYZ", WiringPair.toPairs(""))
				);
		
		assertEquals(
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
				WiringPair.pairsToWiring("ABCDEFGHIJKLMNOPQRSTUVWXYZ", WiringPair.toPairs(new String[0]))
				);
	}
	
	@Test void pairsToWiring() {
		assertEquals(
				"VSGLEUCZNJMDKIWPQXBTFAORYH",
				WiringPair.pairsToWiring("ABCDEFGHIJKLMNOPQRSTUVWXYZ", WiringPair.toPairs("AV BS CG DL FU HZ IN KM OW RX"))
				);
	}
	
	@Test void pairsToWiringUhr_00() {
		assertEquals(
				WiringPair.pairsToWiring("ABCDEFGHIKLMNOPQRSTUVWXYZ", WiringPair.toPairs("AV BS CG DL FU HZ IN KM OW RX")),
				WiringPair.pairsToWiringUhr("ABCDEFGHIKLMNOPQRSTUVWXYZ", WiringPair.toPairs("AV BS CG DL FU HZ IN KM OW RX"), 0)
				);
	}
	
	@Test void pairsToWiringUhr_01() {
		assertEquals(
				"GLWMEUANVXDRCSPQZHTBKFIYO",
				WiringPair.pairsToWiringUhr("ABCDEFGHIKLMNOPQRSTUVWXYZ", WiringPair.toPairs("AV BS CG DL FU HZ IN KM OW RX"), 1)
				);
	}
	
	@Test void pairsToWiringUhr_02() {
		assertEquals(
				"NZUXEWBMLVCIHSPQGATDRKOYF",
				WiringPair.pairsToWiringUhr("ABCDEFGHIKLMNOPQRSTUVWXYZ", WiringPair.toPairs("AV BS CG DL FU HZ IN KM OW RX"), 2)
				);
	}
	
	@Test void pairsToWiringUhr_03() {
		assertEquals(
				"UWLXESRZGNACFMPQVKTDHBIYO",
				WiringPair.pairsToWiringUhr("ABCDEFGHIKLMNOPQRSTUVWXYZ", WiringPair.toPairs("AV BS CG DL FU HZ IN KM OW RX"), 3)
				);
	}
	
	@Test void pairsToWiringUhr_04() {
		assertEquals(
				WiringPair.pairsToWiring("ABCDEFGHIKLMNOPQRSTUVWXYZ", WiringPair.toPairs("AZ BU CX DW FM HL IV KS OG RN")),
				WiringPair.pairsToWiringUhr("ABCDEFGHIKLMNOPQRSTUVWXYZ", WiringPair.toPairs("AV BS CG DL FU HZ IN KM OW RX"), 4)
				);
	}
	
	@Test void pairsToWiringUhr_36() {
		assertEquals(
				WiringPair.pairsToWiring("ABCDEFGHIKLMNOPQRSTUVWXYZ", WiringPair.toPairs("AL BM CW DX FN HG IS KV OZ RU")),
				WiringPair.pairsToWiringUhr("ABCDEFGHIKLMNOPQRSTUVWXYZ", WiringPair.toPairs("AV BS CG DL FU HZ IN KM OW RX"), 36)
				);
	}
	
	@Test void pairsToWiringUhr_39() {
		assertEquals(
				"MGUNELFWSXKIBVPQZRTCHOAYD",
				WiringPair.pairsToWiringUhr("ABCDEFGHIKLMNOPQRSTUVWXYZ", WiringPair.toPairs("AV BS CG DL FU HZ IN KM OW RX"), 39)
				);
	}
	
	@Test void uhrSetting_00() {
		assertEquals(0, WiringPair.uhrSetting("AA"));
		assertEquals(0, WiringPair.uhrSetting("FC"));
	}
	
	@Test void uhrSetting_01() {
		assertEquals(1, WiringPair.uhrSetting("AD"));
		assertEquals(1, WiringPair.uhrSetting("FE"));
	}
	
	@Test void uhrSetting_02() {
		assertEquals(2, WiringPair.uhrSetting("AF"));
		assertEquals(2, WiringPair.uhrSetting("FH"));
	}
	
	@Test void uhrSetting_03() {
		assertEquals(3, WiringPair.uhrSetting("AI"));
		assertEquals(3, WiringPair.uhrSetting("FJ"));
	}
	
	@Test void uhrSetting_04() {
		assertEquals(4, WiringPair.uhrSetting("AK"));
		assertEquals(4, WiringPair.uhrSetting("FM"));
	}
	
	@Test void uhrSetting_05() {
		assertEquals(5, WiringPair.uhrSetting("AN"));
		assertEquals(5, WiringPair.uhrSetting("FO"));
	}
	
	@Test void uhrSetting_06() {
		assertEquals(6, WiringPair.uhrSetting("AP"));
		assertEquals(6, WiringPair.uhrSetting("FR"));
	}
	
	@Test void uhrSetting_07() {
		assertEquals(7, WiringPair.uhrSetting("AS"));
		assertEquals(7, WiringPair.uhrSetting("FT"));
	}
	
	@Test void uhrSetting_08() {
		assertEquals(8, WiringPair.uhrSetting("AU"));
		assertEquals(8, WiringPair.uhrSetting("FW"));
	}
	
	@Test void uhrSetting_09() {
		assertEquals(9, WiringPair.uhrSetting("AX"));
		assertEquals(9, WiringPair.uhrSetting("FZ"));
	}
	
	@Test void uhrSetting_10() {
		assertEquals(10, WiringPair.uhrSetting("GA"));
		assertEquals(10, WiringPair.uhrSetting("MC"));
	}
	
	@Test void uhrSetting_20() {
		assertEquals(20, WiringPair.uhrSetting("NA"));
		assertEquals(20, WiringPair.uhrSetting("SC"));
	}
	
	@Test void uhrSetting_30() {
		assertEquals(30, WiringPair.uhrSetting("TA"));
		assertEquals(30, WiringPair.uhrSetting("ZC"));
	}
	
	@Test void uhrSetting_39() {
		assertEquals(39, WiringPair.uhrSetting("TX"));
		assertEquals(39, WiringPair.uhrSetting("ZZ"));
	}
}
