package main

import (
	"bytes"
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/gorilla/mux"
)

func homeLink(w http.ResponseWriter, r *http.Request) {
	jsonFile, err := os.Open("dramas-sample.json")
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println("Successfully Opened dramas-sample.json")
	defer jsonFile.Close()

	buf := new(bytes.Buffer)
	buf.ReadFrom(jsonFile)
	contents := buf.String()

	fmt.Fprintf(w, contents)
}

func main() {
	router := mux.NewRouter().StrictSlash(true)
	router.HandleFunc("/drama", homeLink)
	log.Fatal(http.ListenAndServe(":8080", router))
}
