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

class EnigmaM3Test {
	
	@Test void namedMachine() {
		assertEquals(Enigma.M3.machine(), Enigma.machine("M3"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.M3.ETW, Enigma.M3.spec().entryWheel("ETW"));
		assertEquals(Enigma.M3.I, Enigma.M3.spec().rotor("I"));
		assertEquals(Enigma.M3.II, Enigma.M3.spec().rotor("II"));
		assertEquals(Enigma.M3.III, Enigma.M3.spec().rotor("III"));
		assertEquals(Enigma.M3.IV, Enigma.M3.spec().rotor("IV"));
		assertEquals(Enigma.M3.V, Enigma.M3.spec().rotor("V"));
		assertEquals(Enigma.M3.VI, Enigma.M3.spec().rotor("VI"));
		assertEquals(Enigma.M3.VII, Enigma.M3.spec().rotor("VII"));
		assertEquals(Enigma.M3.VIII, Enigma.M3.spec().rotor("VIII"));
		assertEquals(Enigma.M3.UKW_B, Enigma.M3.spec().reflector("UKW-B"));
		assertEquals(Enigma.M3.UKW_C, Enigma.M3.spec().reflector("UKW-C"));
	}
	
	@Test void translate_rotorB123() {
		EnigmaMachine enigmaMachine = Enigma.M3.machine()
				.reflector(Enigma.M3.UKW_B)
				.rotor(3, Enigma.M3.I.ring('A'), 'B')
				.rotor(2, Enigma.M3.II.ring('B'), 'A')
				.rotor(1, Enigma.M3.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("TGLQUVSVJGUQI", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_rotorC456() {
		EnigmaMachine enigmaMachine = Enigma.M3.machine()
				.reflector(Enigma.M3.UKW_C)
				.rotor(3, Enigma.M3.IV.ring('A'), 'B')
				.rotor(2, Enigma.M3.V.ring('B'), 'A')
				.rotor(1, Enigma.M3.VI.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("EZKHYSGPVGIVH", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_rotorC781() {
		EnigmaMachine enigmaMachine = Enigma.M3.machine()
				.reflector(Enigma.M3.UKW_C)
				.rotor(3, Enigma.M3.VII.ring('A'), 'B')
				.rotor(2, Enigma.M3.VIII.ring('B'), 'A')
				.rotor(1, Enigma.M3.I.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("HVBMTMPYHAIBO", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_Plugboard() {
		EnigmaMachine enigmaMachine = Enigma.M3.machine()
				.reflector(Enigma.M3.UKW_B)
				.rotor(3, Enigma.M3.I.ring('A'), 'B')
				.rotor(2, Enigma.M3.II.ring('B'), 'A')
				.rotor(1, Enigma.M3.III.ring('C'), 'Z')
				.plugboard("AB CD EF GH IJ KL MN OP QR ST");
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("TKTQGRKXQIVMV", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	/**
	 * Operation Barbarossa, 1941
	 * See: http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
	 */
	@Test void translate_OperationBarbarossa1941_BLA() {
		EnigmaMachine enigmaMachine = Enigma.M3.machine()
				.reflector(Enigma.M3.UKW_B)
				.rotor(3, Enigma.M3.II.ring(2), 'B')
				.rotor(2, Enigma.M3.IV.ring(21), 'L')
				.rotor(1, Enigma.M3.V.ring(12), 'A')
				.plugboard("AV BS CG DL FU HZ IN KM OW RX");
		
		String encrypted = "EDPUD NRGYS ZRCXN UYTPO MRMBO FKTBZ REZKM LXLVE FGUEY SIOZV EQMIK UBPMM YLKLT TDEIS MDICA GYKUA CTCDO MOHWX MUUIA UBSTS LRNBZ SZWNR FXWFY SSXJZ VIJHI DISHP RKLKA YUPAD TXQSP INQMA TLPIF SVKDA SCTAC DPBOP VHJK";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("AUFKL XABTE ILUNG XVONX KURTI NOWAX KURTI NOWAX NORDW ESTLX SEBEZ XSEBE ZXUAF FLIEG ERSTR ASZER IQTUN GXDUB ROWKI XDUBR OWKIX OPOTS CHKAX OPOTS CHKAX UMXEI NSAQT DREIN ULLXU HRANG ETRET ENXAN GRIFF XINFX RGTX", plain);
	}
	
	/**
	 * Operation Barbarossa, 1941
	 * See: http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
	 */
	@Test void translate_OperationBarbarossa1941_LSD() {
		EnigmaMachine enigmaMachine = Enigma.M3.machine()
				.reflector(Enigma.M3.UKW_B)
				.rotor(3, Enigma.M3.II.ring(2), 'L')
				.rotor(2, Enigma.M3.IV.ring(21), 'S')
				.rotor(1, Enigma.M3.V.ring(12), 'D')
				.plugboard("AV BS CG DL FU HZ IN KM OW RX");
		
		String encrypted = "SFBWD NJUSE GQOBH KRTAR EEZMW KPPRB XOHDR OEQGB BGTQV PGVKB VVGBI MHUSZ YDAJQ IROAX SSSNR EHYGG RPISE ZBOVM QIEMM ZCYSG QDGRE RVBIL EKXYQ IRGIR QNRDN VRXCY YTNJR";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("DREIG EHTLA NGSAM ABERS IQERV ORWAE RTSXE INSSI EBENN ULLSE QSXUH RXROE MXEIN SXINF RGTXD REIXA UFFLI EGERS TRASZ EMITA NFANG XEINS SEQSX KMXKM XOSTW XKAME NECXK", plain);
	}

	/**
	 * Scharnhorst (Konteradmiral Erich Bey), 1943
	 * The third break of the M4 Project
	 * See: https://www.bytereef.org/m4-project-scharnhorst-break.html
	 * See: http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
	 */
	@Test void translate_Scharnhorst_1943() {
		EnigmaMachine enigmaMachine = Enigma.M3.machine()
				.reflector(Enigma.M3.UKW_B)
				.rotor(3, Enigma.M3.III.ring('A'), 'U')
				.rotor(2, Enigma.M3.VI.ring('H'), 'Z')
				.rotor(1, Enigma.M3.VIII.ring('M'), 'V')
				.plugboard("AN EZ HK IJ LR MQ OT PV SW UX");
		
		String encrypted = "YKAE NZAP MSCH ZBFO CUVM RMDP YCOF HADZ IZME FXTH FLOL PZLF GGBO TGOX GRET DWTJ IQHL MXVJ WKZU ASTR";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("STEU EREJ TANA FJOR DJAN STAN DORT QUAA ACCC VIER NEUN NEUN ZWOF AHRT ZWON ULSM XXSC HARN HORS THCO", plain);
	}

	/**
	 * PAGE_47_KRNV  (Swedish Enigma M3 intercept)
	 * See: https://enigma.hoerenberg.com/index.php?cat=Norrk%C3%B6ping%20messages&page=PAGE_47_KRNV
	 */
	@Test void translate_PAGE_47_KRNV() {
		EnigmaMachine enigmaMachine = Enigma.M3.machine()
				.reflector(Enigma.M3.UKW_B)
				.rotor(3, Enigma.M3.VII.ring('A'), 'R')
				.rotor(2, Enigma.M3.IV.ring('G'), 'T')
				.rotor(1, Enigma.M3.VI.ring('W'), 'A')
				.plugboard("BM DX EW GP JO KV NZ RT");
		
		String encrypted = "IWNOXNFTRJNPMRAWPZNKQECKKTWIOBEXLBHFLHHJLMYCZGCYSLCUDIJDLGQQPRYURGKGTFKRQEPIOVBI";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("SEENACHRICHTLEUCHTSCHIFFEMILSXBQPLANNORDFUENFNUOMERDREIEINSLIEGTWIEDERAUFSTATION", plain);
	}
}
