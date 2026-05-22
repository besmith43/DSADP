#!/usr/bin/env bash


inputFile="$(ls *.txt | fzf)"


java Main.java "$inputFile"

