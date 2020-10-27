CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:= -lboost_system -lboost_thread

all: Client
	g++ -o bin/BBclient bin/connectionHandler.o bin/BBclient.o bin/Task.o $(LDFLAGS)

Client: bin/connectionHandler.o bin/BBclient.o bin/Task.o

bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/Task.o: src/Task.cpp
	g++ $(CFLAGS) -o bin/Task.o src/Task.cpp

bin/BBclient.o: src/BBclient.cpp
	g++ $(CFLAGS) -o bin/BBclient.o src/BBclient.cpp

.PHONY: clean
clean:
	rm -f bin/*
