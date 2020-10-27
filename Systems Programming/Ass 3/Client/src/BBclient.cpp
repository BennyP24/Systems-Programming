
#include <cstdlib>
#include <iostream>
#include "../include/connectionHandler.h"
#include "../include/Task.h"
#include <boost/thread.hpp>
#include <thread>
#include <chrono>
using namespace std;

int main (int argc, char *argv[]) {
    if (argc < 3) {
        cerr << "Usage: " << argv[0] << " host port" << endl << endl;
        return -1;
    }
    string host = argv[1];
    short port = atoi(argv[2]);
    ConnectionHandler connectionHandler(host, port);
    atomic<bool> terminate(false);
    Task task(&connectionHandler, &terminate);
    if (!connectionHandler.connect()) {
        return 1;
    }
    boost::thread thread(boost::bind(&Task::run, &task));
    while (!terminate.load()) {
        const short bufsize = 1024;
        char buf[bufsize];
        this_thread::sleep_for(chrono::seconds(1));
        if (!terminate.load() && !cin.eof()) {
            cin.getline(buf, bufsize);
            string line(buf);
            if (!connectionHandler.sendLine(line)) {
                break;
            }
        }
    }
    thread.join();
    return 0;
}

