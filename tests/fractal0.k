class ComplexNumber {
    private ComplexNumber constant_;

    public ComplexNumber(double real, double im) {
        real_ = real.clone;
        im_ = im.clone;
    }

    public double real() const {
        return real_;
    }

    public double im() const {
        return im_;
    }

    public ComplexNumber square() const {
        double newReal = real() * real() - im() * im();
        double newImag = 2.0 * real() * im();

        return new ComplexNumber(newReal, newImag);
    }

    public double abs() {
        return Math.sqrt(real() * real() + im() * im());
    }

    public String toString() {
        String sign = if(im() >= 0.0) "+" else "";
        return real().toString() + sign + im().toString() + "im";
    }

    private double real_;
    private double im_;
}

class Mandelbrot {
    public Mandelbrot() {}

    public ComplexNumber compute(ComplexNumber Z, ComplexNumber pos) const {
        ComplexNumber squared = Z.square();
        return new ComplexNumber(
            squared.real() + pos.real(), 
            squared.im() + pos.im()
        );
    }
}

context Fractal {
    stageprop TheSet {
        public ComplexNumber recompute(ComplexNumber Z, ComplexNumber pos) const {
           return compute(Z, pos)
        }
    } requires {
        ComplexNumber compute(ComplexNumber Z, ComplexNumber pos) const;
    }

    public Fractal(int width, int height, TheSet set) {
        width_ = width.clone;
        height_ = height.clone;
        TheSet = set;

        // Thanks for the shades: http://larc.unt.edu/ian/art/ascii/shader/
        shades_ = " .:*I$VFNM";
    }

    public void display() {
        double alphax = 4.0 / width_;
        double alphay = 4.0 / height_;
        int total = 0;
        int maxIterations = shades_.length() - 1;

        for(double y = 2.0; y >= -2.0; y = y - alphay) {
            for(double x = -2.0; x <= 2.0; x = x + alphax) {
                ComplexNumber pos = new ComplexNumber(x, y);
                ComplexNumber Z = pos;
                int iterations = 0;

                while(Z.abs() < 2.0) {
                    Z = TheSet.recompute(Z, pos);
                    iterations = iterations + 1;
                    if(iterations == maxIterations) break;
                }

                System.out.print(shades_.substring(iterations, iterations+1));
                total = total + 1;
                if(total % width_ == 0) System.out.println();
            }
        }
    }

    private int width_;
    private int height_;
    private String shades_;
}

{
    new Fractal(35, 18, new Mandelbrot()).display();
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________

                                   
          ................         
       ......................      
     ..........................    
   ...::::::::**VI*::............  
  ..:::::::***IVMM$I*::........... 
  ::::::***INMMMMMMNV$*::......... 
 ::::*I$I$$FMMMMMMMMMFI:::.........
 ***IIVMMMMMMMMMMMMMMMI*:::........
 MMMMMMMMMMMMMMMMMMMMVI*:::........
 ***IIVMMMMMMMMMMMMMMMI*:::........
 ::::*I$I$$FMMMMMMMMMFI:::.........
  ::::::***INMMMMMMNV$*::......... 
  ..:::::::***IVMM$I*::........... 
   ...::::::::**VI*::............  
     ..........................    
       ......................      
          ................         
                                   
*/
