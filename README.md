# enigma4j [![](https://jitpack.io/v/mozq/enigma4j.svg)](https://jitpack.io/#mozq/enigma4j)

The enigma4j is an Enigma machine library for Java. 


## How to use

### Standard usage

```java
EnigmaMachine enigmaMachine = Enigma.M3.machine()
        .reflector(Enigma.M3.UKW_B)
        .rotor(3, Enigma.M3.III.ring('A'), 'U')
        .rotor(2, Enigma.M3.VI.ring('H'), 'Z')
        .rotor(1, Enigma.M3.VIII.ring('M'), 'V')
        .plugboard("AN EZ HK IJ LR MQ OT PV SW UX");

String encrypted = enigmaMachine.translate("SECRETMESSAGE");
```

### By name

```java
EnigmaMachine enigmaMachine = Enigma.machine("M3")
        .reflector("UKW_B")
        .rotor(3, "III", 'A', 'U')
        .rotor(2, "VI", 'H', 'Z')
        .rotor(1, "VIII", 'M', 'V')
        .plugboard("AN EZ HK IJ LR MQ OT PV SW UX");

String encrypted = enigmaMachine.translate("SECRETMESSAGE");
```

### Settable reflector

```java
EnigmaMachine enigmaMachine = Enigma.D.machine()
        .reflector(Enigma.D.UKW.ring('Z'), 'C') // Ring='Z', Position='C'
        .rotor(3, Enigma.D.I.ring('A'), 'B')
        .rotor(2, Enigma.D.II.ring('B'), 'A')
        .rotor(1, Enigma.D.III.ring('C'), 'Z');

String encrypted = enigmaMachine.translate("SECRETMESSAGE");
```

### 4 rotors (Enigma M4)

```java
EnigmaMachine enigmaMachine = Enigma.M4.machine()
        .reflector(Enigma.M4.UKW_B)
        .rotor(4, Enigma.M4.BETA.ring('A'), 'V')
        .rotor(3, Enigma.M4.II.ring('A'), 'J')
        .rotor(2, Enigma.M4.IV.ring('A'), 'N')
        .rotor(1, Enigma.M4.I.ring('V'), 'A')
        .plugboard("AT BL DF GJ HM NW OP QY RZ VX");

String encrypted = enigmaMachine.translate("SECRETMESSAGE");
```

### UKW-D (Enigma I, Enigma M4 and Enigma KD)

```java
EnigmaMachine enigmaMachine = Enigma.KD.machine()
        .reflector(Enigma.KD.UKW_D("AZ BC DE FG HI KL MN OP QR ST UV WX")) // UKW-D
        .rotor(3, Enigma.KD.I.ring('A'), 'B')
        .rotor(2, Enigma.KD.II.ring('B'), 'A')
        .rotor(1, Enigma.KD.III.ring('C'), 'Z');

String encrypted = enigmaMachine.translate("SECRETMESSAGE");
```

### Plugboard with Uhr (Enigma I)

```java
EnigmaMachine enigmaMachine = Enigma.I.machine()
        .reflector(Enigma.I.UKW_A)
        .rotor(3, Enigma.I.I.ring(1), 2)
        .rotor(2, Enigma.I.II.ring(2), 1)
        .rotor(1, Enigma.I.III.ring(3), 26)
        .plugboard("AB CD EF GH IJ KL MN OP QR ST", 4); // Uhr = 4

String encrypted = enigmaMachine.translate("SECRETMESSAGE");
```

### Supported Enigma machines

- Enigma I
- Enigma M3
- Enigma M4 (U-boat Enigma)
- Norway Enigma "Norenigma"
- Sondermaschine (Special machine)
- Enigma G "Zählwerk Enigma" (A28/G31)
- Enigma G G-312 (G31 Abwehr Enigma)
- Enigma G G-260 (G31 Abwehr Enigma)
- Enigma G G-111 (G31 Hungarian Enigma)
- Enigma D (Commercial Enigma A26)
- Enigma K (Commercial Enigma A27)
- Enigma KD (Enigma K with UKW-D)
- Swiss-K (Swiss Enigma K variant)
- Railway Enigma "Rocket I"
- Enigma T "Tirpitz" (Japanese Enigma)
- Enigma B - Mark II, Wiring of the A-133
- Enigma Z
- Spanish Enigma, wiring D
- Spanish Enigma, wiring F
- Spanish Enigma, Zagreb Delta machine A 16081
- Spanish Enigma, Sonderschaltung / Zagreb Delta machine A 16101


## License

The enigma4j is open source software licensed under the [Apache License 2.0](https://github.com/mozq/enigma4j/blob/main/LICENSE.txt).
