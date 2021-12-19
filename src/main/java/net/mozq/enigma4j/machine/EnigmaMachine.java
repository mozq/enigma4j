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
package net.mozq.enigma4j.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import net.mozq.enigma4j.scrambler.EntryWheel;
import net.mozq.enigma4j.scrambler.Plugboard;
import net.mozq.enigma4j.scrambler.Reflector;
import net.mozq.enigma4j.scrambler.Rotor;
import net.mozq.enigma4j.scrambler.ScrambleTracker;
import net.mozq.enigma4j.scrambler.ScramblerChain;
import net.mozq.enigma4j.scrambler.WiringPair;

/**
 * Enigma machine.
 */
public class EnigmaMachine {
	
	/** Default scramble tracker */
	private static ScrambleTracker defaultTracker = null;
	
	/** Enigma machine specification */
	private EnigmaMachineSpec spec;
	
	/** Plugboard */
	private Plugboard plugboard;
	
	/** Entry wheel */
	private EntryWheel entryWheel;
	
	/** Rotors */
	private Rotor[] rotors;
	
	/** Rotor starting positions */
	private int[] rotorPositions;
	
	/** Reflector */
	private Reflector reflector;
	
	/** Reflector position */
	private int reflectorPosition;
	
	/** Scramble tracker */
	private ScrambleTracker tracker;
	
	/**
	 * Constracts with the Enigma machine specification.
	 * 
	 * @param spec Enigma machine specification
	 */
	public EnigmaMachine(EnigmaMachineSpec spec) {
		this.spec = spec;
		
		this.plugboard = null;
		this.entryWheel = spec.defaultEntryWheel();
		this.rotors = new Rotor[spec.rotorSlotCount()];
		this.rotorPositions = new int[spec.rotorSlotCount()];
		this.reflector = spec.defaultReflector();
		this.reflectorPosition = 1;
		this.tracker = defaultTracker;
	}
	
	/**
	 * Set the default scramble tracker.
	 * 
	 * @param tracker scramble tracker
	 */
	public static void setDefaultTracker(ScrambleTracker tracker) {
		defaultTracker = tracker;
	}
	
	/**
	 * Returns the machine name.
	 * 
	 * @return machine name
	 */
	public String name() {
		return this.spec.name();
	}
	
	/**
	 * Returns the Enigma machine specification.
	 * 
	 * @return Enigma machine specification
	 */
	public EnigmaMachineSpec spec() {
		return this.spec;
	}
	
	/**
	 * Returns the plugboard.
	 * 
	 * @return plugboard
	 */
	public Plugboard plugboard() {
		return this.plugboard;
	}
	
	/**
	 * Returns the entry wheel.
	 * 
	 * @return entry wheel
	 */
	public EntryWheel entryWheel() {
		return this.entryWheel;
	}
	
	/**
	 * Returns the rotors.
	 * 
	 * @return rotors
	 */
	public List<Rotor> rotors() {
		return List.of(this.rotors);
	}
	
	/**
	 * Returns the rotor positions.
	 * 
	 * @return rotor positions
	 */
	public List<Integer> rotorPositions() {
		int len = this.rotorPositions.length;
		List<Integer> list = new ArrayList<>(len);
		for (int i = 0; i < len; i++) {
			int position = this.rotorPositions[i];
			list.add(Integer.valueOf(position));
		}
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * Returns the rotor.
	 * 
	 * @param slotNo slot no
	 * @return rotor
	 */
	public Rotor rotor(int slotNo) {
		if (slotNo <= 0 || this.rotors.length < slotNo) {
			throw new IllegalArgumentException();
		}
		
		return this.rotors[slotNo - 1];
	}
	
	/**
	 * Returns the rotor position.
	 * 
	 * @param slotNo slot no
	 * @return rotor position
	 */
	public int rotorPosition(int slotNo) {
		if (slotNo <= 0 || this.rotors.length < slotNo) {
			throw new IllegalArgumentException();
		}
		
		return this.rotorPositions[slotNo - 1];
	}
	
	/**
	 * Returns the reflector.
	 * 
	 * @return reflector
	 */
	public Reflector reflector() {
		return this.reflector;
	}
	
	/**
	 * Returns the reflector position.
	 * 
	 * @return reflector position
	 */
	public int reflectorPosition() {
		return this.reflectorPosition;
	}
	
	/**
	 * Set the plugboard.
	 * 
	 * @param wiringPairs wiring pairs
	 * @return this instance
	 */
	public EnigmaMachine plugboard(String wiringPairs) {
		return plugboard(WiringPair.toPairs(wiringPairs));
	}
	
	/**
	 * Set the plugboard.
	 * 
	 * @param wiringPairs wiring pairs
	 * @return this instance
	 */
	public EnigmaMachine plugboard(String[] wiringPairs) {
		return plugboard(WiringPair.toPairs(wiringPairs));
	}
	
	/**
	 * Set the plugboard.
	 * 
	 * @param wiringPairs wiring pairs
	 * @return this instance
	 */
	public EnigmaMachine plugboard(List<WiringPair> wiringPairs) {
		this.plugboard = new Plugboard("Plugboard", this.spec().letters(), wiringPairs);
		return this;
	}
	
	/**
	 * Set the plugboard with Uhr.
	 * 
	 * @param wiringPairs wiring pairs
	 * @param uhrSetting Uhr setting (0 to 39)
	 * @return this instance
	 */
	public EnigmaMachine plugboard(String wiringPairs, int uhrSetting) {
		return plugboard(WiringPair.toPairs(wiringPairs), uhrSetting);
	}
	
	/**
	 * Set the plugboard with Uhr.
	 * 
	 * @param wiringPairs wiring pairs
	 * @param uhrSetting Uhr setting (0 to 39)
	 * @return this instance
	 */
	public EnigmaMachine plugboard(String[] wiringPairs, int uhrSetting) {
		return plugboard(WiringPair.toPairs(wiringPairs), uhrSetting);
	}
	
	/**
	 * Set the plugboard with Uhr.
	 * 
	 * @param wiringPairs wiring pairs
	 * @param uhrSetting Uhr setting (0 to 39)
	 * @return this instance
	 */
	public EnigmaMachine plugboard(List<WiringPair> wiringPairs, int uhrSetting) {
		this.plugboard = new Plugboard("Plugboard", this.spec().letters(), wiringPairs, uhrSetting);
		return this;
	}
	
	/**
	 * Set the entry wheel.
	 * 
	 * @param entryWheel entry wheel
	 * @return this instance
	 */
	public EnigmaMachine entryWheel(EntryWheel entryWheel) {
		this.entryWheel = entryWheel;
		return this;
	}
	
	/**
	 * Set the entry wheel.
	 * 
	 * @param entryWheelName entry wheel name
	 * @return this instance
	 */
	public EnigmaMachine entryWheel(String entryWheelName) {
		EntryWheel entryWheel = this.spec.entryWheel(entryWheelName);
		if (entryWheel == null) {
			throw new IllegalArgumentException("Unknown entry wheel. entryWheelName: " + entryWheelName);
		}
		
		return entryWheel(entryWheel);
	}
	
	/**
	 * Set the rotor.
	 * 
	 * @param slotNo slot no
	 * @param rotor rotor
	 * @return this instance
	 */
	public EnigmaMachine rotor(int slotNo, Rotor rotor) {
		return rotor(slotNo, rotor, 1);
	}
	
	/**
	 * Set the rotor.
	 * 
	 * @param slotNo slot no
	 * @param rotor rotor
	 * @param positionLetter position letter
	 * @return this instance
	 */
	public EnigmaMachine rotor(int slotNo, Rotor rotor, char positionLetter) {
		return rotor(slotNo, rotor, rotor.letterNumberOf(positionLetter));
	}
	
	/**
	 * Set the rotor.
	 * 
	 * @param slotNo slot no
	 * @param rotor rotor
	 * @param position position
	 * @return this instance
	 */
	public EnigmaMachine rotor(int slotNo, Rotor rotor, int position) {
		if (slotNo <= 0 || this.rotors.length < slotNo) {
			throw new IllegalArgumentException("Slot no is out of range. slotNo: " + slotNo);
		}
		
		if (position <= 0 || rotor.size() < position) {
			throw new IllegalArgumentException("Position is out of range. position: " + position);
		}
		
		this.rotors[slotNo - 1] = rotor;
		this.rotorPositions[slotNo - 1] = position;
		return this;
	}
	
	/**
	 * Set the rotor.
	 * 
	 * @param slotNo slot no
	 * @param rotorName rotor name
	 * @param positionLetter position letter
	 * @return this instance
	 */
	public EnigmaMachine rotor(int slotNo, String rotorName, char positionLetter) {
		return rotor(slotNo, rotorName, 1, positionLetter);
	}
	
	/**
	 * Set the rotor.
	 * 
	 * @param slotNo slot no
	 * @param rotorName rotor name
	 * @param position position
	 * @return this instance
	 */
	public EnigmaMachine rotor(int slotNo, String rotorName, int position) {
		return rotor(slotNo, rotorName, 1, position);
	}
	
	/**
	 * Set the rotor.
	 * 
	 * @param slotNo slot no
	 * @param rotorName rotor name
	 * @param ringSettingLetter ring setting letter
	 * @param positionLetter position letter
	 * @return this instance
	 */
	public EnigmaMachine rotor(int slotNo, String rotorName, char ringSettingLetter, char positionLetter) {
		Rotor rotor = this.spec.rotor(rotorName);
		if (rotor == null) {
			throw new IllegalArgumentException("Unknown rotor. rotorName: " + rotorName);
		}
		
		return rotor(slotNo, rotor.ring(ringSettingLetter), positionLetter);
	}
	
	/**
	 * Set the rotor.
	 * 
	 * @param slotNo slot no
	 * @param rotorName rotor name
	 * @param ringSettingLetter ring setting letter
	 * @param position position
	 * @return this instance
	 */
	public EnigmaMachine rotor(int slotNo, String rotorName, char ringSettingLetter, int position) {
		Rotor rotor = this.spec.rotor(rotorName);
		if (rotor == null) {
			throw new IllegalArgumentException("Unknown rotor. rotorName: " + rotorName);
		}
		
		return rotor(slotNo, rotor.ring(ringSettingLetter), position);
	}
	
	/**
	 * Set the rotor.
	 * 
	 * @param slotNo slot no
	 * @param rotorName rotor name
	 * @param ringSetting ring setting
	 * @param positionLetter position letter
	 * @return this instance
	 */
	public EnigmaMachine rotor(int slotNo, String rotorName, int ringSetting, char positionLetter) {
		Rotor rotor = this.spec.rotor(rotorName);
		if (rotor == null) {
			throw new IllegalArgumentException("Unknown rotor. rotorName: " + rotorName);
		}
		
		return rotor(slotNo, rotor.ring(ringSetting), positionLetter);
	}
	
	/**
	 * Set the rotor.
	 * 
	 * @param slotNo slot no
	 * @param rotorName rotor name
	 * @param ringSetting ring setting
	 * @param position position
	 * @return this instance
	 */
	public EnigmaMachine rotor(int slotNo, String rotorName, int ringSetting, int position) {
		Rotor rotor = this.spec.rotor(rotorName);
		if (rotor == null) {
			throw new IllegalArgumentException("Unknown rotor. rotorName: " + rotorName);
		}
		
		return rotor(slotNo, rotor.ring(ringSetting), position);
	}
	
	/**
	 * Set the reflector.
	 * 
	 * @param reflector reflector
	 * @return this instance
	 */
	public EnigmaMachine reflector(Reflector reflector) {
		return reflector(reflector, 1);
	}
	
	/**
	 * Set the reflector.
	 * 
	 * @param reflector reflector
	 * @param positionLetter position letter
	 * @return this instance
	 */
	public EnigmaMachine reflector(Reflector reflector, char positionLetter) {
		return reflector(reflector, reflector.letterNumberOf(positionLetter));
	}
	
	/**
	 * Set the reflector.
	 * 
	 * @param reflector reflector
	 * @param position position
	 * @return this instance
	 */
	public EnigmaMachine reflector(Reflector reflector, int position) {
		if (position <= 0 || reflector.size() < position) {
			throw new IllegalArgumentException("Position is out of range. position: " + position);
		}
		
		this.reflector = reflector;
		this.reflectorPosition = position;
		return this;
	}
	
	/**
	 * Set the reflector.
	 * 
	 * @param reflectorName reflector name
	 * @return this instance
	 */
	public EnigmaMachine reflector(String reflectorName) {
		return reflector(reflectorName, 1);
	}
	
	/**
	 * Set the reflector.
	 * 
	 * @param reflectorName reflector name
	 * @param positionLetter position letter
	 * @return this instance
	 */
	public EnigmaMachine reflector(String reflectorName, char positionLetter) {
		return reflector(reflectorName, 1, positionLetter);
	}
	
	/**
	 * Set the reflector.
	 * 
	 * @param reflectorName reflector name
	 * @param position position
	 * @return this instance
	 */
	public EnigmaMachine reflector(String reflectorName, int position) {
		return reflector(reflectorName, 1, position);
	}
	
	/**
	 * Set the reflector.
	 * 
	 * @param reflectorName reflector name
	 * @param ringSettingLetter ring setting letter
	 * @param positionLetter position letter
	 * @return this instance
	 */
	public EnigmaMachine reflector(String reflectorName, char ringSettingLetter, char positionLetter) {
		Reflector reflector = this.spec.reflector(reflectorName);
		if (reflector == null) {
			throw new IllegalArgumentException("Unknown reflector. reflectorName: " + reflectorName);
		}
		
		return reflector(reflector.ring(ringSettingLetter), positionLetter);
	}
	
	/**
	 * Set the reflector.
	 * 
	 * @param reflectorName reflector name
	 * @param ringSettingLetter ring setting letter
	 * @param position position
	 * @return this instance
	 */
	public EnigmaMachine reflector(String reflectorName, char ringSettingLetter, int position) {
		Reflector reflector = this.spec.reflector(reflectorName);
		if (reflector == null) {
			throw new IllegalArgumentException("Unknown reflector. reflectorName: " + reflectorName);
		}
		
		return reflector(reflector.ring(ringSettingLetter), position);
	}
	
	/**
	 * Set the reflector.
	 * 
	 * @param reflectorName reflector name
	 * @param ringSetting ring setting
	 * @param positionLetter position letter
	 * @return this instance
	 */
	public EnigmaMachine reflector(String reflectorName, int ringSetting, char positionLetter) {
		Reflector reflector = this.spec.reflector(reflectorName);
		if (reflector == null) {
			throw new IllegalArgumentException("Unknown reflector. reflectorName: " + reflectorName);
		}
		
		return reflector(reflector.ring(ringSetting), positionLetter);
	}
	
	/**
	 * Set the reflector.
	 * 
	 * @param reflectorName reflector name
	 * @param ringSetting ring setting
	 * @param position position
	 * @return this instance
	 */
	public EnigmaMachine reflector(String reflectorName, int ringSetting, int position) {
		Reflector reflector = this.spec.reflector(reflectorName);
		if (reflector == null) {
			throw new IllegalArgumentException("Unknown reflector. reflectorName: " + reflectorName);
		}
		
		return reflector(reflector.ring(ringSetting), position);
	}
	
	/**
	 * Set the scramble tracker.
	 * 
	 * @param tracker scramble tracker.
	 * @return this instance
	 */
	public EnigmaMachine tracker(ScrambleTracker tracker) {
		this.tracker = tracker;
		return this;
	}
	
	/**
	 * Translates the text value.
	 * 
	 * @param value text value
	 * @return translated value
	 * @see #translate(String, UnsupportedLetterHandling)
	 */
	public String translate(String value) {
		return translate(value, UnsupportedLetterHandling.PATH_THROUGH);
	}
	
	/**
	 * Translates the text value.
	 * 
	 * If it is a plain value, the encrypted value will be returned.
	 * If it is an encrypted value, the decrypted value will be returned.
	 * 
	 * The case of letters is preserved.
	 * 
	 * @param value text value
	 * @param unsupportedLetterHandling unsupported letter handling
	 * @return translated value
	 * @throws UnsupportedLetterException thrown when unsupportedLetterHandling is set EXCEPTION and value contains unsupported letters.
	 */
	public String translate(String value, UnsupportedLetterHandling unsupportedLetterHandling) {
		ScramblerChain chain = null;
		if (this.reflector != null) {
			chain = new ScramblerChain(this, this.reflector, this.reflectorPosition, chain);
		}
		for (int i = this.rotors.length - 1; 0 <= i; i--) {
			if (this.rotors[i] != null) {
				chain = new ScramblerChain(this, this.rotors[i], this.rotorPositions[i], chain);
			}
		}
		chain = new ScramblerChain(this, this.entryWheel, chain);
		if (this.plugboard != null) {
			chain = new ScramblerChain(this, this.plugboard, chain);
		}
		if (this.tracker != null) {
			chain.setTrackerAll(this.tracker);
		}
		
		int len = value.length();
		
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char letter = value.charAt(i);
			char upperLetter = Character.toUpperCase(letter);
			
			int number = letterNumberOf(upperLetter, -1);
			if (number == -1) {
				switch (unsupportedLetterHandling) {
				case EXCEPTION: throw new UnsupportedLetterException(letter);
				case REMOVAL: continue;
				default /* Path through */: sb.append(letter); continue;
				}
			}
			
			chain.turn(this.spec.turnoverMechanism());
			number = chain.scramble(number);
			
			char scrambledLetter = letterOf(number);
			
			if (letter != upperLetter) {
				// Original letter is a lower case
				
				// To lower case
				scrambledLetter = Character.toLowerCase(scrambledLetter);
			}
			
			sb.append(scrambledLetter);
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns the letter number of the letter.
	 * 
	 * @param letter letter
	 * @return letter number
	 * @throws IllegalArgumentException If the letter is not found
	 */
	public int letterNumberOf(char letter) {
		int letterNumber = letterNumberOf(letter, -1);
		if (letterNumber == -1) {
			throw new IllegalArgumentException("Unknown letter. letter: " + letter);
		}
		
		return letterNumber;
	}
	
	/**
	 * Returns the letter number of the letter.
	 * If the letter is not found, return the default value.
	 * 
	 * @param letter letter
	 * @param defaultValue default value
	 * @return letter number
	 */
	public int letterNumberOf(char letter, int defaultValue) {
		int idx = this.spec().letters().indexOf(letter);
		if (idx == -1) {
			return defaultValue;
		}
		
		return idx + 1;
	}
	
	/**
	 * Returns the letter of the letter number.
	 * 
	 * @param letterNumber letter number
	 * @return letter
	 * @throws IllegalArgumentException If the letter number is out of range
	 */
	public char letterOf(int letterNumber) {
		if (letterNumber <= 0 || this.spec().letters().length() < letterNumber) {
			throw new IllegalArgumentException("Letter number is out of range. letterNumber: " + letterNumber);
		}
		
		return this.spec().letters().charAt(letterNumber - 1);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (obj.getClass() != getClass()) {
			return false;
		}
		
		EnigmaMachine o = (EnigmaMachine)obj;
		return Objects.equals(o.spec, this.spec) &&
				Objects.equals(o.entryWheel, this.entryWheel) &&
				Arrays.equals(o.rotors, this.rotors) &&
				Arrays.equals(o.rotorPositions, this.rotorPositions) &&
				Objects.equals(o.reflector, this.reflector) &&
				Objects.equals(o.tracker, this.tracker);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + Objects.hashCode(this.spec);
		result = prime * result + Objects.hashCode(this.entryWheel);
		result = prime * result + Arrays.hashCode(this.rotors);
		result = prime * result + Arrays.hashCode(this.rotorPositions);
		result = prime * result + Objects.hashCode(this.reflector);
		result = prime * result + Objects.hashCode(this.tracker);
		return result;
	}
}
