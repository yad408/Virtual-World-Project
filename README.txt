Virtual World Project
CSC 203, Summer '19

The "Functions" class has a "createSnoop" method that is used in the cloud class to spawn Snoop Dogg.
The "Functions" class has the "clamp" method in it because it's only needed in the "WorldView" class.
I added three abstract classes - "ActionEntity" which implements the interface "Action"; "AnimateEntity" which extends "ActionEntity"; "Miner" which extends "AnimateEntity"

A new "SnoopDogg" entity was created that spawns out of a smoke cloud when the screen is clicked.
The "SnoopDogg" class extends the abstract class "Miner".
This smoke cloud is created in the "Cloud" class.
The "Cloud" class has two methods that spawns Snoop Dogg within a radius bound by limits.
Since Snoop Dogg has the munchies he moves really slow to eat the ores, but can never get full.


