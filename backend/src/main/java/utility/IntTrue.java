package utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import utility.utilityLog.LogFactory;

import java.util.InputMismatchException;

@Component
public class IntTrue {



    public int intTrue() {
        int number = 0;
        boolean rule = false;
        while (!rule) {
            try {
                number = ScannerThis.getInstance().nextInt();
                ScannerThis.getInstance().nextLine();
                rule = true;
            } catch (InputMismatchException e) {
                LogFactory.warning(this.getClass().getName(), "Entered a symbol instead of a number", e.getStackTrace());
                System.out.println("Необхідно ввести число, а не символи. Спробуйте знову!");
                ScannerThis.getInstance().nextLine();
            }
        }
        return number;
    }
}
