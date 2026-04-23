import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 *Deivid Mateo Cañan Pretel - Maria Camila Melo 
 */
public class ArbolGUI extends JFrame {
    private ArbolBinarioBusqueda<Integer> arbol;
    private PanelArbol panelArbol;
    private JTextField inputDato;
    private JTextArea textResultados;
    private JLabel labelInfo;

    public ArbolGUI() {
        arbol = new ArbolBinarioBusqueda<>();
        setTitle("🌳 Árbol Binario de Búsqueda - Todas las Operaciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setResizable(true);

        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(new Color(240, 248, 255));

        // Panel izquierdo: Controles
        JPanel panelControles = crearPanelControles();

        // Panel central: Visualización del árbol
        panelArbol = new PanelArbol(arbol);
        JScrollPane scrollArbol = new JScrollPane(panelArbol);
        scrollArbol.setBorder(new TitledBorder("📊 Visualización del Árbol"));

        // Panel derecho: Información y resultados
        JPanel panelDerecha = crearPanelDerecha();

        // Agregar a la ventana principal
        panelPrincipal.add(panelControles, BorderLayout.WEST);
        panelPrincipal.add(scrollArbol, BorderLayout.CENTER);
        panelPrincipal.add(panelDerecha, BorderLayout.EAST);

        add(panelPrincipal);
        setVisible(true);
    }

    private JPanel crearPanelControles() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder("🎮 Controles"));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(250, 0));

        // Input de datos
        panel.add(createLabel("Ingresar Número:"));
        inputDato = new JTextField();
        inputDato.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        inputDato.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(inputDato);
        panel.add(Box.createVerticalStrut(10));

        // Botón Agregar
        JButton btnAgregar = createButton("➕ Agregar", new Color(34, 139, 34), e -> {
            try {
                int valor = Integer.parseInt(inputDato.getText());
                arbol.agregar(valor);
                panelArbol.repaint();
                actualizarInfo();
                inputDato.setText("");
                mostrarMensaje("✅ Número " + valor + " agregado");
            } catch (NumberFormatException ex) {
                mostrarError("❌ Ingrese un número válido");
            }
        });
        panel.add(btnAgregar);
        panel.add(Box.createVerticalStrut(15));

        // Sección: Búsqueda y Consultas
        panel.add(createLabel("📍 Búsqueda y Consultas:"));

        JButton btnExiste = createButton("🔍 ¿Existe?", new Color(70, 130, 180), e -> {
            try {
                int valor = Integer.parseInt(inputDato.getText());
                boolean existe = arbol.existe(valor);
                String msg = existe ? "✅ Sí existe el " + valor : "❌ No existe el " + valor;
                mostrarMensaje(msg);
            } catch (NumberFormatException ex) {
                mostrarError("❌ Ingrese un número válido");
            }
        });
        panel.add(btnExiste);

        JButton btnNivel = createButton("📏 Obtener Nivel", new Color(70, 130, 180), e -> {
            try {
                int valor = Integer.parseInt(inputDato.getText());
                int nivel = arbol.obtenerNivel(valor);
                if (nivel >= 0) {
                    mostrarMensaje("📌 El nivel de " + valor + " es: " + nivel);
                } else {
                    mostrarMensaje("❌ El número " + valor + " no existe");
                }
            } catch (NumberFormatException ex) {
                mostrarError("❌ Ingrese un número válido");
            }
        });
        panel.add(btnNivel);
        panel.add(Box.createVerticalStrut(15));

        // Sección: Operaciones generales
        panel.add(createLabel("⚙️ Operaciones Generales:"));

        JButton btnMenor = createButton("↙️ Obtener Menor", new Color(176, 134, 198), e -> {
            Integer menor = arbol.obtenerMenor();
            mostrarMensaje(menor != null ? "➡️ Menor: " + menor : "❌ Árbol vacío");
        });
        panel.add(btnMenor);

        JButton btnMayor = createButton("↗️ Obtener Mayor", new Color(176, 134, 198), e -> {
            Integer mayor = arbol.obtenerMayor();
            mostrarMensaje(mayor != null ? "➡️ Mayor: " + mayor : "❌ Árbol vacío");
        });
        panel.add(btnMayor);

        JButton btnPeso = createButton("⚖️ Peso (Nodos)", new Color(176, 134, 198), e -> {
            mostrarMensaje("🔢 Peso (número de nodos): " + arbol.obtenerPeso());
        });
        panel.add(btnPeso);

        JButton btnAltura = createButton("📐 Altura", new Color(176, 134, 198), e -> {
            mostrarMensaje("📏 Altura del árbol: " + arbol.obtenerAltura());
        });
        panel.add(btnAltura);

        JButton btnHojas = createButton("🍃 Contar Hojas", new Color(176, 134, 198), e -> {
            mostrarMensaje("🔢 Número de hojas: " + arbol.contarHojas());
        });
        panel.add(btnHojas);
        panel.add(Box.createVerticalStrut(15));

        // Sección: Recorridos
        panel.add(createLabel("🔄 Recorridos:"));

        JButton btnInorden = createButton("Inorden", new Color(184, 134, 11), e -> {
            List<Integer> resultado = arbol.recorridoInorden();
            mostrarResultado("Inorden (Izq-Raiz-Der): ", resultado);
        });
        panel.add(btnInorden);

        JButton btnPreorden = createButton("Preorden", new Color(184, 134, 11), e -> {
            List<Integer> resultado = arbol.recorridoPreorden();
            mostrarResultado("Preorden (Raiz-Izq-Der): ", resultado);
        });
        panel.add(btnPreorden);

        JButton btnPostorden = createButton("Postorden", new Color(184, 134, 11), e -> {
            List<Integer> resultado = arbol.recorridoPostorden();
            mostrarResultado("Postorden (Izq-Der-Raiz): ", resultado);
        });
        panel.add(btnPostorden);

        JButton btnAmplitud = createButton("Amplitud (Niveles)", new Color(184, 134, 11), e -> {
            List<List<Integer>> resultado = arbol.imprimirAmplitud();
            StringBuilder sb = new StringBuilder("📊 Recorrido por Amplitud:\n");
            for (int i = 0; i < resultado.size(); i++) {
                sb.append("Nivel ").append(i).append(": ").append(resultado.get(i)).append("\n");
            }
            mostrarResultado(sb.toString(), new ArrayList<>());
        });
        panel.add(btnAmplitud);
        panel.add(Box.createVerticalStrut(15));

        // Sección: Eliminar
        panel.add(createLabel("🗑️ Operaciones de Eliminación:"));

        JButton btnEliminar = createButton("❌ Eliminar", new Color(178, 34, 34), e -> {
            try {
                int valor = Integer.parseInt(inputDato.getText());
                arbol.eliminar(valor);
                panelArbol.repaint();
                actualizarInfo();
                mostrarMensaje("✅ Número " + valor + " eliminado");
                inputDato.setText("");
            } catch (NumberFormatException ex) {
                mostrarError("❌ Ingrese un número válido");
            }
        });
        panel.add(btnEliminar);

        JButton btnLimpiar = createButton("🧹 Borrar Árbol", new Color(178, 34, 34), e -> {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de borrar todo el árbol?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                arbol.borrarArbol();
                panelArbol.repaint();
                actualizarInfo();
                mostrarMensaje("✅ Árbol borrado completamente");
            }
        });
        panel.add(btnLimpiar);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel crearPanelDerecha() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new TitledBorder("📋 Información y Resultados"));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(250, 0));

        // Panel de información general
        labelInfo = new JLabel();
        labelInfo.setVerticalAlignment(JLabel.TOP);
        labelInfo.setHorizontalAlignment(JLabel.CENTER);
        labelInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        actualizarInfo();

        JScrollPane scrollInfo = new JScrollPane(labelInfo);
        scrollInfo.setBorder(new TitledBorder("ℹ️ Estado del Árbol"));
        panel.add(scrollInfo, BorderLayout.NORTH);

        // Área de resultados
        textResultados = new JTextArea();
        textResultados.setEditable(false);
        textResultados.setFont(new Font("Courier New", Font.PLAIN, 11));
        textResultados.setLineWrap(true);
        textResultados.setWrapStyleWord(true);
        JScrollPane scrollResultados = new JScrollPane(textResultados);
        scrollResultados.setBorder(new TitledBorder("📝 Resultados"));
        panel.add(scrollResultados, BorderLayout.CENTER);

        return panel;
    }

    private JButton createButton(String text, Color color, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(new RoundBorder(5));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        label.setBorder(new EmptyBorder(5, 0, 5, 0));
        return label;
    }

    private void actualizarInfo() {
        String info = String.format(
                "<html><div style='text-align: center; padding: 10px;'>" +
                        "<b>Estado del Árbol:</b><br><br>" +
                        "Vacío: %s<br>" +
                        "Nodos (Peso): %d<br>" +
                        "Altura: %d<br>" +
                        "Hojas: %d<br>" +
                        "%s" +
                        "</div></html>",
                arbol.estaVacio() ? "✅ Sí" : "❌ No",
                arbol.obtenerPeso(),
                arbol.obtenerAltura(),
                arbol.contarHojas(),
                !arbol.estaVacio() ? String.format(
                        "<br>Menor: %d<br>Mayor: %d<br>",
                        arbol.obtenerMenor(),
                        arbol.obtenerMayor()
                ) : ""
        );
        labelInfo.setText(info);
    }

    private void mostrarResultado(String titulo, List<Integer> resultado) {
        StringBuilder sb = new StringBuilder(titulo);
        if (!resultado.isEmpty()) {
            sb.append("\n").append(resultado);
        }
        sb.append("\n");
        textResultados.setText(sb.toString());
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArbolGUI());
    }

    // Clase interna para el panel del árbol
    private static class PanelArbol extends JPanel {
        private ArbolBinarioBusqueda<Integer> arbol;
        private int offsetY = 30;

        PanelArbol(ArbolBinarioBusqueda<Integer> arbol) {
            this.arbol = arbol;
            setBackground(new Color(255, 255, 240));
            setPreferredSize(new Dimension(1000, 600));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            if (!arbol.estaVacio()) {
                int ancho = getWidth();
                dibujarArbol(g2d, arbol.getRaiz(), ancho / 2, offsetY, ancho / 4);
            } else {
                g2d.setFont(new Font("Arial", Font.PLAIN, 18));
                g2d.setColor(Color.GRAY);
                g2d.drawString("📭 Árbol vacío", getWidth() / 2 - 80, getHeight() / 2);
            }
        }

        private void dibujarArbol(Graphics2D g, Nodo<Integer> nodo, int x, int y,
                int offsetX) {
            if (nodo == null) {
                return;
            }

            int radio = 25;

            // Dibujar conexiones con hijos
            if (nodo.izquierdo != null) {
                int xIzq = x - offsetX;
                int yIzq = y + 70;
                g.setColor(new Color(100, 100, 100));
                g.setStroke(new BasicStroke(2));
                g.drawLine(x, y + radio, xIzq, yIzq - radio);
                dibujarArbol(g, nodo.izquierdo, xIzq, yIzq, offsetX / 2);
            }

            if (nodo.derecho != null) {
                int xDer = x + offsetX;
                int yDer = y + 70;
                g.setColor(new Color(100, 100, 100));
                g.setStroke(new BasicStroke(2));
                g.drawLine(x, y + radio, xDer, yDer - radio);
                dibujarArbol(g, nodo.derecho, xDer, yDer, offsetX / 2);
            }

            // Dibujar nodo
            g.setColor(new Color(100, 200, 255));
            g.fillOval(x - radio, y - radio, radio * 2, radio * 2);
            g.setColor(new Color(0, 0, 0));
            g.setStroke(new BasicStroke(2));
            g.drawOval(x - radio, y - radio, radio * 2, radio * 2);

            // Dibujar texto
            String texto = nodo.toString();
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(texto);
            g.drawString(texto, x - textWidth / 2, y + 6);
        }
    }

    // Clase para bordes redondeados
    private static class RoundBorder extends AbstractBorder {
        private int radius;

        RoundBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width,
                int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(2, 2, 2, 2);
        }
    }
}
