package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import java.util.List;
import java.util.ArrayList;

public class closeAcourse extends Action{
    private String courseName;

    public closeAcourse(String courseName){
        this.courseName = courseName;
        setActionName("Close A Course");
    }

    protected void start() {
        DepartmentPrivateState dps = (DepartmentPrivateState) actorState;
        CoursePrivateState cps = (CoursePrivateState) poolThreads.getPrivateState(courseName);
        List<Action<Boolean>> actionList = new ArrayList<>();
        for(String f: cps.getRegStudents()){
            Action x= new Unregister(f, courseName);
            actionList.add(x);
        }
        for (Action e: actionList){
            sendMessage(e, courseName, cps);
        }
        then(actionList,()->{
            dps.getCourseList().remove(courseName);
            cps.setAvailableSpots(-1);
            complete(true);
        });

    }
}
