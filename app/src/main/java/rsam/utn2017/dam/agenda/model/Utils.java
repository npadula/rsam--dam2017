package rsam.utn2017.dam.agenda.model;

/**
 * Created by npadula on 16/1/2018.
 */

public class Utils {
    public void iniciarListas() {

    }

    public Usuario[] getListaPersonas() {
        Usuario[] usrs = new Usuario[5];


        usrs[0]=new Usuario(1,"Juan Perez", "1234");
        usrs[1]=new Usuario(2,"John Doe", "456");
        usrs[2]=new Usuario(3,"Camila Diaz", "777");
        usrs[3]=new Usuario(4,"Nick Warren", "323");
        usrs[4]=new Usuario(5,"Hernan Cattaneo", "4477");

        return usrs;
    }
}
