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

import java.util.Objects;

/**
 * Ring wheel.
 */
public abstract class RingWheel<T extends RingWheel<?>> extends Wheel {
	
	/** Ring setting (Ringstellung) */
	private int ringSetting = 1;
	
	/**
	 * Constructs with the same settings as the specified ring wheel.
	 * 
	 * @param ringWheel ring wheel
	 */
	protected RingWheel(RingWheel<T> ringWheel) {
		super(ringWheel);
		this.ringSetting = ringWheel.ringSetting;
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
	public RingWheel(String name, String letters, String wiring, String turnovers) {
		super(name, letters, wiring, turnovers);
	}
	
	/**
	 * Creates new instance with the specified wheel.
	 * 
	 * @param wheel wheel
	 * @return new instance of the wheel
	 */
	protected abstract T newInstance(RingWheel<T> wheel);
	
	/**
	 * Returns a wheel with the specified ring settings.
	 * 
	 * @param ringSettingLetter the letter of the ring setting
	 * @return wheel
	 */
	public T ring(char ringSettingLetter) {
		return ring(letterNumberOf(ringSettingLetter));
	}
	
	/**
	 * Returns a wheel with the specified ring settings.
	 * 
	 * @param ringSetting ring setting
	 * @return wheel
	 */
	public T ring(int ringSetting) {
		if (ringSetting <= 0 || size() < ringSetting) {
			throw new IllegalArgumentException("Ring setting is out of range. ringSetting: " + ringSetting);
		}
		
		T wheel = newInstance(this);
		((RingWheel<?>)wheel).ringSetting = ringSetting;
		
		return wheel;
	}
	
	/**
	 * Returns the ring setting.
	 * 
	 * @return ring setting
	 */
	public int ringSetting() {
		return this.ringSetting;
	}

	/**
	 * Scrambles the number in the forward direction.
	 * 
	 * @param number number
	 * @return scrambled number
	 */
	@Override
	public int scrambleForward(int number) {
		int wiringOffset = -(this.ringSetting - 1);
		
		int scrambled = scrambleForward(number, wiringOffset);
		
		return scrambled;
	}

	/**
	 * Scrambles the number in the backward direction.
	 * 
	 * @param number number
	 * @return scrambled number
	 */
	@Override
	public int scrambleBackward(int number) {
		int wiringOffset = -(this.ringSetting - 1);
		
		int scrambled = scrambleBackward(number, wiringOffset);
		
		return scrambled;
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
		
		RingWheel<?> o = (RingWheel<?>)obj;
		return o.ringSetting == this.ringSetting;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + super.hashCode();
		result = prime * result + Objects.hashCode(this.ringSetting);
		return result;
	}
	
	@Override
	public String toString() {
		return super.toString() + "; ring=" + this.ringSetting + "(" + letterOf(this.ringSetting) + ")";
	}
}
