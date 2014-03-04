package main

import (
	"flag"
	"fmt"
	"strconv"
	"encoding/json"
	"os"
)

func Reg() {

	who := flag.Arg(1)
	amountStr := flag.Arg(2)

	amount, err := strconv.ParseInt(amountStr, 10, 64)

	if err != nil {
		fmt.Printf("reg command wasn't used correct. type 'aidreg help reg' for more information on the reg command\n\n")
		return
	}

	if amount <= 0 {
		fmt.Printf("Amount must be greater than zero! We are not giving our money away to %v!\n\n", who)
		return
	}

	reg := Registration{who, amount}

	fmt.Print(reg.ToString()+"\n\n")

	// everything is ok, move forward with registration

	conn := Conn()
	defer conn.Close()

	regJson, err := json.Marshal(reg)
	if (err != nil) {
		fmt.Printf("Error converting registration to json\n%v", err)
		os.Exit(1)
	}

	conn.Send("LPUSH", "last", regJson)
	conn.Send("LTRIM", "last", 0, 19)
	conn.Send("ZADD", "contributors", reg.Amount, regJson)
	conn.Send("INCRBY", "total:amount", reg.Amount)
	conn.Send("INCR", "total:cnt")

	conn.Flush()
	conn.Do("publish", "contributions-updated", "updated")
}

type Registration struct {
	Who string
	Amount int64
}

func(this Registration) ToString() string {
	return fmt.Sprintf("%v contributed amount: %v", this.Who, this.Amount)
}
