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
        List<String> lista = new ArrayList<>();
        for (JToggleButton b : botones) {
            if (b.isSelected()) {
                lista.add(b.getText());
            }
        }
        return lista;
    }

    public void bloquearAsiento(String asiento) {
        for (JToggleButton b : botones) {
            if (b.getText().equals(asiento)) {
                b.setEnabled(false);
                b.setSelected(false);
                b.setBackground(Color.GRAY);
            }
        }
    }

    public void desbloquearTodos() {
        for (JToggleButton b : botones) {
            b.setEnabled(true);
            b.setSelected(false);
            b.setBackground(Color.GREEN);
        }
    }

    public void seleccionarAsiento(String asiento) {
        for (JToggleButton b : botones) {
            if (!b.isEnabled()) {
                continue;
            }
            if (b.getText().equals(asiento)) {
                b.setSelected(true);
                b.setBackground(Color.RED);
            } else {
                b.setSelected(false);
                b.setBackground(Color.GREEN);
            }
        }
    }

    public void limpiarSeleccion() {
        for (JToggleButton b : botones) {
            if (b.isEnabled()) {

                b.setSelected(false);
                b.setBackground(Color.GREEN);
            }
        }
    }
}
