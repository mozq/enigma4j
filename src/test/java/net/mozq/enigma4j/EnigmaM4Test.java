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

class EnigmaM4Test {
	
	@Test void namedMachine() {
		assertEquals(Enigma.M4.machine(), Enigma.machine("M4"));
	}
	
	@Test void namedScramblers() {
		assertEquals(Enigma.M4.ETW, Enigma.M4.spec().entryWheel("ETW"));
		assertEquals(Enigma.M4.I, Enigma.M4.spec().rotor("I"));
		assertEquals(Enigma.M4.II, Enigma.M4.spec().rotor("II"));
		assertEquals(Enigma.M4.III, Enigma.M4.spec().rotor("III"));
		assertEquals(Enigma.M4.IV, Enigma.M4.spec().rotor("IV"));
		assertEquals(Enigma.M4.V, Enigma.M4.spec().rotor("V"));
		assertEquals(Enigma.M4.VI, Enigma.M4.spec().rotor("VI"));
		assertEquals(Enigma.M4.VII, Enigma.M4.spec().rotor("VII"));
		assertEquals(Enigma.M4.VIII, Enigma.M4.spec().rotor("VIII"));
		assertEquals(Enigma.M4.BETA, Enigma.M4.spec().rotor("Beta"));
		assertEquals(Enigma.M4.GAMMA, Enigma.M4.spec().rotor("Gamma"));
		assertEquals(Enigma.M4.UKW_B, Enigma.M4.spec().reflector("UKW-B"));
		assertEquals(Enigma.M4.UKW_C, Enigma.M4.spec().reflector("UKW-C"));
	}
	
	@Test void ukwd() {
		Reflector ukwdBP = Enigma.M4.UKW_D_BP(WiringPair.toPairs("BO AK CT DV EP FN GL HM IJ QW RY SX UZ"));
		Reflector ukwd = Enigma.M4.UKW_D(WiringPair.toPairs("AQ BG CK DI EL FX HZ MW NV OT PU RS"));
		
		assertEquals(new Reflector("UKW-D", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "KOTVPNLMJIAGHFBEWYXCZDQSRU").wiring(), ukwdBP.wiring());
		assertEquals(new Reflector("UKW-D", "A-ZXWVUTSRQPON-MLKIHGFEDCB", "Q-HFMNPORSAUTV-WECDZBXLIKG", null).wiring(), ukwd.wiring());
		
		for (int n = 1; n <= 26; n++) {
			assertEquals(ukwdBP.scrambleForward(n), ukwd.scrambleForward(n));
			assertEquals(ukwdBP.scrambleBackward(n), ukwd.scrambleBackward(n));
		}
	}
	
	@Test void translate_rotorBB123() {
		EnigmaMachine enigmaMachine = Enigma.M4.machine()
				.reflector(Enigma.M4.UKW_B)
				.rotor(4, Enigma.M4.BETA.ring('Z'), 'C')
				.rotor(3, Enigma.M4.I.ring('A'), 'B')
				.rotor(2, Enigma.M4.II.ring('B'), 'A')
				.rotor(1, Enigma.M4.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("CRWKFAHKYZQPU", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_UKW_D() {
		EnigmaMachine enigmaMachine = Enigma.M4.machine()
				.reflector(Enigma.M4.UKW_D("AZ BC DE FG HI KL MN OP QR ST UV WX"))
				.rotor(3, Enigma.M4.I.ring('A'), 'B')
				.rotor(2, Enigma.M4.II.ring('B'), 'A')
				.rotor(1, Enigma.M4.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("YNVMSXWUAPJWZ", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_UKW_D_BP() {
		EnigmaMachine enigmaMachine = Enigma.M4.machine()
				.reflector(Enigma.M4.UKW_D_BP("AC DE FG HI JK LM NP QR ST UV WX YZ"))
				.rotor(3, Enigma.M4.I.ring('A'), 'B')
				.rotor(2, Enigma.M4.II.ring('B'), 'A')
				.rotor(1, Enigma.M4.III.ring('C'), 'Z');
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("YNVMSXWUAPJWZ", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	@Test void translate_Plugboard() {
		EnigmaMachine enigmaMachine = Enigma.M4.machine()
				.reflector(Enigma.M4.UKW_B)
				.rotor(4, Enigma.M4.BETA.ring('Z'), 'C')
				.rotor(3, Enigma.M4.I.ring('A'), 'B')
				.rotor(2, Enigma.M4.II.ring('B'), 'A')
				.rotor(1, Enigma.M4.III.ring('C'), 'Z')
				.plugboard("AB CD EF GH IJ KL MN OP QR ST");
		
		String plain = "SECRETMESSAGE";
		String encrypted = enigmaMachine.translate(plain);
		assertEquals("MGKOFDYMAPQAX", encrypted);
		String decrypted = enigmaMachine.translate(encrypted);
		assertEquals(plain, decrypted);
	}
	
	/**
	 * U-264 (Kapitänleutnant Hartwig Looks), 1942
	 * The first break of the M4 Project
	 * See: https://www.bytereef.org/m4-project-first-break.html
	 * See: http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
	 */
	@Test void translate_U264_1942() {
		EnigmaMachine enigmaMachine = Enigma.M4.machine()
				.reflector(Enigma.M4.UKW_B)
				.rotor(4, Enigma.M4.BETA.ring('A'), 'V')
				.rotor(3, Enigma.M4.II.ring('A'), 'J')
				.rotor(2, Enigma.M4.IV.ring('A'), 'N')
				.rotor(1, Enigma.M4.I.ring('V'), 'A')
				.plugboard("AT BL DF GJ HM NW OP QY RZ VX");
		
		String encrypted = "NCZW VUSX PNYM INHZ XMQX SFWX WLKJ AHSH NMCO CCAK UQPM KCSM HKSE INJU SBLK IOSX CKUB HMLL XCSJ USRR DVKO HULX WCCB GVLI YXEO AHXR HKKF VDRE WEZL XOBA FGYU JQUK GRTV UKAM EURB VEKS UHHV OYHA BCJW MAKL FKLM YFVN RIZR VVRT KOFD ANJM OLBG FFLE OPRG TFLV RHOW OPBE KVWM UQFM PWPA RMFH AGKX IIBG";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("VONV ONJL OOKS JHFF TTTE INSE INSD REIZ WOYY QNNS NEUN INHA LTXX BEIA NGRI FFUN TERW ASSE RGED RUEC KTYW ABOS XLET ZTER GEGN ERST ANDN ULAC HTDR EINU LUHR MARQ UANT ONJO TANE UNAC HTSE YHSD REIY ZWOZ WONU LGRA DYAC HTSM YSTO SSEN ACHX EKNS VIER MBFA ELLT YNNN NNNO OOVI ERYS ICHT EINS NULL", plain);
	}
	
	/**
	 * P1030660
	 * See: https://enigma.hoerenberg.com/index.php?cat=The%20U534%20messages&page=P1030660
	 */
	@Test void translate_P1030660() {
		EnigmaMachine enigmaMachine = Enigma.M4.machine()
				.reflector(Enigma.M4.UKW_C)
				.rotor(4, Enigma.M4.BETA.ring('A'), 'I')
				.rotor(3, Enigma.M4.V.ring('A'), 'G')
				.rotor(2, Enigma.M4.VI.ring('E'), 'Z')
				.rotor(1, Enigma.M4.VIII.ring('L'), 'Q')
				.plugboard("AE BF CM DQ HU JN LX PR SZ VW");
		
		String encrypted = "TWNHYAZGBILSHEWPGLBPQLWQEKITIAFGZHWIMCWDFXPAFEILQZWFNRFTTQHUOADVLRLGAOQKVLWLSJHWOFJJSLUVEYNRRAJAQDKQBGMFYCEVKPFJPKOWHHQZYZEQRTQIKKXIXTFPOEMI";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("FXDXUUUOSTYFUNCQUUUFXWTTXVVVUUUEINSEINSNULDREIKKEISELEKKXXISTSECHSSTUENDLICHESDOCKENVORMITTAGSAMDREIXFUNFXINRENDSBURGGEMXFXDXUUUOSTMOEGLICHL", plain);
	}
	
	/**
	 * P1030712
	 * See: https://enigma.hoerenberg.com/index.php?cat=The%20U534%20messages&page=P1030712
	 */
	@Test void translate_P1030712() {
		EnigmaMachine enigmaMachine = Enigma.M4.machine()
				.reflector(Enigma.M4.UKW_B)
				.rotor(4, Enigma.M4.GAMMA.ring('A'), 'F')
				.rotor(3, Enigma.M4.IV.ring('A'), 'U')
				.rotor(2, Enigma.M4.III.ring('C'), 'R')
				.rotor(1, Enigma.M4.VIII.ring('U'), 'O')
				.plugboard("CH EJ NV OU TY LG SZ PK DI QB");
		
		String encrypted = "EFLPBHCFKMRPFQYXAGEDUVASMXLYMBPRYTWSZMJCHZIVDJYBPMNHRAPDLZTABQHFIOSBQIBLRWOWAOVSCIIBZDDRENHDGKVPCZGUWMCO";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("XUUUFLOTTXVVVUUUFUNFDREIVIERKKNOLLAUKKEINSACHTUHRIMGELEITVVVZWONULEINSVIERVONROTNULEINSBISROTNULSIEBENFH", plain);
	}
	
	/**
	 * P1030713
	 * See: https://enigma.hoerenberg.com/index.php?cat=The%20U534%20messages&page=P1030713
	 */
	@Test void translate_P1030713() {
		EnigmaMachine enigmaMachine = Enigma.M4.machine()
				.reflector(Enigma.M4.UKW_C)
				.rotor(4, Enigma.M4.GAMMA.ring('A'), 'Z')
				.rotor(3, Enigma.M4.V.ring('A'), 'P')
				.rotor(2, Enigma.M4.II.ring('F'), 'E')
				.rotor(1, Enigma.M4.VIII.ring('B'), 'U')
				.plugboard("CP DG EJ FI KT LZ MS NO QU RW");
		
		String encrypted = "MPSANXAASRPBMXMNJMJYYPUGPFZOYAEQIEVWIOXHRVJECKAFVASOIELMFYBYGABXMYWOIVIMKGBA";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("LEITUNGVVVUUUSTUETZPKTXWWWHAVENXXFFFTTTEUNSZWOZWOVIERHUERMITVRRHHHVVVGELOEST", plain);
	}
	
	/**
	 * (Thin UKW+rotor compatibility test, Using Enigma I/M3 message)
	 * Operation Barbarossa, 1941
	 * See: http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
	 */
	@Test void translate_OperationBarbarossa1941_BLA() {
		EnigmaMachine enigmaMachine = Enigma.M4.machine()
				.reflector(Enigma.M4.UKW_B)
				.rotor(4, Enigma.M4.BETA)
				.rotor(3, Enigma.M4.II.ring(2), 'B')
				.rotor(2, Enigma.M4.IV.ring(21), 'L')
				.rotor(1, Enigma.M4.V.ring(12), 'A')
				.plugboard("AV BS CG DL FU HZ IN KM OW RX");
		
		String encrypted = "EDPUD NRGYS ZRCXN UYTPO MRMBO FKTBZ REZKM LXLVE FGUEY SIOZV EQMIK UBPMM YLKLT TDEIS MDICA GYKUA CTCDO MOHWX MUUIA UBSTS LRNBZ SZWNR FXWFY SSXJZ VIJHI DISHP RKLKA YUPAD TXQSP INQMA TLPIF SVKDA SCTAC DPBOP VHJK";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("AUFKL XABTE ILUNG XVONX KURTI NOWAX KURTI NOWAX NORDW ESTLX SEBEZ XSEBE ZXUAF FLIEG ERSTR ASZER IQTUN GXDUB ROWKI XDUBR OWKIX OPOTS CHKAX OPOTS CHKAX UMXEI NSAQT DREIN ULLXU HRANG ETRET ENXAN GRIFF XINFX RGTX", plain);
	}
	
	/**
	 * (Thin UKW+rotor compatibility test, Using Enigma I/M3 message)
	 * Operation Barbarossa, 1941
	 * See: http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
	 */
	@Test void translate_OperationBarbarossa1941_LSD() {
		EnigmaMachine enigmaMachine = Enigma.M4.machine()
				.reflector(Enigma.M4.UKW_B)
				.rotor(4, Enigma.M4.BETA)
				.rotor(3, Enigma.M4.II.ring(2), 'L')
				.rotor(2, Enigma.M4.IV.ring(21), 'S')
				.rotor(1, Enigma.M4.V.ring(12), 'D')
				.plugboard("AV BS CG DL FU HZ IN KM OW RX");
		
		String encrypted = "SFBWD NJUSE GQOBH KRTAR EEZMW KPPRB XOHDR OEQGB BGTQV PGVKB VVGBI MHUSZ YDAJQ IROAX SSSNR EHYGG RPISE ZBOVM QIEMM ZCYSG QDGRE RVBIL EKXYQ IRGIR QNRDN VRXCY YTNJR";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("DREIG EHTLA NGSAM ABERS IQERV ORWAE RTSXE INSSI EBENN ULLSE QSXUH RXROE MXEIN SXINF RGTXD REIXA UFFLI EGERS TRASZ EMITA NFANG XEINS SEQSX KMXKM XOSTW XKAME NECXK", plain);
	}

	/**
	 * (Thin UKW+rotor compatibility test, Using Enigma M3 message)
	 * Scharnhorst (Konteradmiral Erich Bey), 1943
	 * See: http://wiki.franklinheath.co.uk/index.php/Enigma/Sample_Messages
	 */
	@Test void translate_Scharnhorst_1943() {
		EnigmaMachine enigmaMachine = Enigma.M4.machine()
				.reflector(Enigma.M4.UKW_B)
				.rotor(4, Enigma.M4.BETA)
				.rotor(3, Enigma.M4.III.ring(1), 'U')
				.rotor(2, Enigma.M4.VI.ring(8), 'Z')
				.rotor(1, Enigma.M4.VIII.ring(13), 'V')
				.plugboard("AN EZ HK IJ LR MQ OT PV SW UX");
		
		String encrypted = "YKAE NZAP MSCH ZBFO CUVM RMDP YCOF HADZ IZME FXTH FLOL PZLF GGBO TGOX GRET DWTJ IQHL MXVJ WKZU ASTR";
		String plain = enigmaMachine.translate(encrypted);
		assertEquals("STEU EREJ TANA FJOR DJAN STAN DORT QUAA ACCC VIER NEUN NEUN ZWOF AHRT ZWON ULSM XXSC HARN HORS THCO", plain);
	}
}
