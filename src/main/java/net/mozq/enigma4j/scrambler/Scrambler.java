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
 * Scrambler.
 */
public abstract class Scrambler {
	
	/** Name */
	private String name;
	
	/** Letters */
	private String letters;
	
	/** Wiring */
	private String wiring;
	
	/** Turnovers */
	private String turnovers;
	
	/** Turnable */
	private boolean turnable;
	
	/** Forward mapping */
	private int[] forwardMapping;
	
	/** Backward mapping */
	private int[] backwardMapping;
	
	/**
	 * Constracts the same settings as the specified scrambler.
	 * 
	 * @param scrambler scrambler
	 */
	protected Scrambler(Scrambler scrambler) {
		this.name = scrambler.name;
		this.letters = scrambler.letters;
		this.wiring = scrambler.wiring;
		this.turnovers = scrambler.turnovers;
		this.turnable = scrambler.turnable;
		
		this.forwardMapping = scrambler.forwardMapping;
		this.backwardMapping = scrambler.backwardMapping;
	}
	
	/**
	 * Constracts with the wiring and the turnovers.
	 * 
	 * If turnovers is specified, this scrambler will be rotatable and turnover the next scrambler on the letters of turnovers.
	 * If turnovers is "" (empty string), this scrambler will be rotatable but doesn't turnover the next scrambler.
	 * If turnovers is null, this scrambler doesn't rotate and doesn't turnover the next scrambler.
	 * 
	 * @param name name
	 * @param letters letters
	 * @param wiring wiring
	 * @param turnovers turnovers
	 */
	protected Scrambler(String name, String letters, String wiring, String turnovers) {
		if (wiring == null) {
			throw new IllegalArgumentException("Wiring cannot be null.");
		}
		
		if (letters.length() != wiring.length()) {
			throw new IllegalArgumentException("Letters and wiring must be the same length.");
		}
		
		this.name = name;
		this.letters = letters;
		this.wiring = wiring;
		
		this.turnovers = (turnovers == null) ? "" : turnovers;
		this.turnable = (turnovers != null);
		
		int len = letters.length();
		this.forwardMapping = new int[len];
		this.backwardMapping = new int[len];
		for (int wi = 0; wi < len; wi++) {
			char wc = wiring.charAt(wi);
			
			int li = this.letters.indexOf(wc);
			if (li == -1) {
				throw new IllegalArgumentException("" + wc);
			}
			
			if (wc == '-' && li == wi) {
				li = this.letters.indexOf(wc, li + 1);
			}
			
			if (li == -1) {
				throw new IllegalArgumentException("" + wc);
			}
			
			this.forwardMapping[wi] = li;
			this.backwardMapping[li] = wi;
		}
	}
	
	/**
	 * Returns the name.
	 * 
	 * @return name
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
	 * Returns the wiring.
	 * 
	 * @return wiring
	 */
	public String wiring() {
		return this.wiring;
	}
	
	/**
	 * Returns the turnovers.
	 * 
	 * @return turnovers
	 */
	public String turnovers() {
		return this.turnovers;
	}
	
	/**
	 * Returns whether this scrambler is turnable.
	 * 
	 * @return true if this scrambler is turnable
	 */
	public boolean isTurnable() {
		return this.turnable;
	}
	
	/**
	 * Returns the count of letters.
	 * 
	 * @return count of letters
	 */
	public int size() {
		return this.letters.length();
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
		int idx = this.letters.indexOf(letter);
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
		if (letterNumber <= 0 || this.letters.length() < letterNumber) {
			throw new IllegalArgumentException("Letter number is out of range. letterNumber: " + letterNumber);
		}
		
		return this.letters.charAt(letterNumber - 1);
	}
	
	/**
	 * Returns whether the letter is in the turnover position.
	 * 
	 * @param letter letter
	 * @return true if the letter is the turnover position
	 */
	public boolean isTurnoverPosition(char letter) {
		int idx = this.turnovers.indexOf(letter);
		return (idx != -1);
	}
	
	/**
	 * Returns whether the letter number is in the turnover position.
	 * 
	 * @param letterNumber letter number
	 * @return true if the letter number is the turnover position
	 */
	public boolean isTurnoverPosition(int letterNumber) {
		return isTurnoverPosition(letterOf(letterNumber));
	}
	
	/**
	 * Scrambles the number in the forward direction.
	 * 
	 * @param number number
	 * @return scrambled number
	 */
	public int scrambleForward(int number) {
		return scrambleForward(number, 0);
	}
	
	/**
	 * Scrambles the number in the forward direction.
	 * 
	 * @param number number
	 * @param wiringOffset wiring offset
	 * @return scrambled number
	 */
	protected int scrambleForward(int number, int wiringOffset) {
		if (number <= 0 || size() < number) {
			return -1;
		}
		
		number = normalizeOneToBase(number, wiringOffset, size());
		int scrambled = this.forwardMapping[number - 1] + 1;
		scrambled = normalizeOneToBase(scrambled, -wiringOffset, size());
		
		return scrambled;
	}
	
	/**
	 * Scrambles the number in the backward direction.
	 * 
	 * @param number number
	 * @return scrambled number
	 */
	public int scrambleBackward(int number) {
		return scrambleBackward(number, 0);
	}
	
	/**
	 * Scrambles the number in the backward direction.
	 * 
	 * @param number number
	 * @param wiringOffset wiring offset
	 * @return scrambled number
	 */
	protected int scrambleBackward(int number, int wiringOffset) {
		if (number <= 0 || size() < number) {
			return -1;
		}
		
		number = normalizeOneToBase(number, wiringOffset, size());
		int scrambled = this.backwardMapping[number - 1] + 1;
		scrambled = normalizeOneToBase(scrambled, -wiringOffset, size());
		
		return scrambled;
	}
	
	private static int normalizeOneToBase(int n, int offset, int base) {
		n = (n + offset) % base;
		
		if (n <= 0) {
			n += base;
		}
		
		return n;
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
		
		Scrambler o = (Scrambler)obj;
		return Objects.equals(o.name, this.name) &&
				Objects.equals(o.letters, this.letters) &&
				Objects.equals(o.wiring, this.wiring) &&
				Objects.equals(o.turnovers, this.turnovers) &&
				o.turnable == this.turnable;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + Objects.hashCode(this.name);
		result = prime * result + Objects.hashCode(this.letters);
		result = prime * result + Objects.hashCode(this.wiring);
		result = prime * result + Objects.hashCode(this.turnovers);
		result = prime * result + Boolean.hashCode(this.turnable);
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.name);
		sb.append("; ");
		sb.append(this.letters);
		sb.append('=');
		sb.append(this.wiring);
		if (this.turnable) {
			sb.append("; turnovers=");
			sb.append(this.turnovers);
		}
		return sb.toString();
	}
}
