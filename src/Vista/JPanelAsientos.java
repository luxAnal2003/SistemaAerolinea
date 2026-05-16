package Vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JPanelAsientos extends JPanel {

    private List<JToggleButton> botones = new ArrayList<>();
    private int capacidad = 20;

    public JPanelAsientos() {
        setLayout(new GridLayout(4, 5, 5, 5));
        generarAsientos();
    }

    private void generarAsientos() {

        for (int i = 1; i <= capacidad; i++) {

            JToggleButton btn = new JToggleButton("A" + i);

            btn.setBackground(Color.GREEN);

            btn.addActionListener(e -> {

                if (btn.isSelected()) {
                    btn.setBackground(Color.RED);
                } else {
                    btn.setBackground(Color.GREEN);
                }
            });

            botones.add(btn);
            add(btn);
        }
    }

    public List<String> getAsientosSeleccionados() {

        List<String> seleccionados = new ArrayList<>();

        for (JToggleButton b : botones) {
            if (b.isSelected()) {
                seleccionados.add(b.getText());
            }
        }

        return seleccionados;
    }

    public void bloquearAsiento(String asiento) {

        for (JToggleButton b : botones) {
            if (b.getText().equals(asiento)) {
                b.setEnabled(false);
                b.setBackground(Color.GRAY);
            }
        }
    }
}