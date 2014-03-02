package main

import (
	"github.com/garyburd/redigo/redis"
	"fmt"
	"os"
)

func Conn() redis.Conn {
	conn, err := redis.Dial("tcp", ":6379")

	if (err != nil) {
		fmt.Printf("Error connecting to redis.\n%v\n\n", err)
		os.Exit(1)
	}

	return conn
}

type KeyGen struct {
}

func (KeyGen) Last() string {
	return "last"
}

func (KeyGen) Contributors() string {
	return "contributors"
}

func (KeyGen) TotAmount() string {
	return "total:amount"
}

func (KeyGen) TotalCnt() string {
	return "total:cnt"
}
