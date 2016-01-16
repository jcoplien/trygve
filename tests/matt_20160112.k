context Activity {
    private String name_;

    public Activity(String name, int duration, int earlyStart) {
        name_ = name.clone();
        Duration = duration;
        EarlyStart = earlyStart;
    }

    stageprop Duration {} requires {
        int +(int time) const;
    }

    //earliest possible start time for this activity
    stageprop EarlyStart {} requires {
        int +(int duration) const;
    }

    public int earlyFinish() {
        return EarlyStart + Duration + 0;
    }

    public int earlyFinish2() {
        return EarlyStart + (Duration + 0);
    }

    public int earlyStart() {
        return EarlyStart + 0;
    }

    public String name() {
        return name_;
    }

    public int duration() {
        return Duration + 0;
    }
}

/* GOLD:
line 20: No such operation `+' on type `EarlyStart' for argument `Duration'.
line 20: Operation `+' cannot be applied to type `EarlyStart' for argument `Duration'.
line 38: Missing main expression.
___________________________________________________________
*/
