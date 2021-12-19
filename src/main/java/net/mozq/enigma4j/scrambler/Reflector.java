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

import java.util.ArrayList;
import java.util.List;

/**
 * Reflector (UKW; Umkehrwalze).
 */
public class Reflector extends RingWheel<Reflector> {
	
	/**
	 * Constructs with the same settings as the specified reflector.
	 * 
	 * @param reflector reflector
	 */
	protected Reflector(RingWheel<Reflector> reflector) {
		super(reflector);
	}
	
	/**
	 * Constructs with the specified wiring.
	 * 
	 * @param name name
	 * @param letters letters
	 * @param wiring wiring
	 */
	public Reflector(String name, String letters, String wiring) {
		this(name, letters, wiring, null);
	}
	
	/**
	 * Constructs with the specified wiring and turnovers.
	 * 
	 * If turnovers is "" (empty string), this reflector will be rotatable.
	 * If turnovers is null, this reflector doesn't rotate.
	 * 
	 * @param name name
	 * @param letters letters
	 * @param wiring wiring
	 * @param turnovers turnovers
	 */
	public Reflector(String name, String letters, String wiring, String turnovers) {
		super(name, letters, wiring, turnovers);
	}
	
	/**
	 * Constructs with the specified wiring pairs.
	 * 
	 * If wiring pairs are not specified, this reflector doesn't convert anything.
	 * 
	 * @param name name
	 * @param letters letters
	 * @param wiringPairs wiring pairs
	 */
	public Reflector(String name, String letters, List<WiringPair> wiringPairs) {
		this(name, letters, wiringPairs, null);
	}
	
	/**
	 * Constructs with the specified wiring pairs and turnovers.
	 * 
	 * If wiring pairs are not specified, this reflector doesn't convert anything.
	 * 
	 * If turnovers is "" (empty string), this reflector will be rotatable.
	 * If turnovers is null, this reflector doesn't rotate.
	 * 
	 * @param name name
	 * @param letters letters
	 * @param wiringPairs wiring pairs
	 * @param turnovers turnovers
	 */
	public Reflector(String name, String letters, List<WiringPair> wiringPairs, String turnovers) {
		this(name, letters, WiringPair.pairsToWiring(letters, wiringPairs), turnovers);
	}
	
	@Override
	protected Reflector newInstance(RingWheel<Reflector> wheel) {
		return new Reflector(wheel);
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
		return UKW_D(WiringPair.toPairs(wiringPairs));
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
		return UKW_D(WiringPair.toPairs(wiringPairs));
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
		List<WiringPair> newWiringPairs = new ArrayList<>(wiringPairs.size() + 1);
		newWiringPairs.addAll(wiringPairs);
		newWiringPairs.add(WiringPair.of('-', '-'));
		
		return new Reflector("UKW-D", "A-ZXWVUTSRQPON-MLKIHGFEDCB", newWiringPairs, null);
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
		return UKW_D_BP(WiringPair.toPairs(wiringPairs));
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
		return UKW_D_BP(WiringPair.toPairs(wiringPairs));
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
		List<WiringPair> newWiringPairs = new ArrayList<>(wiringPairs.size() + 1);
		newWiringPairs.addAll(wiringPairs);
		newWiringPairs.add(WiringPair.of('B', 'O'));
		
		return new Reflector("UKW-D", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", newWiringPairs, null);
	}
}
