class ComplexNumber {
    private ComplexNumber constant_;

    public ComplexNumber(double real, double im) {
        real_ = real;
        im_ = im;
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
        // NOTE: Silent error when comparing im() to 0 instead of 0.0
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

class Julia {
    private ComplexNumber constant_;

    public Julia(ComplexNumber constant) {
        constant_ = constant;
    }

    public ComplexNumber compute(ComplexNumber Z, ComplexNumber pos) const {
        ComplexNumber squared = Z.square();
        return new ComplexNumber(
            // NOTE: Must add "this" to the "constant" calls:
            squared.real() + constant_.real(), 
            squared.im() + constant_.im()
        );
    }
}

context Fractal {
    stageprop Set {} requires {
        ComplexNumber compute(ComplexNumber Z, ComplexNumber pos) const;
    }

    public Fractal(int width, int height, Set set) {
        width_ = width;
        height_ = height;
        Set = set;

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
                    // NOTE: Error when using Set here
                    Z = Set.compute(Z, pos);
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
//    new Fractal(110, 60, new Julia(new ComplexNumber(-0.1, 0.8))).display();
//    new Fractal(110, 60, new Julia(new ComplexNumber(-0.79, 0.15))).display();
}

/* GOLD:
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
