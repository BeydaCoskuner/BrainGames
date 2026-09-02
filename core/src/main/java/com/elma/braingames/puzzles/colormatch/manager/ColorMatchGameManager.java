package com.elma.braingames.puzzles.colormatch.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.elma.braingames.puzzles.colormatch.models.ColorCircle;

public class ColorMatchGameManager {

    private final Array<ColorCircle> circles;

    private final Array<ColorLine> completedLines;

    private Array<Vector2> activeLinePoints;

    private ColorCircle activeStartCircle;

    private Color activeColor;

    private boolean drawing;

    private boolean gameFailed;

    private boolean gameCompleted;

    private int completedConnectionCount;

    private static final int TOTAL_CONNECTIONS = 3;

    private static final float CIRCLE_RADIUS = 85f;

    private static final float INTERSECTION_EPSILON = 1f;


    public ColorMatchGameManager() {

        circles =
            new Array<>();

        completedLines =
            new Array<>();

        activeLinePoints =
            new Array<>();

        activeStartCircle =
            null;

        activeColor =
            Color.WHITE;

        drawing =
            false;

        gameFailed =
            false;

        gameCompleted =
            false;

        completedConnectionCount =
            0;
    }


    public void createCircles(
        float worldWidth,
        float worldHeight
    ) {

        circles.clear();

        completedLines.clear();

        activeLinePoints.clear();

        activeStartCircle = null;

        activeColor = Color.WHITE;

        drawing = false;

        gameFailed = false;

        gameCompleted = false;

        completedConnectionCount = 0;


        float leftX =
            worldWidth * 0.22f;

        float rightX =
            worldWidth * 0.78f;

        float topY =
            worldHeight * 0.70f;

        float middleY =
            worldHeight * 0.50f;

        float bottomY =
            worldHeight * 0.30f;


        circles.add(
            new ColorCircle(
                Color.PURPLE,
                leftX,
                topY,
                CIRCLE_RADIUS,
                true
            )
        );


        circles.add(
            new ColorCircle(
                Color.RED,
                leftX,
                middleY,
                CIRCLE_RADIUS,
                true
            )
        );


        circles.add(
            new ColorCircle(
                Color.GREEN,
                leftX,
                bottomY,
                CIRCLE_RADIUS,
                true
            )
        );


        circles.add(
            new ColorCircle(
                Color.RED,
                rightX,
                topY,
                CIRCLE_RADIUS,
                false
            )
        );


        circles.add(
            new ColorCircle(
                Color.GREEN,
                rightX,
                middleY,
                CIRCLE_RADIUS,
                false
            )
        );


        circles.add(
            new ColorCircle(
                Color.PURPLE,
                rightX,
                bottomY,
                CIRCLE_RADIUS,
                false
            )
        );
    }

    public ColorCircle getCircleAt(
        float x,
        float y
    ) {

        for (
            ColorCircle circle : circles
        ) {

            float dx =
                x - circle.getX();

            float dy =
                y - circle.getY();

            float distanceSquared =
                dx * dx +
                    dy * dy;

            float radius =
                circle.getRadius();


            if (
                distanceSquared
                    <= radius * radius
            ) {

                return circle;
            }
        }


        return null;
    }


    public boolean canStartLine(
        ColorCircle circle
    ) {

        if (circle == null) {

            return false;
        }


        if (gameFailed) {

            return false;
        }


        if (gameCompleted) {

            return false;
        }


        if (drawing) {

            return false;
        }


        if (isCircleAlreadyConnected(
            circle
        )) {

            return false;
        }


        return true;
    }


    public void startLine(
        ColorCircle circle
    ) {

        if (!canStartLine(
            circle
        )) {

            return;
        }


        activeStartCircle =
            circle;


        activeColor =
            circle.getColor();


        activeLinePoints.clear();


        activeLinePoints.add(
            new Vector2(
                circle.getX(),
                circle.getY()
            )
        );


        drawing = true;
    }



    public void setActiveLinePoints(
        Array<Vector2> points
    ) {

        if (!drawing) {

            return;
        }


        if (points == null) {

            return;
        }


        activeLinePoints =
            points;
    }

    public void clearActiveLinePoints() {

        activeLinePoints.clear();
    }

    public void finishLine(
        ColorCircle targetCircle
    ) {

        if (!drawing) {

            return;
        }

        if (activeStartCircle == null) {

            cancelActiveLine();

            return;
        }

        if (targetCircle == null) {

            cancelActiveLine();

            return;
        }

        if (
            targetCircle ==
                activeStartCircle
        ) {

            cancelActiveLine();

            return;
        }

        if (
            !sameColor(
                activeStartCircle.getColor(),
                targetCircle.getColor()
            )
        ) {

            cancelActiveLine();

            return;
        }

        if (isCircleAlreadyConnected(
            targetCircle
        )) {

            cancelActiveLine();

            return;
        }

        activeLinePoints.add(
            new Vector2(
                targetCircle.getX(),
                targetCircle.getY()
            )
        );

        ColorLine newLine =
            new ColorLine(
                activeStartCircle,
                targetCircle,
                activeColor,
                copyPoints(
                    activeLinePoints
                )
            );

        if (doesLineIntersectExistingLines(
            newLine
        )) {

            gameFailed = true;

            cancelActiveLine();

            return;
        }

        completedLines.add(
            newLine
        );


        completedConnectionCount++;

        cancelActiveLine();


        if (
            completedConnectionCount
                >= TOTAL_CONNECTIONS
        ) {

            gameCompleted = true;
        }
    }


    private void cancelActiveLine() {

        drawing = false;

        activeStartCircle = null;

        activeColor = Color.WHITE;

        activeLinePoints.clear();
    }



    private boolean isCircleAlreadyConnected(
        ColorCircle circle
    ) {

        for (
            ColorLine line
            : completedLines
        ) {

            if (
                line.getStartCircle()
                    == circle
            ) {

                return true;
            }


            if (
                line.getEndCircle()
                    == circle
            ) {

                return true;
            }
        }


        return false;
    }


    private boolean sameColor(
        Color color1,
        Color color2
    ) {

        if (
            color1 == null
                || color2 == null
        ) {

            return false;
        }


        return
            Math.abs(
                color1.r - color2.r
            ) < 0.01f

                &&

                Math.abs(
                    color1.g - color2.g
                ) < 0.01f

                &&

                Math.abs(
                    color1.b - color2.b
                ) < 0.01f;
    }



    private boolean doesLineIntersectExistingLines(
        ColorLine newLine
    ) {

        for (
            ColorLine existingLine
            : completedLines
        ) {

            if (
                linesIntersect(
                    newLine.getPoints(),
                    existingLine.getPoints()
                )
            ) {

                return true;
            }
        }


        return false;
    }

    private boolean linesIntersect(
        Array<Vector2> lineA,
        Array<Vector2> lineB
    ) {

        if (
            lineA == null
                || lineB == null
        ) {

            return false;
        }


        if (
            lineA.size < 2
                || lineB.size < 2
        ) {

            return false;
        }

        for (
            int i = 0;
            i < lineA.size - 1;
            i++
        ) {

            Vector2 a1 =
                lineA.get(i);

            Vector2 a2 =
                lineA.get(i + 1);


            for (
                int j = 0;
                j < lineB.size - 1;
                j++
            ) {

                Vector2 b1 =
                    lineB.get(j);

                Vector2 b2 =
                    lineB.get(j + 1);


                if (
                    segmentsIntersect(
                        a1,
                        a2,
                        b1,
                        b2
                    )
                ) {

                    return true;
                }
            }
        }


        return false;
    }


    private boolean segmentsIntersect(
        Vector2 p1,
        Vector2 p2,
        Vector2 q1,
        Vector2 q2
    ) {

        float o1 =
            orientation(
                p1,
                p2,
                q1
            );

        float o2 =
            orientation(
                p1,
                p2,
                q2
            );

        float o3 =
            orientation(
                q1,
                q2,
                p1
            );

        float o4 =
            orientation(
                q1,
                q2,
                p2
            );


        if (
            ((o1 > 0 && o2 < 0)
                || (o1 < 0 && o2 > 0))

                &&

                ((o3 > 0 && o4 < 0)
                    || (o3 < 0 && o4 > 0))
        ) {

            return true;
        }


        if (
            Math.abs(o1)
                < INTERSECTION_EPSILON

                && onSegment(
                p1,
                q1,
                p2
            )
        ) {

            return true;
        }


        if (
            Math.abs(o2)
                < INTERSECTION_EPSILON

                && onSegment(
                p1,
                q2,
                p2
            )
        ) {

            return true;
        }


        if (
            Math.abs(o3)
                < INTERSECTION_EPSILON

                && onSegment(
                q1,
                p1,
                q2
            )
        ) {

            return true;
        }


        if (
            Math.abs(o4)
                < INTERSECTION_EPSILON

                && onSegment(
                q1,
                p2,
                q2
            )
        ) {

            return true;
        }


        return false;
    }



    private float orientation(
        Vector2 a,
        Vector2 b,
        Vector2 c
    ) {

        return
            (b.x - a.x)
                * (c.y - a.y)

                -

                (b.y - a.y)
                    * (c.x - a.x);
    }



    private boolean onSegment(
        Vector2 a,
        Vector2 b,
        Vector2 c
    ) {

        return
            b.x
                <= Math.max(
                a.x,
                c.x
            ) + INTERSECTION_EPSILON

                &&

                b.x
                    >= Math.min(
                    a.x,
                    c.x
                ) - INTERSECTION_EPSILON

                &&

                b.y
                    <= Math.max(
                    a.y,
                    c.y
                ) + INTERSECTION_EPSILON

                &&

                b.y
                    >= Math.min(
                    a.y,
                    c.y
                ) - INTERSECTION_EPSILON;
    }



    private Array<Vector2> copyPoints(
        Array<Vector2> source
    ) {

        Array<Vector2> copy =
            new Array<>();


        for (
            Vector2 point
            : source
        ) {

            copy.add(
                new Vector2(
                    point.x,
                    point.y
                )
            );
        }


        return copy;
    }



    public void resetGame() {

        completedLines.clear();

        activeLinePoints.clear();

        activeStartCircle = null;

        activeColor = Color.WHITE;

        drawing = false;

        gameFailed = false;

        gameCompleted = false;

        completedConnectionCount = 0;
    }

    public boolean isGameFailed() {

        return gameFailed;
    }


    public void clearGameFailed() {

        gameFailed = false;
    }


    public boolean isGameCompleted() {

        return gameCompleted;
    }


    public boolean isDrawing() {

        return drawing;
    }

    public Array<Vector2> getActiveLinePoints() {

        return activeLinePoints;
    }


    public Color getActiveColor() {

        return activeColor;
    }


    public Array<ColorCircle> getCircles() {

        return circles;
    }


    public Array<ColorLine> getCompletedLines() {

        return completedLines;
    }


    public int getCompletedConnectionCount() {

        return completedConnectionCount;
    }


    public static class ColorLine {

        private final ColorCircle startCircle;

        private final ColorCircle endCircle;

        private final Color color;

        private final Array<Vector2> points;


        public ColorLine(
            ColorCircle startCircle,
            ColorCircle endCircle,
            Color color,
            Array<Vector2> points
        ) {

            this.startCircle =
                startCircle;

            this.endCircle =
                endCircle;

            this.color =
                color;

            this.points =
                points;
        }


        public ColorCircle getStartCircle() {

            return startCircle;
        }


        public ColorCircle getEndCircle() {

            return endCircle;
        }


        public Color getColor() {

            return color;
        }


        public Array<Vector2> getPoints() {

            return points;
        }
    }
}
