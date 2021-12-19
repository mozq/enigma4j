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

/**
 * Swiss-K
 * Swiss Enigma K variant
 */
public class EnigmaSwiss_K extends EnigmaMachineModel {
	
	/** Letters */
	private static final String LETTERS = LETTERS_26;
	
	/** ETW */
	public static final EntryWheel ETW = new EntryWheel("ETW", LETTERS, "JWULCMNOHPQZYXIRADKEGVBTSF"); // QWERTZUIOASDFGHJKPYXCVBNML -> ABCDEFGHIJKLMNOPQRSTUVWXYZ
	
	/** Rotor I */
	public static final Rotor I = new Rotor("I", LETTERS, "PEZUOHXSCVFMTBGLRINQJWAYDK", "Y"); // These wiring are not the original (physical). They were recovered cryptanalytically by the code­breakers at Bletchley Park (BP).
	
	/** Rotor II */
	public static final Rotor II = new Rotor("II", LETTERS, "ZOUESYDKFWPCIQXHMVBLGNJRAT", "E");
	
	/** Rotor III */
	public static final Rotor III = new Rotor("III", LETTERS, "EHRVXGAOBQUSIMZFLYNWKTPDJC", "N"); // These wiring are not the original (physical). They were recovered cryptanalytically by the code­breakers at Bletchley Park (BP).
	
	/** UKW */
	public static final Reflector UKW = new Reflector("UKW", LETTERS, "IMETCGFRAYSQBZXWLHKDVUPOJN");
	
	/** Specification */
	private static final EnigmaMachineSpec SPEC = new EnigmaMachineSpec(
			"Swiss-K",
			LETTERS,
			ETW,
			List.of(I, II, III),
			null,
			List.of(UKW),
			TurnoverMechanism.REGULAR,
			EnigmaFeature.SETTABLE_REFLECTOR
			);
	
	/**
	 * Constracts.
	 */
	protected EnigmaSwiss_K() {
		// NOP
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
