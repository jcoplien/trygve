class Disk {
    public Disk(int size) {
        size_ = size;
    }

    public int size() const { return size_ }
    private int size_;
}

class Rod {
    public Rod() {
        disks_ = new List<Disk>();
    }

    public Rod(List<Disk> disks) {
        disks_ = disks;
    }

    public int numberOfDisks() const { return disks_.size(); }

    public String toString() const { 
        String output = "";
        for(Disk d : disks_) {
            output = output + d.size().toString();
        }
        return output;
    }

    public String toString2() const { 
        String output = "> ";
        for(Disk d : disks_) {
            output = output + d.size().toString();
        }
        return output;
    }

    public void add(Disk disk) {
        if(!disks_.isEmpty()) {
            int topDiskSize = disks_.get(disks_.size()-1).size();
            assert(disk.size() < topDiskSize,
                "Tried to put a larger disk on top of a smaller.");
        }

        disks_.add(disk);
    }

    public void moveDiskTo(Rod to) {        
        assert(disks_.size() > 0, "Tried to remove a disk from an empty rod.");
        Disk topDisk = disks_.remove(disks_.size()-1);
        to.add(topDisk);
    }

    private List<Disk> disks_;
}

class TowerOfHanoi {
    public TowerOfHanoi() {}
    
    public void solve(int disks) {
        assert(disks > 0, "Number of disks must be at least one.");

        source_ = new Rod();
        spare_ = new Rod();
        dest_ = new Rod();
        moves = 0;

        int i = disks;
        while(i > 0) {
           source_.add(new Disk(i--));
        } 

        print();
        move(disks, source_, dest_, spare_);

        System.out.println("Completed in " + moves.toString() + " moves.");
    }

    public void print() {
        System.out.println("|" + source_.toString());
        System.out.println("|" + spare_.toString());
        System.out.println("|" + dest_.toString());
        System.out.println();;
    }

    public void move(int disk, Rod source, Rod dest, Rod spare) {
        moves++;
        assert (moves < 10); 	// safety valve, or you will loop forever
        if(disk == 1) {
            source.moveDiskTo(dest);
            print();
        } else {
            move(disk-1, source, spare, dest);
            source.moveDiskTo(dest);
            print();
            move(disk-1, spare, dest, source);
        }
    }

    private int moves;
    private Rod source_;
    private Rod spare_;
    private Rod dest_;
}

{
    new TowerOfHanoi().solve(3);
}

/* GOLD:
line 67: WARNING: Initialization does not create a new instance. Both `i' and `disks' will refer to the same object. Use `disks.clone' to create a separate instance.
___________________________________________________________
|321
|
|
Line 87: Assertion failed.
*/
