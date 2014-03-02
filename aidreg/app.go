package main

import (
	"fmt"
	"flag"
)

func main() {
	fmt.Print("        _     _                \n   __ _(_) __| |_ __ ___  __ _ \n  / _` | |/ _` | '__/ _ \\/ _` |\n | (_| | | (_| | | |  __/ (_| |\n  \\__,_|_|\\__,_|_|  \\___|\\__, |\n                         |___/ \n")
	fmt.Print("et eksempel av Sonat Consulting AS\n\n")

	flag.Parse()

//	fmt.Printf("the argument is:'%v'\n", flag.Arg(0))

	if len(flag.Args()) == 0 {
		PrintUsage()
		return;
	}

	a1 := flag.Arg(0)

//	fmt.Printf("the argument is:%v", a1)

	switch a1 {
	case "reg":
		Reg()
	case "list":
		fmt.Print("list...")
	case "top":
		fmt.Print("top...")
	default:
		PrintUsage()
	}
}



