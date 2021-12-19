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

class EnigmaTTest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.T.machine(), Enigma.machine("T"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.T.ETW, Enigma.T.spec().entryWheel("ETW"));
		assertEquals(Enigma.T.I, Enigma.T.spec().rotor("I"));
		assertEquals(Enigma.T.II, Enigma.T.spec().rotor("II"));
		assertEquals(Enigma.T.III, Enigma.T.spec().rotor("III"));
		assertEquals(Enigma.T.IV, Enigma.T.spec().rotor("IV"));
		assertEquals(Enigma.T.V, Enigma.T.spec().rotor("V"));
		assertEquals(Enigma.T.VI, Enigma.T.spec().rotor("VI"));
		assertEquals(Enigma.T.VII, Enigma.T.spec().rotor("VII"));
		assertEquals(Enigma.T.VIII, Enigma.T.spec().rotor("VIII"));
		assertEquals(Enigma.T.UKW, Enigma.T.spec().reflector("UKW"));
	}
	
	@Test void translate_rotor123() {
		EnigmaMachine enigmaMachine = Enigma.T.machine()
				.reflector(Enigma.T.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.T.I.ring('A'), 'B')
				.rotor(2, Enigma.T.II.ring('B'), 'A')
				.rotor(1, Enigma.T.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("UIWVIPTFYCBVB", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_rotor456() {
		EnigmaMachine enigmaMachine = Enigma.T.machine()
				.reflector(Enigma.T.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.T.IV.ring('A'), 'B')
				.rotor(2, Enigma.T.V.ring('B'), 'A')
				.rotor(1, Enigma.T.VI.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("YKHUMXCHLQQTU", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_rotor781() {
		EnigmaMachine enigmaMachine = Enigma.T.machine()
				.reflector(Enigma.T.UKW.ring('Z'), 'C')
				.rotor(3, Enigma.T.VII.ring('A'), 'B')
				.rotor(2, Enigma.T.VIII.ring('B'), 'A')
				.rotor(1, Enigma.T.I.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("XDDSFDZIZIKQA", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
//	/**
//	 * German Enigma T message A
//	 * See: https://cryptocellar.org/bgac/EnigmaT_messages.html
//	 */
//	@Test void translate_A() {
//		EnigmaMachine enigmaMachine = Enigma.T.machine()
//				;
//		
//		String encrypted = "HCHFZ JIISW NXFZQ JOHCG TSWYI FBHKD TJLJH XLHQT NZEXR YLIAE\n"
//				+ "XHQHW LAYXW KGDRX FLDPC PYLSE XEBDP HPQTY YEKHN RGZVX XNZHP\n"
//				+ "XGQPL ACDKI VUPGV NUSUZ HALMB ACBHI LVYVC HNOYU XIACT IVVKG\n"
//				+ "UJEPJ BOJHX KHTFQ ZVVTZ VBDDA MLVPS ZMJCU AZMWU WSAXF ZVPUF\n"
//				+ "LULIF EZATT ASOLY JRDVP HYVRE GSBBV OQJMN YPUMA IQWQJ";
//		String plain = enigmaMachine.translate(encrypted);
//		assertEquals("", plain);
//	}
//	
//	/**
//	 * German Enigma T message B
//	 * See: https://cryptocellar.org/bgac/EnigmaT_messages.html
//	 */
//	@Test void translate_B() {
//		EnigmaMachine enigmaMachine = Enigma.T.machine()
//				;
//		
//		String encrypted = "DJYQT FKCHD JEBKM QZDFY QLHCP TLKUL RYIAA BDGEJ XWDBY BABMH\n"
//				+ "LSVLF NCSUA BNBKW BRNRP XZOKE UJDLO QYYGV ZGHXG IGJLO ODTRC\n"
//				+ "SQQOO GTBET RNGUP IVWGF JNGBW MISQY KVYJI GCTYO YLWWC CXFPM\n"
//				+ "IKVHQ RRBJA ZLZAE SAMPF BJXOT QOYGQ TYQEI NHZYQ ZCWOP XBOUJ\n"
//				+ "AOEIV GGJKG MKHNB PQKBI ZNVEH GENJU MNGEI FGJXU UMWRC";
//		String plain = enigmaMachine.translate(encrypted);
//		assertEquals("", plain);
//	}
//	
//	/**
//	 * German Enigma T message C
//	 * See: https://cryptocellar.org/bgac/EnigmaT_messages.html
//	 */
//	@Test void translate_C() {
//		EnigmaMachine enigmaMachine = Enigma.T.machine()
//				;
//		
//		String encrypted = "IHXLP RWUIK IRCYP XNVSF ERKMK MNJZZ ZTDBF GMFBO JGADL KJSVG\n"
//				+ "JKSGB JQFKU FXWVS MWGKO CPKMQ KFDDR MRDSQ OAOIU GAIRM ZZCBQ\n"
//				+ "MEFMG ZVAOQ QWJXN JENOF DBHVK";
//		String plain = enigmaMachine.translate(encrypted);
//		assertEquals("", plain);
//	}
//	
//	/**
//	 * German Enigma T Sample TIRPITZ (JN-18) Traffic
//	 * See: https://cryptocellar.org/bgac/EnigmaT_messages.html
//	 */
//	@Test void translate_SampleTraffic() {
//		EnigmaMachine enigmaMachine = Enigma.T.machine()
//				;
//		
//		String encrypted = "KLDSS ANGWY XWMID CRXFC CLQZS UJNRD VJYLG OPFME TIGPJ DQWIW";
//		String plain = enigmaMachine.translate(encrypted);
//		assertEquals("", plain);
//	}
}
