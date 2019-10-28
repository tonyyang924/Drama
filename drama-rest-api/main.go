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

func singleDrama(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	//FIXME: hardcode temporarily
	id := vars["id"]
	switch id {
	case "1":
		fmt.Fprintf(w, "{\"data\":[{\"drama_id\":1,\"name\":\"致我們單純的小美好\",\"total_views\":23562274,\"created_at\":\"2017-11-23T02:04:39.000Z\",\"thumb\":\"https://i.pinimg.com/originals/61/d4/be/61d4be8bfc29ab2b6d5cab02f72e8e3b.jpg\",\"rating\":4.4526}]}")
	case "2":
		fmt.Fprintf(w, "{\"data\":[{\"drama_id\":2,\"name\":\"獅子王強大\",\"total_views\":14611380,\"created_at\":\"2017-12-02T03:34:41.000Z\",\"thumb\":\"https://i.pinimg.com/originals/12/ce/16/12ce16bd184253837326599b26e16c44.jpg\",\"rating\":4.3813}]}")
	case "3":
		fmt.Fprintf(w, "{\"data\":[{\"drama_id\":3,\"name\":\"如果有妹妹就好了\",\"total_views\":2931180,\"created_at\":\"2017-10-21T12:34:41.000Z\",\"thumb\":\"https://i.pinimg.com/originals/32/c1/7a/32c17af1c085be75657e965954f8d601.jpg\",\"rating\":4.0647}]}")
	case "5":
		fmt.Fprintf(w, "{\"data\":[{\"drama_id\":5,\"name\":\"黑騎士\",\"total_views\":7473757,\"created_at\":\"2017-12-06T22:09:45.000Z\",\"thumb\":\"https://s-media-cache-ak0.pinimg.com/originals/88/2c/dc/882cdca85526dfb9d9f03cf192c0846c.png\",\"rating\":4.2167}]}")
	case "6":
		fmt.Fprintf(w, "{\"data\":[{\"drama_id\":6,\"name\":\"出境事務所\",\"total_views\":79310,\"created_at\":\"2017-10-21T12:34:41.000Z\",\"thumb\":\"https://s-media-cache-ak0.pinimg.com/564x/30/35/49/30354912dd432a2475428a003661784a.jpg\",\"rating\":4.2667}]}")
	case "10":
		fmt.Fprintf(w, "{\"data\":[{\"drama_id\":10,\"name\":\"101次求婚\",\"total_views\":239001,\"created_at\":\"2017-11-08T16:40:06.000Z\",\"thumb\":\"https://i.pinimg.com/originals/61/67/b2/6167b2d94d305203b43a230874139dab.jpg\",\"rating\":4.0777}]}")
	default:
		fmt.Fprintf(w, id)
	}
}

func main() {
	router := mux.NewRouter().StrictSlash(true)
	router.HandleFunc("/drama", homeLink)
	router.HandleFunc("/drama/{id:[0-9]+}", singleDrama)
	log.Fatal(http.ListenAndServe(":8080", router))
}
