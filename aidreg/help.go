package main

import "fmt"

func PrintUsage() {

	fmt.Print("Usage: aidreg <command> [<arg>]\n\n");
	fmt.Println("The commands are:")
	fmt.Println("   reg\t\tRegister contribution")
	fmt.Println("   list\t\tGet last 20 contributions")
	fmt.Println("   top\t\tGet top contributions")

	fmt.Print("\n")
}

