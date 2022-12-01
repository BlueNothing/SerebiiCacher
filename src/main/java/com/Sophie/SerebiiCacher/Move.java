package com.Sophie.SerebiiCacher;

public class Move {
//TM # (May be null), Level (May be null), Egg move (Boolean), Name, Type, Category (Phys/Special/Status), Base Power, Accuracy, PP, Eff %, and Description.
int tmNo = 0;
int level = 0;
boolean eggMove = false;
StringBuilder moveName = new StringBuilder();
StringBuilder moveType = new StringBuilder();
StringBuilder moveCategory = new StringBuilder(); //Physical, Special, Status
double basePower = 0;
double accuracy = 0;
double powerPoints = 0;
double effectChance = 0;
StringBuilder description = new StringBuilder();
}
