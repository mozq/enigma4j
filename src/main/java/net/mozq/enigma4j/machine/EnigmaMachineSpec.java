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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.mozq.enigma4j.scrambler.EntryWheel;
import net.mozq.enigma4j.scrambler.Reflector;
import net.mozq.enigma4j.scrambler.Rotor;

/**
 * Enigma machine specification.
 */
public class EnigmaMachineSpec {
	
	/** Basic rotor slot count */
	private static final int BASIC_ROTOR_SLOT_COUNT = 3;
	
	/** Machine name */
	private String name;
	
	/** Letters */
	private String letters;
	
	/** Defined entry wheel */
	private EntryWheel definedEntryWheel;
	
	/** Defined rotors */
	private List<Rotor> definedRotors;
	
	/** Defined fourth rotors */
	private List<Rotor> definedFourthRotors;
	
	/** Defined reflectors */
	private List<Reflector> definedReflectors;
	
	/** Turnover mechanism */
	private TurnoverMechanism turnoverMechanism;
	
	/** Features */
	private Set<EnigmaFeature> features;
	
	
	/** Rotor slot count */
	private int rotorSlotCount;
	
	/** Defined all entry wheel names */
	private List<String> definedAllEntryWheelNames;
	
	/** Defined all rotor names */
	private List<String> definedAllRotorNames;
	
	/** Defined all rotor map */
	private Map<String, Rotor> definedAllRotorMap;
	
	/** Defined reflector names */
	private List<String> definedReflectorNames;
	
	/** Defined reflector map */
	private Map<String, Reflector> definedReflectorMap;
	
	
	/**
	 * Constracts the Enigma machine specification.
	 * 
	 * @param name machine name
	 * @param letters letters
	 * @param definedEntryWheel defined entry wheel
	 * @param definedRotors defined rotors
	 * @param definedFourthRotors defined fourth rotors
	 * @param definedReflectors defined reflectors
	 * @param turnoverMechanism turnover mechanism
	 * @param features feature
	 */
	public EnigmaMachineSpec(String name, String letters, EntryWheel definedEntryWheel, List<Rotor> definedRotors, List<Rotor> definedFourthRotors, List<Reflector> definedReflectors, TurnoverMechanism turnoverMechanism, EnigmaFeature... features) {
		this.name = name;
		this.letters = letters;
		this.definedEntryWheel = definedEntryWheel;
		this.definedRotors = List.copyOf(definedRotors);
		this.definedFourthRotors = (definedFourthRotors == null) ? List.of() : List.copyOf(definedFourthRotors);
		this.definedReflectors = List.copyOf(definedReflectors);
		this.turnoverMechanism = turnoverMechanism;
		this.features = Set.of(features);
		
		this.rotorSlotCount = BASIC_ROTOR_SLOT_COUNT + ((this.definedFourthRotors.size() == 0) ? 0 : 1);
		
		this.definedAllEntryWheelNames = null;
		this.definedAllRotorNames = null;
		this.definedAllRotorMap = null;
		this.definedReflectorNames = null;
		this.definedReflectorMap = null;
	}
	
	/**
	 * Returns the machine name.
	 * 
	 * @return machine name
	 */
	public String name() {
		return this.name;
	}
	
	/**
	 * Returns the letters.
	 * 
	 * @return letters
	 */
	public String letters() {
		return this.letters;
	}
	
	/**
	 * Returns the rotor slot count.
	 * 
	 * @return rotor slot count
	 */
	public int rotorSlotCount() {
		return this.rotorSlotCount;
	}
	
	/**
	 * Returns whether the machine has a forth rotor.
	 * 
	 * @return true if the machine has a forth rotor
	 */
	public boolean isSupportFourthRotor() {
		return !this.definedFourthRotors.isEmpty();
	}
	
	/**
	 * Returns the turnover mechanism.
	 * 
	 * @return turnover mechanism
	 */
	public TurnoverMechanism turnoverMechanism() {
		return this.turnoverMechanism;
	}
	
	/**
	 * Returns the features.
	 * 
	 * @return features
	 */
	public Set<EnigmaFeature> features() {
		return this.features;
	}
	
	/**
	 * Returns whether the machine has the feature.
	 * 
	 * @param feature feature
	 * @return true if the machine has the option
	 */
	public boolean hasFeature(EnigmaFeature feature) {
		return this.features.contains(feature);
	}
	
	/**
	 * Returns the entry wheel names.
	 * 
	 * @return entry wheel names
	 */
	public List<String> entryWheelNames() {
		if (this.definedAllEntryWheelNames == null) {
			this.definedAllEntryWheelNames = List.of(this.definedEntryWheel.name());
		}
		
		return this.definedAllEntryWheelNames;
	}
	
	/**
	 * Returns the rotor names.
	 * 
	 * @return rotor names
	 */
	public List<String> rotorNames() {
		if (this.definedAllRotorNames == null) {
			this.definedAllRotorNames = Stream.concat(this.definedRotors.stream(), this.definedFourthRotors.stream())
					.map(v -> v.name()).collect(Collectors.toUnmodifiableList());
		}
		
		return this.definedAllRotorNames;
	}
	
	/**
	 * Returns the reflector names.
	 * 
	 * @return reflector names
	 */
	public List<String> reflectorNames() {
		if (this.definedReflectorNames == null) {
			this.definedReflectorNames = this.definedReflectors.stream()
					.map(v -> v.name()).collect(Collectors.toUnmodifiableList());
		}
		
		return this.definedReflectorNames;
	}
	
	/**
	 * Returns the entry wheel.
	 * 
	 * @param entryWheelName entry wheel name
	 * @return entry wheel
	 */
	public EntryWheel entryWheel(String entryWheelName) {
		if (this.definedEntryWheel.name().equals(entryWheelName)) {
			return this.definedEntryWheel;
		}
		
		return null;
	}
	
	/**
	 * Returns the default entry wheel.
	 * 
	 * @return default entry wheel
	 */
	public EntryWheel defaultEntryWheel() {
		return this.definedEntryWheel;
	}
	
	/**
	 * Returns the rotor.
	 * 
	 * @param rotorName rotor name
	 * @return rotor
	 */
	public Rotor rotor(String rotorName) {
		if (this.definedAllRotorMap == null) {
			this.definedAllRotorMap = Stream.concat(this.definedRotors.stream(), this.definedFourthRotors.stream())
					.collect(Collectors.toUnmodifiableMap(v -> v.name(), v -> v));
		}
		
		return this.definedAllRotorMap.get(rotorName);
	}
	
	/**
	 * Returns the reflector.
	 * 
	 * @param reflectorName reflector name
	 * @return reflector
	 */
	public Reflector reflector(String reflectorName) {
		if (this.definedReflectorMap == null) {
			this.definedReflectorMap = this.definedReflectors.stream()
					.collect(Collectors.toUnmodifiableMap(v -> v.name(), v -> v));
		}
		
		return this.definedReflectorMap.get(reflectorName);
	}
	
	/**
	 * Returns the default reflector.
	 * 
	 * @return default reflector
	 */
	public Reflector defaultReflector() {
		return this.definedReflectors.get(0);
	}
}
