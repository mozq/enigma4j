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

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Plugboard (Steckerbrett).
 */
public class Plugboard extends Scrambler {
	
	/** Wiring pairs */
	private List<WiringPair> wiringPairs;
	
	/** Uhr setting */
	private int uhrSetting;
	
	
	/**
	 * Constructs with the specified wiring pairs.
	 * 
	 * If wiring pairs are not specified, this plugboard doesn't convert anything.
	 * 
	 * @param name name
	 * @param letters letters
	 * @param wiringPairs wiring pairs
	 */
	public Plugboard(String name, String letters, List<WiringPair> wiringPairs) {
		super(name, letters, WiringPair.pairsToWiring(letters, wiringPairs), null);
		
		this.wiringPairs = Collections.unmodifiableList(wiringPairs);
		this.uhrSetting = 0;
	}
	
	/**
	 * Constructs with the specified wiring pairs and Uhr setting.
	 * 
	 * Be sure to specify 10 pairs of wiring for wiring pairs.
	 * 
	 * @param name name
	 * @param letters letters
	 * @param wiringPairs wiring pairs
	 * @param uhrSetting Uhr setting (0 to 39)
	 */
	public Plugboard(String name, String letters, List<WiringPair> wiringPairs, int uhrSetting) {
		super(name, letters, WiringPair.pairsToWiringUhr(letters, wiringPairs, uhrSetting), null);
		
		this.wiringPairs = Collections.unmodifiableList(wiringPairs);
		this.uhrSetting = uhrSetting;
	}
	
	/**
	 * Returns the wiring pairs.
	 * 
	 * @return wiring pairs
	 */
	public List<WiringPair> getWiringPairs() {
		return this.wiringPairs;
	}
	
	/**
	 * Returns the Uhr setting.
	 * 
	 * @return Uhr setting
	 */
	public int getUhrSetting() {
		return this.uhrSetting;
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
		
		if (!super.equals(obj)) {
			return false;
		}
		
		Plugboard o = (Plugboard)obj;
		return o.uhrSetting == this.uhrSetting;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + super.hashCode();
		result = prime * result + Objects.hashCode(this.uhrSetting);
		return result;
	}
	
	@Override
	public String toString() {
		if (this.uhrSetting == 0) {
			return super.toString();
		} else {
			return super.toString() + "; uhrSetting=" + this.uhrSetting;
		}
	}
}
