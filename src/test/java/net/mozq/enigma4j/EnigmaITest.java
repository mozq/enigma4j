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

class EnigmaITest {
	
	@Test void namedMachine() {
		assertEquals(Enigma.I.machine(), Enigma.machine("I"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.I.ETW, Enigma.I.spec().entryWheel("ETW"));
		assertEquals(Enigma.I.I, Enigma.I.spec().rotor("I"));
		assertEquals(Enigma.I.II, Enigma.I.spec().rotor("II"));
		assertEquals(Enigma.I.III, Enigma.I.spec().rotor("III"));
		assertEquals(Enigma.I.IV, Enigma.I.spec().rotor("IV"));
		assertEquals(Enigma.I.V, Enigma.I.spec().rotor("V"));
		assertEquals(Enigma.I.UKW_A, Enigma.I.spec().reflector("UKW-A"));
		assertEquals(Enigma.I.UKW_B, Enigma.I.spec().reflector("UKW-B"));
		assertEquals(Enigma.I.UKW_C, Enigma.I.spec().reflector("UKW-C"));
	}
	
	@Test void ukwd() {
		Reflector ukwdBP = Enigma.I.UKW_D_BP(WiringPair.toPairs("BO AK CT DV EP FN GL HM IJ QW RY SX UZ"));
		Reflector ukwd = Enigma.I.UKW_D(WiringPair.toPairs("AQ BG CK DI EL FX HZ MW NV OT PU RS"));
		
		assertEquals(new Reflector("UKW-D", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "KOTVPNLMJIAGHFBEWYXCZDQSRU").wiring(), ukwdBP.wiring());
		assertEquals(new Reflector("UKW-D", "A-ZXWVUTSRQPON-MLKIHGFEDCB", "Q-HFMNPORSAUTV-WECDZBXLIKG", null).wiring(), ukwd.wiring());
		
		for (int n = 1; n <= 26; n++) {
			assertEquals(ukwdBP.scrambleForward(n), ukwd.scrambleForward(n));
			assertEquals(ukwdBP.scrambleBackward(n), ukwd.scrambleBackward(n));
		}
	}
	
	@Test void translate_rotorA123() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_A)
				.rotor(3, Enigma.I.I.ring(1), 2)
				.rotor(2, Enigma.I.II.ring(2), 1)
				.rotor(1, Enigma.I.III.ring(3), 26);
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("UFRJOAHRTVBWC", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_rotorB345() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_B)
				.rotor(3, Enigma.I.III.ring(1), 2)
				.rotor(2, Enigma.I.IV.ring(2), 1)
				.rotor(1, Enigma.I.V.ring(3), 26);
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("AKLLOKRXOYYOC", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_rotorC512() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_C)
				.rotor(3, Enigma.I.V.ring(1), 2)
				.rotor(2, Enigma.I.I.ring(2), 1)
				.rotor(1, Enigma.I.II.ring(3), 26);
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("OLMMASTFPTJIM", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_UKW_D() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_D("AZ BC DE FG HI KL MN OP QR ST UV WX"))
				.rotor(3, Enigma.I.I.ring(1), 2)
				.rotor(2, Enigma.I.II.ring(2), 1)
				.rotor(1, Enigma.I.III.ring(3), 26);
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("YNVMSXWUAPJWZ", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_UKW_D_BP() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_D_BP("AC DE FG HI JK LM NP QR ST UV WX YZ"))
				.rotor(3, Enigma.I.I.ring(1), 2)
				.rotor(2, Enigma.I.II.ring(2), 1)
				.rotor(1, Enigma.I.III.ring(3), 26);
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("YNVMSXWUAPJWZ", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_Plugboard() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_A)
				.rotor(3, Enigma.I.I.ring(1), 2)
				.rotor(2, Enigma.I.II.ring(2), 1)
				.rotor(1, Enigma.I.III.ring(3), 26)
				.plugboard("AB CD EF GH IJ KL MN OP QR ST");
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("FFPBIWWATDBCG", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_Plugboard_Uhr0() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_A)
				.rotor(3, Enigma.I.I.ring(1), 2)
				.rotor(2, Enigma.I.II.ring(2), 1)
				.rotor(1, Enigma.I.III.ring(3), 26)
				.plugboard("AB CD EF GH IJ KL MN OP QR ST", 0);
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("FFPBIWWATDBCG", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_Plugboard_Uhr1() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_A)
				.rotor(3, Enigma.I.I.ring(1), 2)
				.rotor(2, Enigma.I.II.ring(2), 1)
				.rotor(1, Enigma.I.III.ring(3), 26)
				.plugboard("AB CD EF GH IJ KL MN OP QR ST", 1);
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("VVZKJGJNWQCUF", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_Plugboard_Uhr2() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_A)
				.rotor(3, Enigma.I.I.ring(1), 2)
				.rotor(2, Enigma.I.II.ring(2), 1)
				.rotor(1, Enigma.I.III.ring(3), 26)
				.plugboard("AB CD EF GH IJ KL MN OP QR ST", 2);
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("JBJSSLPBTLHTU", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_Plugboard_Uhr3() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_A)
				.rotor(3, Enigma.I.I.ring(1), 2)
				.rotor(2, Enigma.I.II.ring(2), 1)
				.rotor(1, Enigma.I.III.ring(3), 26)
				.plugboard("AB CD EF GH IJ KL MN OP QR ST", 3);
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("TURZLQGIRHRLM", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_Plugboard_Uhr4() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_A)
				.rotor(3, Enigma.I.I.ring(1), 2)
				.rotor(2, Enigma.I.II.ring(2), 1)
				.rotor(1, Enigma.I.III.ring(3), 26)
				.plugboard("AB CD EF GH IJ KL MN OP QR ST", 4);
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("LASXNKJUGCUCW", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	/**
	 * Enigma Instruction Manual, 1930
	 * See: http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
	 */
	@Test void translate_InstructionManual1930_Named() {
		EnigmaMachine enigmaMachine = Enigma.machine("I")
				.reflector("UKW-A")
				.rotor(3, "II", 24, 'A')
				.rotor(2, "I", 13, 'B')
				.rotor(1, "III", 22, 'L')
				.plugboard("AM FI NV PS TU WZ");
		
		String encrypted = "GCDSE AHUGW TQGRK VLFGX UCALX VYMIG MMNMF DXTGN VHVRM MEVOU YFZSL RHDRR XFJWC FHUHM UNZEF RDISI KBGPM YVXUZ";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("FEIND LIQEI NFANT ERIEK OLONN EBEOB AQTET XANFA NGSUE DAUSG ANGBA ERWAL DEXEN DEDRE IKMOS TWAER TSNEU STADT", plain);
	}
	
	/**
	 * Enigma Instruction Manual, 1930
	 * See: http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
	 */
	@Test void translate_InstructionManual1930() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_A)
				.rotor(3, Enigma.I.II.ring(24), 'A')
				.rotor(2, Enigma.I.I.ring(13), 'B')
				.rotor(1, Enigma.I.III.ring(22), 'L')
				.plugboard("AM FI NV PS TU WZ");
		
		String encrypted = "GCDSE AHUGW TQGRK VLFGX UCALX VYMIG MMNMF DXTGN VHVRM MEVOU YFZSL RHDRR XFJWC FHUHM UNZEF RDISI KBGPM YVXUZ";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("FEIND LIQEI NFANT ERIEK OLONN EBEOB AQTET XANFA NGSUE DAUSG ANGBA ERWAL DEXEN DEDRE IKMOS TWAER TSNEU STADT", plain);
	}
	
	/**
	 * Operation Barbarossa, 1941
	 * See: http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
	 */
	@Test void translate_OperationBarbarossa1941_BLA() {
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_B)
				.rotor(3, Enigma.I.II.ring(2), 'B')
				.rotor(2, Enigma.I.IV.ring(21), 'L')
				.rotor(1, Enigma.I.V.ring(12), 'A')
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
		EnigmaMachine enigmaMachine = Enigma.I.machine()
				.reflector(Enigma.I.UKW_B)
				.rotor(3, Enigma.I.II.ring(2), 'L')
				.rotor(2, Enigma.I.IV.ring(21), 'S')
				.rotor(1, Enigma.I.V.ring(12), 'D')
				.plugboard("AV BS CG DL FU HZ IN KM OW RX");
		
		String encrypted = "SFBWD NJUSE GQOBH KRTAR EEZMW KPPRB XOHDR OEQGB BGTQV PGVKB VVGBI MHUSZ YDAJQ IROAX SSSNR EHYGG RPISE ZBOVM QIEMM ZCYSG QDGRE RVBIL EKXYQ IRGIR QNRDN VRXCY YTNJR";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("DREIG EHTLA NGSAM ABERS IQERV ORWAE RTSXE INSSI EBENN ULLSE QSXUH RXROE MXEIN SXINF RGTXD REIXA UFFLI EGERS TRASZ EMITA NFANG XEINS SEQSX KMXKM XOSTW XKAME NECXK", plain);
	}
}
