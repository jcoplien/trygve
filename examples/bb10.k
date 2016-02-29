// bb10.k

interface Time {
   public int compareTo(int other);
   public int +(int other);
   public int -(int other);
   public int toInteger() const;
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
   public List<ActivityClass> successorsOf(ActivityClass activity) {
      assert(false, "Unimplemented");
      return null
   }
   public ActivityClass predecessorOf(ActivityClass activity) {
      assert(false, "Unimplemented");
      return null
   }
   public List<ActivityClass> allActivities() {
      assert(false, "Unimplemented");
      return null
   }
}

class ActivityClass {
   public int duration() const { return duration_ }
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
   public String name() {
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
         text_ = "Further details about the activity named " + name();
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
      stream.print("[").print(name()).println("]")
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
      text_ = "Further details about the activity named " + name()
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
   public DBaseType dBase() {
      assert (false, "Unimplemented");
      return null
   }
   public Time projectFinish() {
     return this.projectFinish_
   }
   private boolean lateStartIsNilDetector(Activity act) {
      boolean retval = true;
      for (Activity b : this.dBase().successorsOf(act)) {
         Activity predecessor = b.pred();
         if (predecessor.activityLateStart() == null) {
            retval = false;
            break
         }
      }
      return retval
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
         return dBase.predecessorOf(this)
      }
      public Time activityLateStart() {
         return lateStart()
      }
      public void activitySetLateFinish(ProjectFinish act) {
         setLateFinish(act)
      }
   } requires {
      void setLateFinish(Time finish);
      Time lateStart() const;
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
         int min = 999999;
         Activity minStartActivity = null;
         for (int i = 0; i < size(); i++) {
            Activity current = get(i);
            if (current.activityLateStart() < min) {
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
      AllActivities = this.dBase().allActivities();
      Backloader = this;
      ProjectFinish = this.dBase().projectFinish();
      Successors = this.dBase().successorsOf(Activity)
   }
   public void backloadProject() {
      lateProjectStart_ = this.triggerInteractionFromWith(Backloader, "backload")
   }
   public Activity activity() {
      Activity retval = null;
      List<Activity> acta = this.dBase().allActivities().clone();
      for (Activity act : acta) {
         if (act.lateFinish() == null) {
            if (lateStartIsNilDetector(act)) {
               retval = act; break
            }
         }
      }
      return retval
   }

   Time projectStart_, projectFinish_, lateProjectStart_
}


