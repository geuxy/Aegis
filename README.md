# Aegis Anticheat
Aegis is a simple open-source anticheat for minecraft 1.8.x that was created for educational purposes.

## Focuses
### Quality over Quantity
Some modern anticheat developers are unaware of basic problems and prefer adding more checks to "patch" bypasses that exploit their poor systems, which can lead to performance degration, false positives, etc.
This anticheat may not fix a lot, but it will aim to fix basic vulnerabilities that may be common.

### Performance
I won't 100% try to make it as fast as possible, but instead keep a balance between clean code / code quality and performance.

### Reduce false positives
This anticheat will try to aim for mainly impossible occurences, since ghost detection can be heavier and still not be as effective.

### Abstraction
I will try to work on abstraction to allow creating powerful custom plugins, improve support for newer minecraft and plugin versions, and different packet protocols instead of only packetevents (which has had vulnerabilities as well) to mitigate possible conflicts.
