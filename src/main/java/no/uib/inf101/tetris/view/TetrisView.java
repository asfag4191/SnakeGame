package no.uib.inf101.tetris.view;

import javax.swing.JPanel;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class TetrisView extends JPanel {

    public static final int OUTERMARGIN = 15;
    public static final int CELLMARGIN = 2;
    public static final int PREFERREDSIDESIZE = 30;
    

    private ViewableTetrisModel viewableTetrisModel;
    private ColorTheme colorTheme;

    public TetrisView(ViewableTetrisModel viewableTetrisModel) {
        this.viewableTetrisModel = viewableTetrisModel;

        this.colorTheme = new DefaultColorTheme();
        this.setBackground(colorTheme.getBackgroundColor());

        this.setFocusable(true);
        this.setPreferredSize(getDefaultSize(viewableTetrisModel.getDimension()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }

    private void drawGame(Graphics2D g2) {
        Rectangle2D box = new Rectangle2D.Double(
                OUTERMARGIN,
                OUTERMARGIN,
                this.getWidth() - OUTERMARGIN * 2,
                this.getHeight() - OUTERMARGIN * 2);
        g2.setColor(null);
        g2.fill(box);

        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(box,
                viewableTetrisModel.getDimension(), CELLMARGIN);
                
        drawCells(g2, viewableTetrisModel.getTilesOnBoard(), converter, colorTheme);
        drawCells(g2, viewableTetrisModel.getFallingPiece(), converter, colorTheme);
    }

    private static void drawCells(Graphics2D g2, Iterable<GridCell<Character>> iterable,
            CellPositionToPixelConverter converter, ColorTheme colorTheme) {
        for (GridCell<Character> cell : iterable) {
            CellPosition pos = cell.pos();
            Character c = cell.value();
            Rectangle2D tile = converter.getBoundsForCell(pos);

            g2.setColor(colorTheme.getCellColor(c));
            g2.fill(tile);
        }
    }

    private static Dimension getDefaultSize(GridDimension gd) {
        int width = (int) (PREFERREDSIDESIZE * gd.cols() + CELLMARGIN * (gd.cols() + 1) + 2 * OUTERMARGIN);
        int height = (int) (PREFERREDSIDESIZE * gd.rows() + CELLMARGIN * (gd.cols() + 1) + 2 * OUTERMARGIN);
        return new Dimension(width, height);
    }

}