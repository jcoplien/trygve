// trygve] Strange "Cannot find class, Role, or interface" error (#46)

class ActivityList {
    int size() {
        return 1;
    }
}

context FrontloadActivities
{
    public FrontloadActivities(ActivityList activities) {
        Activities = activities;
    }

    role Activities {   
        public ActivityList getUnplanned() {
            int i = Activities.size();
            return new ActivityList();
        }
    }
    requires {
        public int size();
    }
}

/* GOLD:
line 24: Missing main expression.
___________________________________________________________
*/