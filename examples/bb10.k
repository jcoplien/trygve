// bb10.k

class Activity {
   public int duration() const { return duration_ }
   public void setDuration(int duration) { duration_ = duration.clone }
   public int earlyFinish() const {
      int retval = null;
      if (earlyStart_ != null)
         retval = earlyStart_ + duration() - 1;
     return retval
   }
   public void setEarlyStart(int earlyStart) {
      earlyStart_ = earlyStart.clone
   }
   public int lateFinish() const {
      return lateFinish_
   }
   public void setLateFinish(int lateFinish) {
      lateFinish_ = lateFinish.clone
   }
   public int lateStart() const {
      int retval = null;
      if (lateFinish() != null)
         retval = lateFinish() - duration() + 1;
      return retval
   }
   public String name() {
      return name_
   }
   public int plannedFinish() const {
      int retval = null;
      if (plannedStart() != null) 
         retval = plannedStart() + duration() - 1;
      return retval
   }
   public int plannedStart() const {
      return plannedStart_
   }
   public void setPlannedStart(int plannedStart) {
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

   public int earliestStart() const {
      return Math.min(earlyStart_, lateStart())
   }
   public int latestFinish() const {
      return Math.max(earlyFinish(), lateFinish())
   }
   public void printOn(PrintStream stream) {
      // super printOn(stream)
      stream.print("[").print(name()).println("]")
   }

   // ------------------------------------------

   private void initialize() {
      // super initialize.
      duration_ = earlyStart_ = lateFinish_ = plannedStart_ =
         resourceRequirement_ = null
   }
   private Activity(String name, int duration,
                    int resourceRequirement, String details) {
      name_ = name_.clone;
      duration_ = duration.clone;
      resourceRequirement_ = resourceRequirement.clone;
      text_ = details.clone;
      this.changed("properties")
   }

   // -------------------------------------------

   public Activity(String name) {
      name_ = name.clone;
      text_ = "Further details about the activity named " + name()
   }

   private void changed(String what) {
      assert (false, "Pure virtual activity.changed(String) called")
   }
   

   int duration_;
   int earlyStart_;
   int lateFinish_;
   int plannedStart_;
   String name_;
   int resourceRequirement_;
   String text_
}

class Dependency extends Object {
   public Activity fromActivity() const {
      return fromActivity_
   }
   public Activity toActivity() const {
      return toActivity_
   }
   public Dependency(Activity from, Activity to) {
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

   Activity fromActivity_;
   Activity toActivity_
}

class Resource {
   public Map<int, List<Activity> > allocations() {
      return allocations_
   }
   public List<Activity> allocationsAt(int weekNo) {
      if (allocations_.containsKey(weekNo) == false)
         allocations_.put(weekNo, new List<Activity>());
      return allocations_.get(weekNo)
   }
   public int maxHeight() {
      int max = 0;
      for (List<Activity> list : allocations_) {
         max = Math.max(max, list.size())
      }
      return max
   }
   public void reset() {
      allocations_ = new Map<int, List<Activity> >();
   }
   public void allocateActivityFromWeek(Activity activity, int weekNo) {
      assert (false, "Method moved to Interaction.")
   }
   public Resource() {
      name_ = "The only Resource";
      allocations_ = new Map<int, List<Activity> >()
   }

   String name_;
   Map<int, List<Activity> > allocations_;
}




