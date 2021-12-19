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
package net.mozq.enigma4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.mozq.enigma4j.machine.EnigmaMachine;
import net.mozq.enigma4j.machine.EnigmaMachineSpec;
import net.mozq.enigma4j.machine.model.EnigmaA_133;
import net.mozq.enigma4j.machine.model.EnigmaD;
import net.mozq.enigma4j.machine.model.EnigmaG;
import net.mozq.enigma4j.machine.model.EnigmaG_111;
import net.mozq.enigma4j.machine.model.EnigmaG_260;
import net.mozq.enigma4j.machine.model.EnigmaG_312;
import net.mozq.enigma4j.machine.model.EnigmaI;
import net.mozq.enigma4j.machine.model.EnigmaK;
import net.mozq.enigma4j.machine.model.EnigmaKD;
import net.mozq.enigma4j.machine.model.EnigmaM3;
import net.mozq.enigma4j.machine.model.EnigmaM4;
import net.mozq.enigma4j.machine.model.EnigmaNorway;
import net.mozq.enigma4j.machine.model.EnigmaRailway;
import net.mozq.enigma4j.machine.model.EnigmaSonder;
import net.mozq.enigma4j.machine.model.EnigmaSwiss_K;
import net.mozq.enigma4j.machine.model.EnigmaT;
import net.mozq.enigma4j.machine.model.EnigmaZ;

/**
 * Enigma.
 */
public class Enigma {
	
	/**
	 * Enigma B - Mark II, Wiring of the A-133
	 */
	public static class A_133 extends EnigmaA_133 {
		private A_133() { }
	}
	
	/**
	 * Enigma D
	 * Commercial Enigma A26
	 */
	public static class D extends EnigmaD {
		private D() { }
	}
	
	/**
	 * Enigma I
	 * German Army and Air Force (Wehrmacht, Luftwaffe)
	 */
	public static class I extends EnigmaI {
		private I() { }
	}
	
	/**
	 * Norway Enigma (Norenigma)
	 * Postwar usage
	 */
	public static class Norway extends EnigmaNorway {
		private Norway() { }
	}
	
	/**
	 * Sonder Enigma
	 * Sondermaschine (special machine)
	 */
	public static class Sonder extends EnigmaSonder {
		private Sonder() { }
	}
	
	/**
	 * Enigma M3
	 * German Navy (Kriegsmarine)
	 */
	public static class M3 extends EnigmaM3 {
		private M3() { }
	}
	
	/**
	 * Enigma M4
	 * U-boat Enigma
	 */
	public static class M4 extends EnigmaM4 {
		private M4() { }
	}
	
	/**
	 * Enigma G
	 * Zählwerk Enigma A28 and G31
	 */
	public static class G extends EnigmaG {
		private G() { }
	}
	
	/**
	 * Enigma G, Wiring of the G-312
	 * G31 Abwehr Enigma
	 */
	public static class G_312 extends EnigmaG_312 {
		private G_312() { }
	}
	
	/**
	 * Enigma G, Wiring of the G-260
	 * G31 Abwehr Enigma
	 */
	public static class G_260 extends EnigmaG_260 {
		private G_260() { }
	}
	
	/**
	 * Enigma G, Wiring of the G-111
	 * G31 Hungarian Enigma
	 */
	public static class G_111 extends EnigmaG_111 {
		private G_111() { }
	}
	
	/**
	 * Enigma K
	 * Commercial Enigma A27
	 */
	public static class K extends EnigmaK {
		private K() { }
	}
	
	/**
	 * Swiss-K
	 * Swiss Enigma K variant
	 */
	public static class Swiss_K extends EnigmaSwiss_K {
		private Swiss_K() { }
	}
	
	/**
	 * Enigma KD
	 * Enigma K with UKW-D
	 */
	public static class KD extends EnigmaKD {
		private KD() { }
	}
	
	/**
	 * Railway Enigma
	 * Modified Enigma K
	 */
	public static class Railway extends EnigmaRailway {
		private Railway() { }
	}
	
	/**
	 * Enigma T
	 * Japanese Enigma (Tirpitz)
	 */
	public static class T extends EnigmaT {
		private T() { }
	}
	
	/**
	 * Enigma Z
	 * Numbers-only Enigma Z30 (Standard version)
	 */
	public static class Z extends EnigmaZ {
		private Z() { }
	}
	
	/** Enigma machine specifications */
	private static final List<EnigmaMachineSpec> MACHINE_SPECS = List.of(
			A_133.spec(),
			D.spec(),
			I.spec(),
			Norway.spec(),
			Sonder.spec(),
			M3.spec(),
			M4.spec(),
			G.spec(),
			G_312.spec(),
			G_260.spec(),
			G_111.spec(),
			K.spec(),
			Swiss_K.spec(),
			KD.spec(),
			Railway.spec(),
			T.spec(),
			Z.spec()
	);
	
	/** Enigma machine names */
	private static final List<String> MACHINE_NAMES = MACHINE_SPECS.stream()
			.map(v -> v.name()).collect(Collectors.toUnmodifiableList());
	
	/** Enigma machine specification map */
	private static final Map<String, EnigmaMachineSpec> MACHINE_SPEC_MAP = MACHINE_SPECS.stream()
			.collect(Collectors.toUnmodifiableMap(v -> v.name(), v -> v));
	
	
	/**
	 * This class has only static methods.
	 */
	private Enigma() {
		// NOP
	}
	
	/**
	 * List all machine names.
	 * 
	 * @return machine name list
	 */
	public static List<String> machineNames() {
		return MACHINE_NAMES;
	}
	
	/**
	 * Returns the machine specification.
	 * 
	 * @param machineName machine name
	 * @return machine specification
	 */
	public static EnigmaMachineSpec machineSpec(String machineName) {
		return MACHINE_SPEC_MAP.get(machineName);
	}
	
	/**
	 * Creates and returns new Enigma machine instance.
	 * 
	 * @param machineName machine name
	 * @return new Enigma machine instance
	 */
	public static EnigmaMachine machine(String machineName) {
		EnigmaMachineSpec machineSpec = machineSpec(machineName);
		if (machineSpec == null) {
			throw new IllegalArgumentException();
		}
		
		return new EnigmaMachine(machineSpec);
	}
}
