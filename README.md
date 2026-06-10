# DroidSpaces ROM for OnePlus 7 Pro (guacamole)

Custom Android 11 (LineageOS 18.1) build integrating PickleHik3,
a custom kernel, and DroidSpaces container support.

## Required GitHub Secrets

| Secret | Description |
|--------|-------------|
| `KEYSTORE_BASE64` | Base64-encoded Java keystore file (JKS) for APK signing |
| `KEYSTORE_ALIAS` | Alias name of the signing key in the keystore |
| `KEYSTORE_PASSWORD` | Keystore password |
| `KEY_PASSWORD` | Private key password |

### Encoding your keystore

```bash
base64 -w0 mykey.jks
```

Copy the output and set it as the `KEYSTORE_BASE64` secret in your
GitHub repository settings (Settings → Secrets and variables → Actions).

## Workflow Run Order

Run the workflows in sequence. Each depends on the previous.

```
1-build-picklehik3.yml  →  2-build-kernel.yml  →  3-build-rom.yml
```

1. **Build PickleHik3** — builds and signs the launcher APK
2. **Build Kernel** — compiles the kernel, patches boot.img with Magisk
3. **Build ROM** — full LineageOS build with PickleHik3 and DroidSpaces
   preinstalled

## Final Artifacts

After all three workflows complete, the following artifacts
are available for download (30-day retention):

- `PickleHik3-signed.apk` — signed launcher APK (also embedded in ROM)
- `Image.gz-dtb` — compiled kernel image + device tree blob
- `boot-patched.img` — Magisk-patched boot image with custom kernel
- `lineage-18.1-guacamole-CUSTOM.zip` — full flashable ROM

## Flash Order

1. **Flash ROM zip** via recovery (LineageOS recovery or TWRP):
   ```
   adb sideload lineage-18.1-guacamole-CUSTOM.zip
   ```
   or copy to device storage and flash from recovery menu.

2. **Flash patched boot image** via fastboot:
   ```
   fastboot flash boot boot-patched.img
   ```

3. **Reboot** and complete initial setup.

## Post-Flash Setup

1. Install DroidSpaces from Play Store or sideload the APK.
2. Install your preferred Linux container (Debian recommended).
3. DroidSpaces does **not** autostart any container — connect your
   own container manually from the app.

## SELinux

SELinux is **enforcing** at all times. The custom `droidspaces.te`
policy grants the necessary permissions for container operations
(namespaces, loop devices, GPU, chroot) as `system_app`.
No `setenforce 0` is required or supported.
