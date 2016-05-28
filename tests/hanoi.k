class Disk {
    public Disk(int size) {
        size_ = size.clone;
    }

    public int size() const { return size_ }
    private int size_;
}

class Rod {
    public Rod() {
        disks_ = "";
    }

    public void add(Disk disk) {
        assert(disk.size() > 0 && disk.size() < 10, 
            "Disk size must be between 1-9, was " + disk.size().toString());

        disks_ = disks_ + disk.size().toString();
    }

    public Disk get(int index) {
        String disk = disks_.substring(index, index+1);

        // NOTE: Stack corruption error here
        return switch(disk) {
            case "1": new Disk(1); break;
            case "2": new Disk(2); break;
            case "3": new Disk(3); break;
            case "4": new Disk(4); break;
            case "5": new Disk(5); break;
            case "6": new Disk(6); break;
            case "7": new Disk(7); break;
            case "8": new Disk(8); break;
            case "9": new Disk(9); break;
            default: 
                assert(false, "Disk size must be between 1-9, was " + disk);
                new Disk(0);
                break;
        }
    }

    public int size() const { return disks_.length(); }
    public String toString() const { return disks_; }

    public Disk remove(int index) {
        int len = disks_.length();
        Disk disk = get(index);

        if(len == 1) {
            disks_ = "";
            return disk;
        }

        if(index == 0) {
            disks_ = disks_.substring(1, len)
            return disk;
        }
        
        if(index == len-1) {
            disks_ = disks_.substring(0, index);
            return disk;
        }

        disks_ = disks_.substring(0, index) + disks_.substring(index, len);
        return disk;
    }

    public void moveDiskTo(Rod to) {        
        assert(this.size() > 0, "Tried to remove a disk from an empty Rod.");

        Disk fromTop = this.remove(this.size()-1);
        if(to.size() > 0) {
            Disk toTop = to.get(to.size()-1);
            assert(fromTop.size() < toTop.size(), "Tried to put a larger disk on top of a smaller.");
        }

        to.add(fromTop);
    }

    private String disks_;
    private String name_;
}

class TowerOfHanoi {
    public TowerOfHanoi() {}
    
    public void solve(int disks) {
        assert(disks > 0, "Number of disks must be at least one.");

        source_ = new Rod();
        spare_ = new Rod();
        dest_ = new Rod();
        moves = 0;

        while(disks > 0) {
            source_.add(new Disk(disks--));
        }

        print();
        move(source_.size(), source_, dest_, spare_);

        System.out.println("Completed in " + moves.toString() + " moves.");
    }

    public void print() {
        System.out.println("|" + source_.toString());
        System.out.println("|" + spare_.toString());
        System.out.println("|" + dest_.toString());
        System.out.println();
    }

    public void move(int disk, Rod source, Rod dest, Rod spare) {
        moves++;
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
0 warnings, 0 errors.
___________________________________________________________
|321
|
|

|32
|
|1

|3
|2
|1

|3
|21
|

|
|21
|3

|1
|2
|3

|1
|
|32

|
|
|321

Completed in 7 moves.
*/
