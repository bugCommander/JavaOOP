package Simulation.Evolution.Genes;

import Simulation.System.Command;
import Simulation.System.Host;

import java.io.*;

public class Rotate implements Command {

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void doStuff(Host host) {
        host.setRotation((int) (Math.random() * 8));
        System.out.println(host.getRotation());
        host.changeEnergy(-5);

    }

    @Override
    public Command copy() throws IOException, ClassNotFoundException {
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(baos);
            ous.writeObject(this);
            ous.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);

            return (Command) ois.readObject();
        }
    }
}

