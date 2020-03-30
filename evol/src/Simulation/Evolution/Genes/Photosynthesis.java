package Simulation.Evolution.Genes;

import Simulation.System.Command;
import Simulation.System.Host;

import java.io.*;

public class Photosynthesis implements Command,Cloneable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }



    @Override
    public void doStuff(Host host) {
        host.changeEnergy((int) (Math.random() * 10));
        System.out.println("energy increase");
        System.out.println(host.getEnergy());


    }

    @Override
    public Command copy() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(baos);
        ous.writeObject(this);
        ous.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Command) ois.readObject();
    }

}
