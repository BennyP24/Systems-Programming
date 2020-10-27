package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.Simulator;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class checkAdmin extends Action {
    private LinkedList<String> studentsId;
    private LinkedList<String> obligations;
    private String computerType;
    private Warehouse warehouse;

    public checkAdmin(String computerType, LinkedList<String> studentsId, LinkedList<String> obligations,Warehouse warehouse){
        this.computerType = computerType;
        this.studentsId = studentsId;
        this.obligations = obligations;
        this.warehouse =warehouse;
        setActionName("Administrative Check");
    }

    protected void start() {
        DepartmentPrivateState dps = ((DepartmentPrivateState)actorState);
        List<Action<Boolean>> actions = new ArrayList<>();
        if(warehouse.isFree(computerType).isResolved()){
            for (Integer i = 0; i < studentsId.size() ; i++) {
                Action x = new updateSignature(warehouse.getComputer(computerType), obligations);
                sendMessage(x, studentsId.get(i), poolThreads.getPrivateState(studentsId.get(i)));
            }
            complete(true);
        }
        else{
            warehouse.isFree(computerType).subscribe(()->{
                sendMessage(this, actorId, dps);
            });


        }
    }
}
