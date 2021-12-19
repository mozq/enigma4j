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
import net.mozq.enigma4j.machine.TurnoverMechanism;
import net.mozq.enigma4j.scrambler.EntryWheel;
import net.mozq.enigma4j.scrambler.Reflector;
import net.mozq.enigma4j.scrambler.Rotor;

/**
 * Enigma B - Mark II, Wiring of the A-133
 */
public class EnigmaA_133 extends EnigmaMachineModel {
	
	/** Letters */
	private static final String LETTERS = LETTERS_28;
	
	/** ETW */
	public static final EntryWheel ETW = new EntryWheel("ETW", LETTERS, "ABCDEFGHIJKLMNOPQRSTUVXYZÅÄÖ"); // The wiring has not yet been verified.
	
	/** Rotor I */
	public static final Rotor I = new Rotor("I", LETTERS, "PSBGÖXQJDHOÄUCFRTEZVÅINLYMKA", "Ä");
	
	/** Rotor II */
	public static final Rotor II = new Rotor("II", LETTERS, "CHNSYÖADMOTRZXBÄIGÅEKQUPFLVJ", "Ä");
	
	/** Rotor III */
	public static final Rotor III = new Rotor("III", LETTERS, "ÅVQIAÄXRJBÖZSPCFYUNTHDOMEKGL", "Ä");
	
	/** UKW */
	public static final Reflector UKW = new Reflector("UKW", LETTERS, "LDGBÄNCPSKJAVFZHXUIÅRMQÖOTEY");
	
	/** Specification */
	private static final EnigmaMachineSpec SPEC = new EnigmaMachineSpec(
			"A-133",
			LETTERS,
			ETW,
			List.of(I, II, III),
			null,
			List.of(UKW),
			TurnoverMechanism.REGULAR
			);
	
	/**
	 * Constracts.
	 */
	protected EnigmaA_133() {
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
