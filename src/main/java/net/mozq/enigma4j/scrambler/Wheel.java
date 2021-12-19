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
 * Wheel.
 */
public abstract class Wheel extends Scrambler {
	
	/**
	 * Constructs with the same settings as the specified wheel.
	 * 
	 * @param wheel wheel
	 */
	protected Wheel(Wheel wheel) {
		super(wheel);
	}
	
	/**
	 * Constructs with the specified wiring and turnovers.
	 * 
	 * If turnovers is specified, this wheel will be rotatable and turnover the next wheel on the letters of turnovers.
	 * If turnovers is "" (empty string), this wheel will be rotatable but doesn't turnover the next wheel.
	 * If turnovers is null, this wheel doesn't rotate and doesn't turnover the next wheel.
	 * 
	 * The ring settings are initialized at A (01).
	 * 
	 * @param name name
	 * @param letters letters
	 * @param wiring wiring
	 * @param turnovers turnovers
	 */
	public Wheel(String name, String letters, String wiring, String turnovers) {
		super(name, letters, wiring, turnovers);
	}
}
