# wirtos'-t9-keyboard

Simple Kotlin keyboard app for Amazfit Stratos|Verge.

No autocomplete, only English, Russian and Ukrainian languages(it's very easy to add one, look into res/xml/number_pad_* and MainActivity.keyMaps. Pull requests appreciated.), all dimensions are hardcoded, no way to set as default keyboard (works basically with [AmazMod](https://github.com/AmazMod/AmazMod)).
### Main features
- Choose languages in launcher
- Swipe up - special symbols keyboard
- Swipe down - hide keyboard silently(without Enter signal)
- Swipe right - move cursor or space
- Swipe left - move cursor
- Tap text preview with green region to close keyboard with enter signal (for example, search something in xbrowser)
- Tap red region to close keyboard without enter signal
- Press and hold green or red region to hide keyboard silently
- Press and hold text while keyboard is closed to move cursor
- Tap shift/globe button - caps lock
- Press shift/globe button - change language
- Touch "0" one time for "space" or use special symbols menu with separate holdable space button
- Press and hold button with number to quickly type it

### How it looks like:

Main             |  Launcher | Symbols
:-------------------------:|:-------------------------:|:-------------------------:
![img_abc](images/img_abc.jpg?raw=true) | ![img_launcher](images/img_launcher.jpg?raw=true)|![img_sym](images/img_sym.jpg?raw=true)
