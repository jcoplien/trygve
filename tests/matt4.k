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
line 12: Role `Activities' cannot be played by object of type `ActivityList':
line 12: 	`size()' needed by Role `Activities' is declared as private in interface of `ActivityList' and is therefore inaccessible to the Role.
line 24: Missing main expression.
0 warnings, 3 errors.
___________________________________________________________
*/
