class TestSimulatedAnnealing {
    //method to test boltzmann method
    static String testBoltzmann(){
        //did the boltzmann formula on my calculator and got the answer that the method is checking against
        //checked it against an online calculator as well just to be safe
        if(Math.abs(SimulatedAnnealing.boltzmann(100, 6, 5) - 0.367879441) < 1e-4){
            return "boltzmann method is working";
        }else{
            return "error in method boltzmann";
        }
    }
}
