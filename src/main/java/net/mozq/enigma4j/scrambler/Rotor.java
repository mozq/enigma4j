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

/**
 * Rotor (Walzen).
 */
public class Rotor extends RingWheel<Rotor> {
	
	/**
	 * Constructs with the same settings as the specified rotor.
	 * 
	 * @param rotor rotor
	 */
	protected Rotor(RingWheel<Rotor> rotor) {
		super(rotor);
	}
	
	/**
	 * Constructs with the specified wiring.
	 * 
	 * The ring settings are initialized at 01(A).
	 * 
	 * @param name name
	 * @param letters letters
	 * @param wiring wiring
	 */
	public Rotor(String name, String letters, String wiring) {
		this(name, letters, wiring, null);
	}
	
	/**
	 * Constructs with the specified wiring and turnovers.
	 * 
	 * If turnovers is specified, this rotor will be rotatable and turnover the next rotor on the letters of turnovers.
	 * If turnovers is "" (empty string), this rotor will be rotatable but doesn't turnover the next rotor.
	 * If turnovers is null, this rotor doesn't rotate and doesn't turnover the next rotor.
	 * 
	 * The ring settings are initialized at 01(A).
	 * 
	 * @param name name
	 * @param letters letters
	 * @param wiring wiring
	 * @param turnovers turnovers
	 */
	public Rotor(String name, String letters, String wiring, String turnovers) {
		super(name, letters, wiring, turnovers);
	}
	
	@Override
	protected Rotor newInstance(RingWheel<Rotor> wheel) {
		return new Rotor(wheel);
	}
}
