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
package net.mozq.enigma4j.machine.model;

import java.util.List;

import net.mozq.enigma4j.machine.EnigmaMachine;
import net.mozq.enigma4j.machine.EnigmaMachineModel;
import net.mozq.enigma4j.machine.EnigmaMachineSpec;
import net.mozq.enigma4j.machine.EnigmaFeature;
import net.mozq.enigma4j.machine.TurnoverMechanism;
import net.mozq.enigma4j.scrambler.EntryWheel;
import net.mozq.enigma4j.scrambler.Reflector;
import net.mozq.enigma4j.scrambler.Rotor;
import net.mozq.enigma4j.scrambler.WiringPair;

/**
 * Enigma M4
 * U-boat Enigma
 */
public class EnigmaM4 extends EnigmaMachineModel {
	
	/** Letters */
	private static final String LETTERS = LETTERS_26;
	
	/** ETW */
	public static final EntryWheel ETW = new EntryWheel("ETW", LETTERS, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	
	/** Rotor I */
	public static final Rotor I = new Rotor("I", LETTERS, "EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q");
	
	/** Rotor II */
	public static final Rotor II = new Rotor("II", LETTERS, "AJDKSIRUXBLHWTMCQGZNPYFVOE", "E");
	
	/** Rotor III */
	public static final Rotor III = new Rotor("III", LETTERS, "BDFHJLCPRTXVZNYEIWGAKMUSQO", "V");
	
	/** Rotor IV */
	public static final Rotor IV = new Rotor("IV", LETTERS, "ESOVPZJAYQUIRHXLNFTGKDCMWB", "J");
	
	/** Rotor V */
	public static final Rotor V = new Rotor("V", LETTERS, "VZBRGITYUPSDNHLXAWMJQOFECK", "Z");
	
	/** Rotor VI */
	public static final Rotor VI = new Rotor("VI", LETTERS, "JPGVOUMFYQBENHZRDKASXLICTW", "ZM");
	
	/** Rotor VII */
	public static final Rotor VII = new Rotor("VII", LETTERS, "NZJHGRCXMYSWBOUFAIVLPEKQDT", "ZM");
	
	/** Rotor VIII */
	public static final Rotor VIII = new Rotor("VIII", LETTERS, "FKQHTLXOCBJSPDZRAMEWNIUYGV", "ZM");
	
	/** Rotor Beta (thin). In the original specifications, this rotor can only be used as a fourth rotor. */
	public static final Rotor BETA = new Rotor("Beta", LETTERS, "LEYJVCNIXWPBQMDRTAKZGFUHOS");
	
	/** Rotor Gamma (thin). In the original specifications, this rotor can only be used as a fourth rotor. */
	public static final Rotor GAMMA = new Rotor("Gamma", LETTERS, "FSOKANUERHMBTIYCWLQPZXVGJD");
	
	/** Reflector UKW-B (thin) */
	public static final Reflector UKW_B = new Reflector("UKW-B", LETTERS, "ENKQAUYWJICOPBLMDXZVFTHRGS");
	
	/** Reflector UKW-C (thin) */
	public static final Reflector UKW_C = new Reflector("UKW-C", LETTERS, "RDOBJNTKVEHMLFCWZAXGYIPSUQ");
	
	/** Specification */
	private static final EnigmaMachineSpec SPEC = new EnigmaMachineSpec(
			"M4",
			LETTERS,
			ETW,
			List.of(I, II, III, IV, V, VI, VII, VIII),
			List.of(BETA, GAMMA),
			List.of(UKW_B, UKW_C),
			TurnoverMechanism.REGULAR,
			EnigmaFeature.PLUGBOARD,
			EnigmaFeature.UKW_D
			);
	
	/**
	 * Constracts.
	 */
	protected EnigmaM4() {
		// NOP
	}
	
	/**
	 * UKW-D (Umkehrwalze D).
	 * Rewirable Enigma reflector.
	 * 
	 * UKW-D uses its own notation instead of the usual ring letter notation (Bletchley Park notation, BP notation).
	 *   - BP notation:    ABCDEFGHIJKLMNOPQRSTUVWXYZ
	 *   - UKW-D notation: A-ZXWVUTSRQPON-MLKIHGFEDCB
	 * 
	 * Please note that the '-' pair ('J' and 'Y'), 'B' and 'O' pair in BP notation, is fixed and cannot be changed.
	 * 
	 * @param wiringPairs wiring pairs
	 * @return UKW-D reflector
	 * @see #UKW_D_BP(String)
	 */
	public static Reflector UKW_D(String wiringPairs) {
		return Reflector.UKW_D(wiringPairs);
	}
	
	/**
	 * UKW-D (Umkehrwalze D).
	 * Rewirable Enigma reflector.
	 * 
	 * UKW-D uses its own notation instead of the usual ring letter notation (Bletchley Park notation, BP notation).
	 *   - BP notation:    ABCDEFGHIJKLMNOPQRSTUVWXYZ
	 *   - UKW-D notation: A-ZXWVUTSRQPON-MLKIHGFEDCB
	 * 
	 * Please note that the '-' pair ('J' and 'Y'), 'B' and 'O' pair in BP notation, is fixed and cannot be changed.
	 * 
	 * @param wiringPairs wiring pairs
	 * @return UKW-D reflector
	 * @see #UKW_D_BP(String[])
	 */
	public static Reflector UKW_D(String[] wiringPairs) {
		return Reflector.UKW_D(wiringPairs);
	}
	
	/**
	 * UKW-D (Umkehrwalze D).
	 * Rewirable Enigma reflector.
	 * 
	 * UKW-D uses its own notation instead of the usual ring letter notation (Bletchley Park notation, BP notation).
	 *   - BP notation:    ABCDEFGHIJKLMNOPQRSTUVWXYZ
	 *   - UKW-D notation: A-ZXWVUTSRQPON-MLKIHGFEDCB
	 * 
	 * Please note that the '-' pair ('J' and 'Y'), 'B' and 'O' pair in BP notation, is fixed and cannot be changed.
	 * 
	 * @param wiringPairs wiring pairs
	 * @return UKW-D reflector
	 * @see #UKW_D_BP(List)
	 */
	public static Reflector UKW_D(List<WiringPair> wiringPairs) {
		return Reflector.UKW_D(wiringPairs);
	}
	
	/**
	 * UKW-D (Umkehrwalze D).
	 * Rewirable Enigma reflector.
	 * 
	 * This is UKW-D that adopted Bletchley Park notation (BP notation).
	 * This is not the original UKW-D notation, but it is provided in case you want to unify it with other reflector notations.
	 * Please use {@link #UKW_D(String)} when using the original UKW-D notation.
	 * 
	 * Please note that the 'B' and 'O' pair is fixed and cannot be changed.
	 * 
	 * @param wiringPairs wiring pairs
	 * @return UKW-D reflector
	 * @see #UKW_D(String)
	 */
	public static Reflector UKW_D_BP(String wiringPairs) {
		return Reflector.UKW_D_BP(wiringPairs);
	}
	
	/**
	 * UKW-D (Umkehrwalze D).
	 * Rewirable Enigma reflector.
	 * 
	 * This is UKW-D that adopted Bletchley Park notation (BP notation).
	 * This is not the original UKW-D notation, but it is provided in case you want to unify it with other reflector notations.
	 * Please use {@link #UKW_D(String[])} when using the original UKW-D notation.
	 * 
	 * Please note that the 'B' and 'O' pair is fixed and cannot be changed.
	 * 
	 * @param wiringPairs wiring pairs
	 * @return UKW-D reflector
	 * @see #UKW_D(String[])
	 */
	public static Reflector UKW_D_BP(String[] wiringPairs) {
		return Reflector.UKW_D_BP(wiringPairs);
	}
	
	/**
	 * UKW-D (Umkehrwalze D).
	 * Rewirable Enigma reflector.
	 * 
	 * This is UKW-D that adopted Bletchley Park notation (BP notation).
	 * This is not the original UKW-D notation, but it is provided in case you want to unify it with other reflector notations.
	 * Please use {@link #UKW_D(List)} when using the original UKW-D notation.
	 * 
	 * Please note that the 'B' and 'O' pair is fixed and cannot be changed.
	 * 
	 * @param wiringPairs wiring pairs
	 * @return UKW-D reflector
	 * @see #UKW_D(List)
	 */
	public static Reflector UKW_D_BP(List<WiringPair> wiringPairs) {
		return Reflector.UKW_D_BP(wiringPairs);
	}
	
	/**
	 * Returns the machine specification.
	 * 
	 * @return the machine specification
	 */
	public static EnigmaMachineSpec spec() {
		return SPEC;
	}
	
	/**
	 * Creates and returns new Enigma machine instance.
	 * 
	 * @return new Enigma machine instance
	 */
	public static EnigmaMachine machine() {
		return new EnigmaMachine(spec());
	}
}
