/*  
                      Mach and Speed of Sound Calculator
            Interactive Program to solve Standard Atmosphere Equations
                     for mach and speed of sound

                          A Java Applet

                     Version 1.1d   - 30 Apr 07

                         Written by Tom Benson
                       NASA Glenn Research Center

>                              NOTICE
>This software is in the Public Domain.  It may be freely copied and used in
>non-commercial products, assuming proper credit to the author is given.  IT
>MAY NOT BE RESOLD.  If you want to use the software for commercial
>products, contact the author.
>No copyright is claimed in the United States under Title 17, U. S. Code.
>This software is provided "as is" without any warranty of any kind, either
>express, implied, or statutory, including, but not limited to, any warranty
>that the software will conform to specifications, any implied warranties of
>merchantability, fitness for a particular purpose, and freedom from
>infringement, and any warranty that the documentation will conform to the
>program, or any warranty that the software will be error free.
>In no event shall NASA be liable for any damages, including, but not
>limited to direct, indirect, special or consequential damages, arising out
>of, resulting from, or in any way connected with this software, whether or
>not based on warranty, contract, tort or otherwise, whether or not injury
>was sustained by persons or property or otherwise, and whether or not loss
>was sustained from, or arose out of the results of, or use of, the software
>or services provided hereunder.

      New Test :
                * move the choice for units
                * add compute button
                  add temperature input

                                                     TJB 30 Apr 07

*/

import java.awt.*;
import java.lang.Math ;

public class Mach extends java.applet.Applet {

   double gama,alt,temp,press,vel ;
   double rgas, rho0, rho, a0, lrat, mach ;
   int lunits,inparam,ther,prs, planet, inptem ;
   int mode ;

   In in ;

   public void init() {

     setLayout(new GridLayout(1,1,0,0)) ;

     setDefaults () ;

     in = new In(this) ;

     add(in) ;

     computeMach() ;
  }
 
  public Insets insets() {
     return new Insets(10,10,10,10) ;
  }

  public void setDefaults() {
     inparam = 0 ;
     lunits = 0;
     mode = 0 ;
     planet = 0 ;
     inptem = 0 ;

     alt = 0.0 ;
     vel = 500. ;
  }

  public void computeMach() {
 
     if (planet == 0) {    // Earth  standard day
        rgas = 1718. ;                /* ft2/sec2 R */
        gama = 1.4 ;

        if (inptem == 0) {
           if (alt <= 36152.) {           // Troposphere
             temp = 518.6 - 3.56 * alt/1000. ;
           }
           if (alt >= 36152. && alt <= 82345.) {   // Stratosphere
             temp = 389.98 ;
           }
           if (alt >= 82345. && alt <= 155348.) {          
             temp = 389.98 + 1.645 * (alt-82345.)/1000. ;
           }
           if (alt >= 155348. && alt <= 175346.) {          
             temp = 508.788 ;
           }
           if (alt >= 175346. && alt <= 262448.) {          
             temp = 508.788 - 2.46888 * (alt-175346.)/1000. ;
           }
        }
     }

     if (planet == 1) {   // Mars - curve fit of orbiter data
        rgas = 1149. ;                /* ft2/sec2 R */
        gama = 1.29 ;

        if (inptem == 0) {
           if (alt <= 22960.) {
             temp = 434.02 - .548 * alt/1000. ;
           }
           if (alt > 22960.) {
             temp = 449.36 - 1.217 * alt/1000. ;
           }
        }
     }

     a0 = Math.sqrt(gama*rgas*temp) ;  // feet /sec
     a0 = a0 * 60.0 / 88. ;   // mph
                       // compute either mach or velocity 
     if (inparam == 0) {
          mach = vel/a0 ;
     }
     if (inparam == 1) {
          vel = mach * a0 ;
     }

     if (lunits == 0) {
        in.dn.o3.setText(String.valueOf(filter0(a0))) ;
        in.dn.o4.setText(String.valueOf(filter0(vel))) ;
     }
     if (lunits == 1) {
        in.dn.o3.setText(String.valueOf(filter0(a0  * .447))) ;
        in.dn.o4.setText(String.valueOf(filter0(vel * .447))) ;
     }
 
     in.dn.o5.setText(String.valueOf(filter3(mach))) ;
 
     if (mode == 0) loadInpt() ;
  }
 
  public void loadInpt() {
     if (lunits == 0) {
         if (inptem == 0) {
            in.up.o1.setText(String.valueOf(filter0(alt))) ;
         }
         if (inptem == 1) {
            in.up.o1.setText(String.valueOf(filter0(temp - 459.8))) ;
         }
         if (inparam == 0) {
            in.up.o2.setText(String.valueOf(filter0(vel))) ;
         }
         if (inparam == 1) {
            in.up.o2.setText(String.valueOf(filter3(mach))) ;
         }
     }
     if (lunits == 1) {
         if (inptem == 0) {
            in.up.o1.setText(String.valueOf(filter0(alt*.3048))) ;
         }
         if (inptem == 1) {
            in.up.o1.setText(String.valueOf(filter0(5.0*temp/9.0 -273. ))) ;
         }
         if (inparam == 0) {
            in.up.o2.setText(String.valueOf(filter0(vel*.447))) ;
         }
         if (inparam == 1) {
            in.up.o2.setText(String.valueOf(filter3(mach))) ;
         }
     }
  }

  public int filter0(double inumbr) {
     //  integer output
       float number ;
       int intermed ;

       intermed = (int) (inumbr) ;
       number = (float) (intermed);
       return intermed ;
  }
 
  public float filter3(double inumbr) {
     //  output only to .001
       float number ;
       int intermed ;
  
       intermed = (int) (inumbr * 1000.) ;
       number = (float) (intermed / 1000. );
       return number ;
  }

  public float filter5(double inumbr) {
     //  output only to .00001
       float number ;
       int intermed ;
  
       intermed = (int) (inumbr * 100000.) ;
       number = (float) (intermed / 100000. );
       return number ;
  }

  class In extends Panel {
     Mach outerparent ;
     Titl titl ;
     Up up ;
     Dn dn ;

     In (Mach target) {                           
        outerparent = target ;
        setLayout(new GridLayout(3,1,5,5)) ;

        titl = new Titl(outerparent) ;
        up = new Up(outerparent) ;
        dn = new Dn(outerparent) ;

        add(titl) ;
        add(up) ;
        add(dn) ;
     }

     public Insets insets() {
        return new Insets(5,5,0,0) ;
     }

     class Titl extends Panel {
        Label la ;
        In2 in2;

        Titl (Mach target) {                           
            outerparent = target ;
            setLayout(new GridLayout(2,1,0,0)) ;

            la = new Label("Mach and Speed of Sound Calculator", Label.CENTER) ;
            la.setForeground(Color.red) ;

            in2 = new In2(outerparent) ;
 
            add(la) ;
            add(in2) ;
        }
 
        class In2 extends Panel {
           Label lc,lb ;
           Choice plntch,untch ;

           In2 (Mach target) {                           
               outerparent = target ;
               setLayout(new GridLayout(2,4,0,0)) ;

               lb = new Label("Units:", Label.RIGHT) ;
               lb.setForeground(Color.red) ;
 
               lc = new Label("Planet:", Label.RIGHT) ;
               lc.setForeground(Color.red) ;
 
               plntch = new Choice() ;
               plntch.addItem("Earth") ;
               plntch.addItem("Mars");
               plntch.setBackground(Color.white) ;
               plntch.setForeground(Color.red) ;
               plntch.select(0) ;

               untch = new Choice() ;
               untch.addItem("Imperial") ;
               untch.addItem("Metric");
               untch.setBackground(Color.white) ;
               untch.setForeground(Color.red) ;
               untch.select(0) ;

               add(new Label(" ", Label.RIGHT)) ;
               add(lb) ;
               add(untch) ;
               add(new Label(" ", Label.RIGHT)) ;

               add(new Label(" ", Label.RIGHT)) ;
               add(lc) ;
               add(plntch) ;
               add(new Label(" ", Label.RIGHT)) ;
           }

           public boolean handleEvent(Event evt) {
               if(evt.id == Event.ACTION_EVENT) {
                  planet  = plntch.getSelectedIndex() ;
                  lunits  = untch.getSelectedIndex() ;

                  if (lunits == 0) {  // English units labels
                      dn.l3u.setText("mph") ;
                      dn.l4u.setText("mph") ;
                      if (inptem == 0) {
                          up.l1u.setText("feet") ;
                      }
                      if (inptem == 1) {
                          up.l1u.setText("degrees F") ;
                      }
                      if (inparam == 0) {
                           up.l2u.setText("mph") ;
                      }
                      if (inparam == 1) {
                           up.l2u.setText(" ") ;
                      }
                  }
                  if (lunits == 1) {  // Metric units labels
                      dn.l3u.setText("m/sec") ;
                      dn.l4u.setText("m/sec") ;
                      if (inptem == 0) {
                           up.l1u.setText("meters") ;
                      }
                      if (inptem == 1) {
                          up.l1u.setText("degrees C") ;
                      }
                      if (inparam == 0) {
                           up.l2u.setText("m/sec") ;
                      } 
                      if (inparam == 1) {
                           up.l2u.setText(" ") ;
                      }
                  }
 
                  mode = 0 ;
                  computeMach() ;
                  return true ;
               }
               else return false ;
           }
        }
     }

     class Up extends Panel {
        TextField o1,o2 ;
        Label l1,l1u,l2u, la ;
        Button bt1;
        Choice inptch, inch2 ;

        Up (Mach target) {                           
            outerparent = target ;
            setLayout(new GridLayout(3,4,0,0)) ;
    
            la = new Label("Input", Label.LEFT) ;
            la.setForeground(Color.red) ;

            o1 = new TextField() ;
            o1.setBackground(Color.white) ;
            o1.setForeground(Color.black) ;

            l1u = new Label(" feet ", Label.CENTER) ;
            l2u = new Label("mph", Label.CENTER) ;

            o2 = new TextField() ;
            o2.setBackground(Color.white) ;
            o2.setForeground(Color.black) ;
 
            inch2 = new Choice() ;
            inch2.addItem("Altitude") ;
            inch2.addItem("Temperature");
            inch2.select(0) ;

            inptch = new Choice() ;
            inptch.addItem("Speed") ;
            inptch.addItem("Mach");
            inptch.select(0) ;

            bt1 = new Button("COMPUTE") ;
            bt1.setBackground(Color.red) ;
            bt1.setForeground(Color.white) ;

            add(la) ;
            add(inch2) ;
            add(o1) ;
            add(l1u) ;

            add(new Label(" ", Label.RIGHT)) ;
            add(inptch) ;
            add(o2) ;
            add(l2u) ;

            add(new Label(" ", Label.RIGHT)) ;
            add(new Label("Press -> ", Label.RIGHT)) ;
            add(bt1) ;
            add(new Label(" ", Label.RIGHT)) ;
        }

        public Insets insets() {
           return new Insets(5,5,5,5) ;
        }

        public boolean action(Event evt, Object arg) {
            if(evt.target instanceof Choice) {
               this.handleProb(arg) ;
               return true ;
            }

            if(evt.target instanceof Button) {
               this.handleBut(evt) ;
               return true ;
            }

            else return false ;
        }
 
        public void handleProb(Object obj) {

            inparam = inptch.getSelectedIndex() ;
            inptem  = inch2.getSelectedIndex() ;

            if (lunits == 0) {  // English units labels
                if (inparam == 0) {
                     l2u.setText("mph") ;
                }
                if (inparam == 1) {
                     l2u.setText(" ") ;
                }
                if (inptem == 0) {
                     l1u.setText("feet") ;
                }
                if (inptem == 1) {
                     l1u.setText("degrees F ") ;
                }
            }
            if (lunits == 1) {  // Metric units labels
                if (inparam == 0) {
                     l2u.setText("m/sec") ;
                } 
                if (inparam == 1) {
                     l2u.setText(" ") ;
                }
                if (inptem == 0) {
                     l1u.setText("meters") ;
                }
                if (inptem == 1) {
                     l1u.setText("degrees C ") ;
                }
            }
 
            mode = 0 ;
            computeMach() ;
        }

        public void handleBut(Event evt) {
            Double V1,V2 ;
            double v1,v2 ;

            V1 = Double.valueOf(o1.getText()) ;
            v1 = V1.doubleValue() ;
            V2 = Double.valueOf(o2.getText()) ;
            v2 = V2.doubleValue() ;

            if (lunits == 0) {
                if (inptem == 0) {   // altitude input
                   if (v1 < 0.0) {
                      v1 = 0.0 ;
                      o1.setText(String.valueOf(filter0(v1))) ;
                   }
                   if (v1 >250000.0) {
                      v1 = 250000.0 ;
                      o1.setText(String.valueOf(filter0(v1))) ;
                   }
                   alt = v1 ;
                }
                if (inptem == 1) {   // temperature input
                   if (v1 < -150.0) {
                      v1 = -150.0 ;
                      o1.setText(String.valueOf(filter0(v1))) ;
                   }
                   if (v1 > 100.0) {
                      v1 = 100.0 ;
                      o1.setText(String.valueOf(filter0(v1))) ;
                   }
                   temp = v1 + 459.7 ;
                }
                if (inparam == 0) {
                   if (v2 < 0.0) {
                      v2 = 0.0 ;
                      o2.setText(String.valueOf(filter0(v2))) ;
                   }
                   if (v2 >17600.0) {
                      v2 = 17600.0 ;
                      o2.setText(String.valueOf(filter0(v2))) ;
                   }
                   vel = v2 ;
                }
                if (inparam == 1) {
                   if (v2 < 0.0) {
                      v2 = 0.0 ;
                      o2.setText(String.valueOf(filter0(v2))) ;
                   }
                   if (v2 >25.0) {
                      v2 = 25.0 ;
                      o2.setText(String.valueOf(filter0(v2))) ;
                   }
                   mach = v2 ;
                }
            }
            if (lunits == 1) {
                if (inptem == 0) {   // altitude input
                   if (v1 < 0.0) {
                      v1 = 0.0 ;
                      o1.setText(String.valueOf(filter0(v1))) ;
                   }
                   if (v1 >76200.0) {
                      v1 = 76200.0 ;
                      o1.setText(String.valueOf(filter0(v1))) ;
                   }
                   alt = v1 / .3048 ;
                }
                if (inptem == 1) {   // temperature input
                   if (v1 < -100.0) {
                      v1 = -100.0 ;
                      o1.setText(String.valueOf(filter0(v1))) ;
                   }
                   if (v1 > 20.0) {
                      v1 = 20.0 ;
                      o1.setText(String.valueOf(filter0(v1))) ;
                   }
                   temp = (v1 + 273.) * 9.0/5.0  ;
                }
                if (inparam == 0) {
                   if (v2 < 0.0) {
                      v2 = 0.0 ;
                      o2.setText(String.valueOf(filter0(v2))) ;
                   }
                   if (v2 >7867.0) {
                      v2 = 7867.0 ;
                      o2.setText(String.valueOf(filter0(v2))) ;
                   }
                   vel = v2 / .447 ;
                }
                if (inparam == 1) {
                   if (v2 < 0.0) {
                      v2 = 0.0 ;
                      o2.setText(String.valueOf(filter0(v2))) ;
                   }
                   if (v2 >25.0) {
                      v2 = 25.0 ;
                      o2.setText(String.valueOf(filter0(v2))) ;
                   }
                   mach = v2 ;
                }
            }

            mode = 1;
            computeMach() ;
        }
     }

     class Dn extends Panel {
        Mach outerparent ;
        TextField o3,o4,o5 ;
        Label l3,l3u,l4,l4u,l5 ;
        Label lb ;

        Dn (Mach target) {
            outerparent = target ;
            setLayout(new GridLayout(3,4,0,0)) ;
    
            lb = new Label("Output", Label.LEFT) ;
            lb.setForeground(Color.blue) ;

            l3 = new Label("Speed of Sound", Label.CENTER) ;
            l3u = new Label(" mph ", Label.CENTER) ;
   
            o3 = new TextField() ;
            o3.setBackground(Color.black) ;
            o3.setForeground(Color.yellow) ;

            l4 = new Label("Speed", Label.CENTER) ;
            l4u = new Label("mph", Label.CENTER) ;

            o4 = new TextField() ;
            o4.setBackground(Color.black) ;
            o4.setForeground(Color.yellow) ;
 
            l5 = new Label("Mach", Label.CENTER) ;

            o5 = new TextField() ;
            o5.setBackground(Color.black) ;
            o5.setForeground(Color.yellow) ;
 
            add(lb) ;
            add(l4) ;
            add(o4) ;
            add(l4u) ;

            add(new Label(" ", Label.RIGHT)) ;
            add(l3) ;
            add(o3) ;
            add(l3u) ;

            add(new Label(" ", Label.RIGHT)) ;
            add(l5) ;
            add(o5) ;
            add(new Label(" ", Label.RIGHT)) ;
        }
     }
  }
}
