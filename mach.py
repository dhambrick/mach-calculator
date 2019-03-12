import numpy as np 

#                             Mach Calculator
#                Adapted from Java Code written by:
#                             Tom Benson 
#                     Nasa Glenn Research Center
def computeMach(altitude,velocity):
    #altitude is in ft
    #velocity is in mph
   
    rGas = 1718 #ft^2/(s^2 * R)
    gama = 1.4

    if(altitude <= 36152):
        temp = 518.6 - 3.56 * altitude/1000
    
    elif ((altitude >= 36152.0) & (altitude <= 82345.0)):
        temp = 389.98
    
    elif ((altitude >= 82345.0) & (altitude <= 155348.0)):
        temp = 389.98 + 1.645 * (altitude-82345.0)/1000.0 
    
    elif ((altitude >= 155348.0) & (altitude <= 175346.0)):
        temp = 508.788    
    
    elif ((altitude >= 175346.0) & (altitude <= 262448.0)):
        temp = 508.788 - 2.46888 * (altitude-175346.0)/1000.0
    else:
        print("Invalid altitude")
        return None
   
    a0 = np.sqrt(gama*rGas*temp) # ft/sec
    a0 = a0 * (60.0/88.0) # mph

    mach = velocity/a0

    return mach