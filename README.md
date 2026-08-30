# SRB2Kart

[SRB2Kart](https://srb2.org/mods/) is a kart racing mod based on the 3D Sonic the Hedgehog fangame [Sonic Robo Blast 2](https://srb2.org/), based on a modified version of [Doom Legacy](http://doomlegacy.sourceforge.net/).

## Android

Download the latest APK from [Releases](https://github.com/Greenzin1/Kart-Public/releases).

- Requires Android 7.0+ (API 24)
- arm64-v8a + armeabi-v7a
- SDL2 2.30.3

### Known Limitations
- HWRENDER disabled (no OpenGL ES setup yet)
- MasterServer disabled
- HTTP addon downloads disabled

### Building

The Android build is done via GitHub Actions. Push to `master` to trigger a build, or use `workflow_dispatch`.

## Dependencies
- NASM (x86 builds only)
- SDL2 (Linux/OS X only)
- SDL2-Mixer (Linux/OS X only)
- libupnp (Linux/OS X only)
- libgme (Linux/OS X only)

## Compiling

See [SRB2 Wiki/Source code compiling](http://wiki.srb2.org/wiki/Source_code_compiling). The compiling process for SRB2Kart is largely identical to SRB2.

## Disclaimer
Kart Krew is in no way affiliated with SEGA or Sonic Team. We do not claim ownership of any of SEGA's intellectual property used in SRB2.
