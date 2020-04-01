# Assignment 3: The Quest
McKenzie Joyce
u41215872

In this assignment I used Object Oriented Programming to create a game called the Quest. This game is designed for 1-3 players, who all play as heroes on a team using a shared terminal. The heroes and the monsters live in a world represented by a square grid of fixed dimensions. The heroes are able to fight the monsters using weapons, armors, potions and spells. The heroes can also augment their powers by purchasing items to assist in their quest. Every time the heroes win, they gain some experience and some money. When they accumulate enough experience they level up which means that their skills become stronger. The goal of the game is for the heroes to reach a very high level of experience.

## Instructions for running the program
1. Open terminal
2. `cd` into the directory with TheQuest.java
3. `javac TheQuest.java` to compile the program
4. `java TheQuest` to run the program

## Game Play
- W/w: move up
- A/a: move left
- S/s: move down
- D/d: move right
- I/i: View information about your heroes (or if you are in a fight the monsters as well)
- Q/q: Quit the game
- V/v: Display inventories
- C/c: Change weapon or armor
- P/p: Consume Potion
- M/m: Display the Map

## Format of Text Files:
You are welcome to change the heroes/monsters/items you play with by editing the text files the format of entries are as follows
#### Heroes.txt
Hero_Type,Name,Level,Experience_Points,Mana,Agility,Strength,Dexerity
  example: Paladin,Legolas,3,22,20,500,2400,3000
#### Monsters.txt
Monster_Type,Name,Level,BaseDamage,Defense,DodgeProb
  example: Dragon,Smaug,7,390,200,21
#### Buyables.txt
Type,Name,Price,Min_Level_Req,...
##### If type is a Weapon
Type,Name,Price,Min_Level_Req,baseDamage, numberOfHands
  example: Weapon,Sword,700,7,400,1
##### If type is an Armor
Type,Name,Price,Min_Level_Req,Protection
  example: Armor,Chest_Plate,200,1,60
##### If type is a Potion
Type,Name,Price,Min_Level_Req,Agility_Boost,Strength_Boost,Dexerity_Boost,hp_Boost,Mana_Boost
  example:Potion,Mopsus,700,6,1500,1500,1500,450,200
##### If type is a Spell
Type,Name,Price,Min_Level_Req,Damage_Range,Mana_Needed,Base_Damage
  example: Spell,Caustic_Charge,250,2,10,40,112
