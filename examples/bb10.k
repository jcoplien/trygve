// bb10.k

interface Time {
   public int compareTo(int other) const;
   public int +(int other);
   public int -(int other);
   public int toInteger() const;
   public int compareTo(int other) const
}

interface ActivityAsPredecessor {
   public String name() const
}

interface ActivityAsSuccessor {
   public String name() const
}

class DBaseType {
   public DBaseType dBase() {
      assert(false, "Unimplemented");
      return null
   }
   public int projectFinish() {
      assert(false, "Unimplemented");
      return 0
   }
   public List<ActivityClass> successorsOf(ActivityAsPredecessor activity) {
      assert(false, "Unimplemented");
      return null
   }
   public ActivityClass predecessorOf(ActivityAsSuccessor activity) {
      assert(false, "Unimplemented");
      return null
   }
   public List<ActivityClass> allActivities() {
      assert(false, "Unimplemented");
      return null
   }
}

class ActivityClass implements ActivityAsPredecessor, ActivityAsSuccessor {
   public int duration() const { Time test; test = 1; return duration_ }
   public void setDuration(int duration) { duration_ = duration.clone }
   public Time earlyFinish() const {
      Time retval = null;
      if (earlyStart_ != null)
         retval = earlyStart_ + duration() - 1;
     return retval
   }
   public void setEarlyStart(Time earlyStart) {
      earlyStart_ = earlyStart.clone
   }
   public Time lateFinish() const {
      return lateFinish_
   }
   public void setLateFinish(Time lateFinish) {
      lateFinish_ = lateFinish.clone
   }
   public Time lateStart() const {
      Time retval = null;
      if (lateFinish() != null)
         retval = lateFinish() - duration() + 1;
      return retval
   }
   public String name() { return rawName() }
   public String rawName() {
      return name_
   }
   public Time plannedFinish() const {
      Time retval = null;
      if (plannedStart() != null) 
         retval = plannedStart() + duration() - 1;
      return retval
   }
   public Time plannedStart() const {
      return plannedStart_
   }
   public void setPlannedStart(Time plannedStart) {
      plannedStart_ = plannedStart.clone
   }
   public int resourceRequirement() const {
      return resourceRequirement_
   }
   public void setResourceRequirement(int resourceRequirement) {
      resourceRequirement_ = resourceRequirement.clone
   }
   public String text() {
      if (text_ == null)
         text_ = "Further details about the activity named " + rawName();
      return text_
   }
   public void setText(String text) {
      text_ = text.clone
   }

   // ---------------------------------------------

   public Time earliestStart() const {
      return Math.min(earlyStart_.toInteger(), lateStart().toInteger())
   }
   public Time latestFinish() const {
      return Math.max(earlyFinish().toInteger(), lateFinish().toInteger())
   }
   public void printOn(PrintStream stream) {
      // super printOn(stream)
      stream.print("[").print(rawName()).println("]")
   }

   // ------------------------------------------

   private void initialize() {
      // super initialize.
      duration_ = 0;
      earlyStart_ = lateFinish_ = plannedStart_ = null;
      resourceRequirement_ = null
   }
   private ActivityClass(String name, int duration,
                    int resourceRequirement, String details) {
      name_ = name_.clone;
      duration_ = duration.clone;
      resourceRequirement_ = resourceRequirement.clone;
      text_ = details.clone;
      this.changed("properties")
   }

   // -------------------------------------------

   public ActivityClass(String name) {
      name_ = name.clone;
      text_ = "Further details about the activity named " + name_
   }

   private void changed(String what) {
      assert (false, "Pure virtual activity.changed(String) called")
   }
   

   int duration_;
   Time earlyStart_;
   Time lateFinish_;
   Time plannedStart_;
   String name_;
   int resourceRequirement_;
   String text_
}

class Dependency extends Object {
   public ActivityClass fromActivity() const {
      return fromActivity_
   }
   public ActivityClass toActivity() const {
      return toActivity_
   }
   public Dependency(ActivityClass from, ActivityClass to) {
      // Dependency fromActivity: activity1 toActivity: activity2
      fromActivity_ = from;
      toActivity_ = to
   }
   public void printOn(PrintStream stream) {
      // super printOn(stream)
      stream.print(" (");
      fromActivity_.printOn(stream);
      stream.print("->");
      toActivity_.printOn(stream);
      stream.print(")")
   }

   ActivityClass fromActivity_, toActivity_
}

class Resource {
   public Map<int, List<ActivityClass> > allocations() {
      return allocations_
   }
   public List<ActivityClass> allocationsAt(int weekNo) {
      if (allocations_.containsKey(weekNo) == false)
         allocations_.put(weekNo, new List<ActivityClass>());
      return allocations_.get(weekNo)
   }
   public int maxHeight() {
      int max = 0;
      for (List<ActivityClass> list : allocations_) {
         max = Math.max(max, list.size())
      }
      return max
   }
   public void reset() {
      allocations_ = new Map<int, List<ActivityClass> >();
   }
   public void allocateActivityFromWeek(ActivityClass activity, int weekNo) {
      // Simplest possible allocation assuming infinite capacity.
      assert(false, "Method moved to interaction")
   }
   public Resource() {
      name_ = "The Only Resource";
      allocations_ = new Map<int, List<ActivityClass> >()
   }

   String name_;
   Map<int, List<ActivityClass> > allocations_;
}

context BackloadCtx {
   public Time projectFinish() {
     return this.projectFinish_
   }
   private boolean lateStartIsNilDetector(Activity act) {
      boolean retval = true;
      for (Activity b : DBaseRole.dbaseSuccessorsOf(act)) {
         Activity predecessor = b.pred();
         if (predecessor.activityLateStart() == null) {
            retval = false;
            break
         }
      }
      return retval
   }
   role DBaseRole {
      public List<ActivityClass> dbaseAllActivities() {
         return allActivities()
      }
      public int dbaseProjectFinish() {
         return projectFinish()
      }
      public List<ActivityClass> dbaseSuccessorsOf(ActivityAsPredecessor activity) {
         return successorsOf(activity)
      }
      public ActivityClass dbasePredecessorOf(ActivityAsPredecessor activity){
         return predecessorOf(activity)
      }
   } requires {
      int projectFinish();
      List<ActivityClass> successorsOf(ActivityAsPredecessor activity);
      List<ActivityClass> allActivities();
      ActivityClass predecessorOf(ActivityAsSuccessor activity)
   }
   public List<ActivityClass> allActivities() {
      assert (false, "Unimplemented")
      return null
   }
   role Activity {
      public void backload() {
         this.setLateFinish(ProjectFinish);
         Activity minSucc = Successors.findMinLateStart();
         if (minSucc != null) {
            setLateFinish(minSucc.lateStart() - 1)
         }
      }
      public Activity pred() {
         return DBaseRole.dbasePredecessorOf(this)
      }
      public Time activityLateStart() {
         return lateStart()
      }
      public Time activityLateFinish() {
         return lateFinish()
      }
      public void activitySetLateFinish(ProjectFinish act) {
         setLateFinish(act)
      }
      public String name() {
         return rawName()
      }
   } requires {
      void setLateFinish(Time finish);
      Time lateStart() const;
      Time lateFinish() const;
      String rawName();
   }
   role AllActivities {
      public int count() { return size(); }
      public Activity getActivityAt(int i) { return get(i) }
   } requires {
      int size() const;
      Activity get(int i) const
   }
   role Backloader {
      public Time backload() {
         Time projectStart;
         for (int i = 0; i < AllActivities.count(); i++) {
            Activity act = AllActivities.getActivityAt(i);
            act.activitySetLateFinish(null)
         }
         projectStart = ProjectFinish;
         do {
            this.remap();
            if (Activity == null) break;
            Activity.backload();
            projectStart = Math.min(projectStart.toInteger(),
                               Activity.activityLateStart().toInteger() - 1)
         } while (Activity != null);
         return projectStart
      }
   } requires {
      void remap()
   }

   role ProjectFinish {
   } requires {
      int compareTo(int other) const;
      int +(int other);
      int -(int other);
      int toInteger() const
   }

   role Successors {
      public Activity findMinLateStart() {
         Time min = 999999;
         Activity minStartActivity = null;
         for (int i = 0; i < size(); i++) {
            Activity current = get(i);
            if (current.activityLateStart().toInteger() < min.toInteger()) {
               min = current.activityLateStart();
               minStartActivity = current
            }
         }
         return minStartActivity;
      }
   } requires {
      Activity get(int i) const;
      int size() const;
   }
   public void remap() {	// have to make public to play the Role
      Activity = activity();
      AllActivities = DBaseRole.dbaseAllActivities();
      Backloader = this;
      ProjectFinish = DBaseRole.dbaseProjectFinish();
      Successors = DBaseRole.dbaseSuccessorsOf(Activity)
   }
   public void backloadProject() {
      lateProjectStart_ = this.triggerInteractionFromWith(Backloader, "backload")
   }
   public Activity activity() {
      Activity retval = null;
      List<Activity> acta = DBaseRole.dbaseAllActivities().clone();
      for (Activity act : acta) {
         if (act.activityLateFinish() == null) {
            if (lateStartIsNilDetector(act)) {
               retval = act; break
            }
         }
      }
      return retval
   }

   Time projectStart_, projectFinish_, lateProjectStart_
}

context FrontloadCtx {
   role DBaseRole {
      public List<ActivityClass> dbaseAllActivities() {
         return allActivities()
      }
      public int dbaseProjectFinish() {
         return projectFinish()
      }
      public List<ActivityClass> dbaseSuccessorsOf(ActivityAsPredecessor activity) {
         return successorsOf(activity)
      }
      public ActivityClass dbasePredecessorOf(ActivityAsPredecessor activity){
         return predecessorOf(activity)
      }
   } requires {
      int projectFinish();
      List<ActivityClass> successorsOf(ActivityAsPredecessor activity);
      List<ActivityClass> allActivities();
      ActivityClass predecessorOf(ActivityAsSuccessor activity)
   }

   role Frontloader {
      public Time frontload() {
      for (Activity act : AllActivities) {
        act.setEarlyStart(null)
      }
      Time projectFinish = ProjectStart;
      this.remap()
      while (Activity != null) {
         Activity.frontLoad();
         projectFinish = Math.max(projectFinish, Activity.earlyFinish())
      }
      return projectFinish
      }
   } requires {
   }

   role Activity {
      public Time frontload() {
         this.setEarlyStart(ProjectStart);
         Time maxEarlyFinish = 0;
         Activity maxPred = null;
         List<Activity> predecessors = Predecessors;
         for (Activity pred : predecessors) {
            if (pred.earlyFinish() > maxEarlyFinish) {
               maxPred = pred;
               maxEarlyFinish = pred.earlyFinish()
            }
         }
         if (maxPred != null) {
            this.setEarlyStart(maxPred.earlyFinish() + 1)
         }
         return this.earlyFinish()
      }
   } requires {
      Time earlyFinish();
      void setEarlyStart(Time t);
   }

   public Activity activity() {
      Activity retval = null;
      List<Activity> acta = DBaseRole.dbaseAllActivities().clone();
      for (Activity act : acta) {
         if (act.activityEarlyStart() == null) {
            if (earlyFinishIsNilDetector(act)) {
               retval = act; break
            }
         }
      }
      return retval
   }

   private boolean earlyFinishIsNilDetector(Activity act) {
      boolean retval = true;
      for (Activity b : DBaseRole.dbaseSuccessorsOf(act)) {
         Activity predecessor = b.pred();
         if (predecessor.activityEarlyFinish() == null) {
            retval = false;
            break
         }
      }
      return retval
   }

   public void remap() {
      Activity = activity();
      AllActivities = DBaseRole.dbaseAllActivities();
      Frontloader = this;
      ProjectStart = projectStart;
      Predecessors = DBaseRole.dbasePredessorsOf(Activity)
   }

   public frontloadProjectFrom(Time startWeek) {
      Time projectFinish;
      projectStart = startWeek.;
      dBase.setProjectStart(startWeek);
      projectFinish = this.triggerIneractionFromWith(Frontloader,
          "frontload");
      dBase.setProjectFinish(projectFinish)
   }
   

   Time projectStart_, projectEnd_;
}
