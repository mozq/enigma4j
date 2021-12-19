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
 * Wiring pair.
 */
public class WiringPair {
	
	/** Uhr A2B positions mapping */
	private static final int[] UHR_A2B_POSITIONS = new int[] {
			6, 31, 4, 29, 18, 39, 16, 25, 30, 23,
			28, 1, 38, 11, 36, 37, 26, 27, 24, 21,
			14, 3, 12, 17, 2, 7, 0, 33, 10, 35,
			8, 5, 22, 19, 20, 13, 34, 15, 32, 9
	};
	
	/** Uhr B2A positions mapping */
	private static final int[] UHR_B2A_POSITIONS = swapKV(UHR_A2B_POSITIONS);
	
	/** Uhr pair A indexes mapping */
	private static final int[] UHR_PAIR_A_INDEXES = new int[] {
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9
	};
	
	/** Uhr pair B indexes mapping */
	private static final int[] UHR_PAIR_B_INDEXES = new int[] {
			6, 0, 7, 5, 1, 8, 4, 2, 9, 3
	};
	
	/** Letter-1 */
	private char letter1;
	
	/** Letter-2 */
	private char letter2;
	
	/**
	 * Constracts with the letters of wiring pair.
	 * 
	 * @param letter1 letter-1
	 * @param letter2 letter-2
	 */
	private WiringPair(char letter1, char letter2) {
		this.letter1 = letter1;
		this.letter2 = letter2;
	}
	
	/**
	 * Returns letter-1.
	 * @return letter-1
	 */
	public char letter1() {
		return this.letter1;
	}
	
	/**
	 * Returns letter-2.
	 * @return letter-2
	 */
	public char letter2() {
		return this.letter2;
	}
	
	/**
	 * Creates new wiring pair.
	 * 
	 * @param letter1 letter-1
	 * @param letter2 letter-2
	 * @return wiring pair
	 */
	public static WiringPair of(char letter1, char letter2) {
		return new WiringPair(letter1, letter2);
	}
	
	/**
	 * Creates new wiring pair. (e.g. "AB")
	 * 
	 * @param pair pair string
	 * @return wiring pair
	 * @throws UnsupportedWiringException thrown when unsupported wiring is specified.
	 */
	public static WiringPair of(String pair) {
		if (pair == null || pair.length() != 2) {
			throw new UnsupportedWiringException(pair);
		}
		
		char letter1 = pair.charAt(0);
		char letter2 = pair.charAt(1);
		
		return new WiringPair(letter1, letter2);
	}
	
	/**
	 * Converts from the white-space separated string to wiring pairs.
	 * 
	 * @param pairs white-space separated string
	 * @return wiring pairs
	 * @throws UnsupportedWiringException thrown when unsupported wiring is specified.
	 */
	public static List<WiringPair> toPairs(String pairs) {
		if (pairs == null) {
			return null;
		}
		
		if (pairs.isBlank()) {
			return List.of();
		}
		
		return toPairs(pairs.trim().split("\\s+"));
	}
	
	/**
	 * Converts from the array of string to wiring pairs.
	 * 
	 * @param pairs string array
	 * @return wiring pairs
	 * @throws UnsupportedWiringException thrown when unsupported wiring is specified.
	 */
	public static List<WiringPair> toPairs(String... pairs) {
		if (pairs == null) {
			return null;
		}
		
		if (pairs.length == 0) {
			return List.of();
		}
		
		return toPairs(List.of(pairs));
	}
	
	/**
	 * Converts from the list of string to wiring pairs.
	 * 
	 * @param pairs string list
	 * @return wiring pairs
	 * @throws UnsupportedWiringException thrown when unsupported wiring is specified.
	 */
	public static List<WiringPair> toPairs(List<String> pairs) {
		if (pairs == null) {
			return null;
		}
		
		List<WiringPair> list = new ArrayList<>(pairs.size());
		for (String pair : pairs) {
			list.add(of(pair));
		}
		
		return list;
	}
	
	/**
	 * Converts from wiring pairs to a wiring.
	 * 
	 * @param letters letters
	 * @param wiringPairs wiring pairs
	 * @return wiring
	 * @throws UnsupportedWiringException thrown when unsupported wiring is specified.
	 */
	public static String pairsToWiring(String letters, List<WiringPair> wiringPairs) {
		char[] wiring = letters.toCharArray();
		
		for (WiringPair pair : wiringPairs) {
			char letter1 = pair.letter1();
			char letter2 = pair.letter2();
			
			int idx1 = -1;
			int idx2 = -1;
			for (int i = 0; i < letters.length(); i++) {
				char letter = letters.charAt(i);
				if (idx1 == -1 && letter == letter1) {
					idx1 = i;
				} else if (idx2 == -1 && letter == letter2) {
					idx2 = i;
				}

				if (idx1 != -1 && idx2 != -1) {
					break;
				}
			}
			
			if (idx1 == -1 || idx2 == -1) {
				throw new UnsupportedWiringException(pair.toString());
			}
			
			wiring[idx1] = letter2;
			wiring[idx2] = letter1;
		}
		
		return String.valueOf(wiring);
	}
	
	/**
	 * Converts from wiring pairs with Uhr setting to a wiring.
	 * 
	 * Uhr
	 *   https://de.wikipedia.org/wiki/Enigma-Uhr
	 *   https://www.cryptomuseum.com/crypto/enigma/uhr/index.htm
	 * 
	 * @param letters letters
	 * @param wiringPairs wiring pairs
	 * @param uhrSetting Uhr setting (0 to 39)
	 * @return wiring
	 * @throws UnsupportedWiringException thrown when unsupported wiring is specified.
	 */
	public static String pairsToWiringUhr(String letters, List<WiringPair> wiringPairs, int uhrSetting) {
		if (wiringPairs.size() != UHR_PAIR_A_INDEXES.length) {
			throw new IllegalArgumentException();
		}
		
		if (uhrSetting < 0 || UHR_A2B_POSITIONS.length <= uhrSetting) {
			throw new IllegalArgumentException();
		}
		
		char[] wiring = letters.toCharArray();
		
		
		for (int pairIdx = 0; pairIdx < wiringPairs.size(); pairIdx++) {
			char letterA = wiringPairs.get(UHR_PAIR_A_INDEXES[pairIdx]).letter1();
			char letterB = wiringPairs.get(UHR_PAIR_B_INDEXES[pairIdx]).letter2();
			
			int pos = normalizeZeroToBase(pairIdx * 4, uhrSetting, UHR_A2B_POSITIONS.length);
			
			int pos2B = normalizeZeroToBase(UHR_A2B_POSITIONS[pos], -2 - uhrSetting, UHR_A2B_POSITIONS.length);
			int pos2A = normalizeZeroToBase(UHR_B2A_POSITIONS[pos], -2 - uhrSetting, UHR_A2B_POSITIONS.length);
			
			char letter2B = wiringPairs.get(UHR_PAIR_B_INDEXES[pos2B / 4]).letter2();
			char letter2A = wiringPairs.get(UHR_PAIR_A_INDEXES[pos2A / 4]).letter1();
			
			int idxA = -1;
			int idxB = -1;
			for (int i = 0; i < letters.length(); i++) {
				char ch = letters.charAt(i);
				
				if (idxA == -1 && ch == letterA) {
					idxA = i;
				} else if (idxB == -1 && ch == letterB) {
					idxB = i;
				}

				if (idxA != -1 && idxB != -1) {
					break;
				}
			}
			
			if (idxA == -1) {
				throw new UnsupportedWiringException(wiringPairs.get(UHR_PAIR_A_INDEXES[pairIdx]).toString());
			}
			
			if (idxB == -1) {
				throw new UnsupportedWiringException(wiringPairs.get(UHR_PAIR_B_INDEXES[pairIdx]).toString());
			}
			
			wiring[idxA] = letter2B;
			wiring[idxB] = letter2A;
		}
		
		return String.valueOf(wiring);
	}
	
	/**
	 * Parse the bigram string of the Uhr setting.
	 * 
	 * @param uhrSettingBigram bigram string
	 * @return Uhr setting
	 */
	public static int uhrSetting(String uhrSettingBigram) {
		if (uhrSettingBigram == null || uhrSettingBigram.length() != 2) {
			throw new IllegalArgumentException();
		}
		
		char c1 = uhrSettingBigram.charAt(0);
		char c2 = uhrSettingBigram.charAt(1);
		
		int d1;
		if ('A' <= c1 && c1 <= 'F') {
			d1 = 0;
		} else if ('G' <= c1 && c1 <= 'M') {
			d1 = 10;
		} else if ('N' <= c1 && c1 <= 'S') {
			d1 = 20;
		} else if ('T' <= c1 && c1 <= 'Z') {
			d1 = 30;
		} else {
			throw new IllegalArgumentException();
		}
		
		int d2;
		if ('A' <= c2 && c2 <= 'C') {
			d2 = 0;
		} else if ('D' <= c2 && c2 <= 'E') {
			d2 = 1;
		} else if ('F' <= c2 && c2 <= 'H') {
			d2 = 2;
		} else if ('I' <= c2 && c2 <= 'J') {
			d2 = 3;
		} else if ('K' <= c2 && c2 <= 'M') {
			d2 = 4;
		} else if ('N' <= c2 && c2 <= 'O') {
			d2 = 5;
		} else if ('P' <= c2 && c2 <= 'R') {
			d2 = 6;
		} else if ('S' <= c2 && c2 <= 'T') {
			d2 = 7;
		} else if ('U' <= c2 && c2 <= 'W') {
			d2 = 8;
		} else if ('X' <= c2 && c2 <= 'Z') {
			d2 = 9;
		} else {
			throw new IllegalArgumentException();
		}
		
		return d1 + d2;
	}
	
	private static int normalizeZeroToBase(int n, int offset, int base) {
		if (n == -1) {
			return -1;
		}
		
		n = (n + offset) % base;
		
		if (n < 0) {
			n += base;
		}
		
		return n;
	}
	
	private static int[] swapKV(int[] kv) {
		int[] vk = new int[kv.length];
		for (int i = 0; i < kv.length; i++) {
			vk[kv[i]] = i;
		}
		return vk;
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
		
		WiringPair o = (WiringPair)obj;
		return o.letter1 == this.letter1 &&
				o.letter2 == this.letter2;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + Character.hashCode(this.letter1);
		result = prime * result + Character.hashCode(this.letter2);
		return result;
	}
	
	@Override
	public String toString() {
		return "" + this.letter1 + this.letter2;
	}
}
