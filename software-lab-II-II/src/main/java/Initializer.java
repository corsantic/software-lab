import java.util.List;

import entity.Node;
import entity.Patient;
import tree.BinaryTreeUI;
import tree.DecisionTreeMaker;
import util.FileHelper;

public class Initializer
{
    private FileHelper fileHelper = new FileHelper();
    private DecisionTreeMaker decisionTreeMaker = new DecisionTreeMaker();

    public static void main(String[] args)
    {
        new Initializer().run();
    }

    private void run()
    {
        List<Patient> patients = fileHelper.readAllPatients();
        Node decisionTree = decisionTreeMaker.buildTree(patients);

        new BinaryTreeUI(decisionTree);
    }
}

