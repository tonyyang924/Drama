# drama-rest-api

## Running Service in GCP platform

$ gcloud compute copy-files $PWD tony-instance1:~

$ gcloud compute ssh tony-instance1

tonyyang@tony-instance1:$ mkdir workspace && mv drama-rest-api workspace

tonyyang@tony-instance1:$ cd drama-rest-api

tonyyang@tony-instance1:$ go build .

tonyyang@tony-instance1:$ sudo vim /etc/systemd/system/drama-rest-api.service

```
[Unit]
Description=My drama rest api service
[Service]
User=ubuntu
# The configuration file application.properties should be here:
#change this to your workspace
WorkingDirectory=/home/tonyyang/workspace
#path to executable.
#executable is a bash script which calls jar file
ExecStart=/home/tonyyang/workspace/drama-rest-api/drama-rest-api
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5
[Install]
WantedBy=multi-user.target
```

tonyyang@tony-instance1:$ sudo systemctl daemon-reload

tonyyang@tony-instance1:$ sudo systemctl start drama-rest-api.service

tonyyang@tony-instance1:$ sudo systemctl status drama-rest-api.service
```
sudo systemctl status drama-rest-api.service
● drama-rest-api.service - My drama rest api service
   Loaded: loaded (/etc/systemd/system/drama-rest-api.service; enabled; vendor preset: enabled)
   Active: active (running) since Sun 2020-03-01 14:00:43 CST; 10min ago
 Main PID: 373 (drama-rest-api)
    Tasks: 5 (limit: 4915)
   CGroup: /system.slice/drama-rest-api.service
           └─373 /home/tonyyang/workspace/drama-rest-api/drama-rest-api

Mar 01 14:00:43 tony-instance1 systemd[1]: Started My drama rest api service.
```

## Verify Drama Web Service

tonyyang@tony-instance1:$ curl localhost:8080/drama/1
```
{"data":[{"drama_id":1,"name":"致我們單純的小美好","total_views":23562274,"created_at":"2017-11-23T02:04:39.000Z","thumb":"https://i.pinimg.com/originals/61/d4/be/61d4be8bfc29ab2b6d5cab02f72e8e3b.jpg","rating":4.4526}]}
```

## Reference

https://dzone.com/articles/run-your-java-application-as-a-service-on-ubuntu