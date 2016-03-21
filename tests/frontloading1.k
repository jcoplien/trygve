// Activity Planning: Frontload Activities
// Use case: http://mbrowne.github.io/prokon-dci/#/use-cases/frontload-activities

context FrontloadActivities
{
    private Project project_;

    public FrontloadActivities(Project proj) {
        project_ = proj;
        Activities = proj.activities();
    }

    public void frontload() {
        Activities.resetStartTimes();
        List<Activity> unplannedActivities;

        do {
            unplannedActivities = Activities.findUnplanned();
            for (int i = 0; i < unplannedActivities.size(); i++) {
                Activity activity = unplannedActivities.get(i);

                // Check to see if early start time has been set.
                // (If the activity has already been planned,
                //    then early start time will already be set.)
                if (activity.earlyStartTime() == null) {
                    new FrontloadActivities_PlanActivity(activity)
                        .tryToPlan(project_.startTime());
                }
            }
        }
        while (unplannedActivities.size() > 0);
    }

    role Activities {   
        public void resetStartTimes() {
            for (int i = 0; i < Activities.size(); i++) {
                Activities.get(i).setEarlyStartTime(null);
            }
        }

        public List<Activity> findUnplanned() {
            List<Activity> unplanned = new List<Activity>();
            for (int i = 0; i < Activities.size(); i++) {
                if (Activities.get(i).earlyStartTime() == null) {
                    unplanned.add(Activities.get(i));
                }
            }
            return unplanned;
        }
    }
    requires {
        int size();
        Activity get(int i);
    }
}

///////////////////////////////////////
context FrontloadActivities_PlanActivity
{
    public FrontloadActivities_PlanActivity(Activity activity) {
        UnplannedActivity = activity;
        Predecessors = activity.dependencies();
    }

    public void tryToPlan(int projectStart) {
        if (UnplannedActivity.hasOnlyPlannedPredecessors()) {
            UnplannedActivity.plan(projectStart);
        }
    }

    role Predecessors {
        public int maxEarlyFinishTime(int projectStart) {
            int maxEarlyFinish = projectStart.clone;
            for (int i=0; i < size(); i++) {
                if (Predecessors.get(i).earlyFinishTime() > maxEarlyFinish) {
                    maxEarlyFinish = Predecessors.get(i).earlyFinishTime();
                }
            }
            return maxEarlyFinish;
        }
    }
    requires {
        int size();
        Activity get(int i);
    }

    role UnplannedActivity {
        public boolean hasOnlyPlannedPredecessors() {
            for (int i = 0; i < Predecessors.size(); i++) {
                if (Predecessors.get(i).earlyStartTime() == null) {
                    return false;
                }
            }
            return true;
        }

        public void plan(int projectStart) {
            setEarlyStartTime(Predecessors.maxEarlyFinishTime(projectStart));
        }
    }
    requires {
        List<Activity> dependencies();
        void setEarlyStartTime(int time);
    }
}

///////////////////////////////////////
context Activity {
    private String name_;
    private List<Activity> dependencies_;

    public Activity(String name, int duration, int earlyStartTime) {
        name_ = name.clone;
        Duration = duration;
        EarlyStart = earlyStartTime;
        dependencies_ = new List<Activity>();
    }

    public Activity(String name, int duration, int earlyStartTime, List<Activity> dependencies) {
        // Not supported; copying and pasting for now
        // this(name, duration, earlyStartTime);

        name_ = name.clone;
        Duration = duration;
        EarlyStart = earlyStartTime;

        dependencies_ = dependencies;
    }

    public void addDependency(Activity a) {
        dependencies_.add(a);
    }

    stageprop Duration {} requires {
        int +(int time) const;
    }

    // earliest possible start time for this activity
    stageprop EarlyStart {} requires {
        int +(int duration) const;
    }

    public int earlyFinishTime() {
        return EarlyStart + (Duration + 0);
    }

    public int earlyStartTime() {
        return EarlyStart + 0
    }

    public void setEarlyStartTime(int time) {
        EarlyStart = time;
    }

    public String name() {
        return name_;
    }

    public int duration() {
        return Duration + 0;
    }

    public List<Activity> dependencies() {
        return dependencies_;
    }
}

///////////////////////////////////////
context Project {
    private int startTime_;
    private List<Activity> activities_;

    public Project(int startTime) {
        startTime_ = startTime.clone;
        activities_ = new List<Activity>();
    }

    public Project(int startTime, List<Activity> activities) {
        startTime_ = startTime.clone;
        activities_ = activities;
    }

    public void addActivity(Activity a) {
        activities_.add(a);
    }

    public void removeActivity(Activity a) {
        activities_.remove(a);
    }

    public int startTime() {
        return startTime_;
    }

    public List<Activity> activities() {
        return activities_;
    }
}

///////////////////////////////////////
{
    Project proj = new Project(0);

    Activity a1 = new Activity("eat breakfast", 2, 0);
    Activity a2 = new Activity("go to the moon", 100, 0);
    a2.addDependency(a1);
    Activity a3 = new Activity("do a moon walk", 5, 0);
    a3.addDependency(a2);
    a3.addDependency(a1);
    Activity a4 = new Activity("read newspaper", 5, 0);

    proj.addActivity(a1);
    proj.addActivity(a2);
    proj.addActivity(a3);
    proj.addActivity(a4);

    new FrontloadActivities(proj).frontload();
}

/* GOLD:
line 89: NONCOMPLIANT: Trying to enact object script `size()' without using the interface of the Role it is playing: `Predecessors'.
line 90: NONCOMPLIANT: Trying to enact object script `get(int)' without using the interface of the Role it is playing: `Predecessors'.
___________________________________________________________
FATAL: TERMINATED: Attempt to invoke script `+' on null object on left-hand side of operation involving `rhs'.
	In script `EarlyStart.+'
	Called from script `Activity.earlyStartTime'
	Called from script `Activities.findUnplanned'
	Called from script `FrontloadActivities.frontload'
*/
