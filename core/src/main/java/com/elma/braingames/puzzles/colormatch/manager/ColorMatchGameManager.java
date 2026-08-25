package com.elma.braingames.puzzles.colormatch.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.elma.braingames.puzzles.colormatch.models.ColorCircle;

public class ColorMatchGameManager {

    /*
     * Oyundaki bütün daireler
     */
    private final Array<ColorCircle> circles;


    /*
     * Tamamlanmış çizgiler
     */
    private final Array<ColorLine> completedLines;


    /*
     * Aktif olarak çizilen yol
     */
    private Array<Vector2> activeLinePoints;


    /*
     * Aktif çizginin başladığı daire
     */
    private ColorCircle activeStartCircle;


    /*
     * Aktif çizginin rengi
     */
    private Color activeColor;


    /*
     * Kullanıcı şu anda çiziyor mu?
     */
    private boolean drawing;


    /*
     * Oyun başarısız oldu mu?
     */
    private boolean gameFailed;


    /*
     * Oyun tamamlandı mı?
     */
    private boolean gameCompleted;


    /*
     * Kaç bağlantı tamamlandı?
     */
    private int completedConnectionCount;


    /*
     * Toplam bağlantı sayısı
     */
    private static final int TOTAL_CONNECTIONS = 3;


    /*
     * Daire yarıçapı
     */
    private static final float CIRCLE_RADIUS = 85f;


    /*
     * Çizginin kesişme toleransı
     */
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


    /*
     * =========================================================
     * DAİRELERİ OLUŞTUR
     * =========================================================
     */

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


        /*
         * SOL TARAF
         */
        float leftX =
            worldWidth * 0.22f;


        /*
         * SAĞ TARAF
         */
        float rightX =
            worldWidth * 0.78f;


        /*
         * DİKEY KONUMlar
         */
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


    /*
     * =========================================================
     * ÇİZGİ BAŞLATILABİLİR Mİ?
     * =========================================================
     */

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


        /*
         * Bu dairenin bağlantısı daha önce
         * tamamlanmışsa tekrar başlanamaz.
         */
        if (isCircleAlreadyConnected(
            circle
        )) {

            return false;
        }


        return true;
    }


    /*
     * =========================================================
     * ÇİZGİYİ BAŞLAT
     * =========================================================
     */

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


    /*
     * =========================================================
     * AKTİF ÇİZGİ NOKTALARINI AYARLA
     * =========================================================
     */

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


    /*
     * =========================================================
     * AKTİF ÇİZGİ NOKTALARINI TEMİZLE
     * =========================================================
     */

    public void clearActiveLinePoints() {

        activeLinePoints.clear();
    }


    /*
     * =========================================================
     * AKTİF ÇİZGİYİ TAMAMLA
     * =========================================================
     */

    public void finishLine(
        ColorCircle targetCircle
    ) {

        if (!drawing) {

            return;
        }


        /*
         * Başlangıç dairesi yoksa iptal
         */
        if (activeStartCircle == null) {

            cancelActiveLine();

            return;
        }


        /*
         * Hedef daire yoksa iptal
         */
        if (targetCircle == null) {

            cancelActiveLine();

            return;
        }


        /*
         * Aynı daireye geri dönüldüyse
         * bağlantı geçersiz.
         */
        if (
            targetCircle ==
                activeStartCircle
        ) {

            cancelActiveLine();

            return;
        }


        /*
         * Hedef dairenin rengi
         * başlangıç rengiyle aynı olmalı.
         */
        if (
            !sameColor(
                activeStartCircle.getColor(),
                targetCircle.getColor()
            )
        ) {

            cancelActiveLine();

            return;
        }


        /*
         * Hedef daire daha önce
         * kullanılmışsa bağlantı geçersiz.
         */
        if (isCircleAlreadyConnected(
            targetCircle
        )) {

            cancelActiveLine();

            return;
        }


        /*
         * Yolun son noktasını hedef dairenin
         * merkezine ekliyoruz.
         */
        activeLinePoints.add(
            new Vector2(
                targetCircle.getX(),
                targetCircle.getY()
            )
        );


        /*
         * Yeni çizgiyi oluştur.
         */
        ColorLine newLine =
            new ColorLine(
                activeStartCircle,
                targetCircle,
                activeColor,
                copyPoints(
                    activeLinePoints
                )
            );


        /*
         * Yeni çizginin mevcut çizgilerle
         * kesişip kesişmediğini kontrol et.
         */
        if (doesLineIntersectExistingLines(
            newLine
        )) {

            gameFailed = true;

            cancelActiveLine();

            return;
        }


        /*
         * Çizgiyi kaydet.
         */
        completedLines.add(
            newLine
        );


        completedConnectionCount++;


        /*
         * Aktif çizimi temizle.
         */
        cancelActiveLine();


        /*
         * Üç bağlantı tamamlandıysa
         * oyun bitmiştir.
         */
        if (
            completedConnectionCount
                >= TOTAL_CONNECTIONS
        ) {

            gameCompleted = true;
        }
    }


    /*
     * =========================================================
     * GEÇİCİ ÇİZGİYİ İPTAL ET
     * =========================================================
     */

    private void cancelActiveLine() {

        drawing = false;

        activeStartCircle = null;

        activeColor = Color.WHITE;

        activeLinePoints.clear();
    }


    /*
     * =========================================================
     * DAİRE DAHA ÖNCE BAĞLANDI MI?
     * =========================================================
     */

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


    /*
     * =========================================================
     * RENK KARŞILAŞTIR
     * =========================================================
     */

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


    /*
     * =========================================================
     * YENİ ÇİZGİ MEVCUT ÇİZGİLERLE KESİŞİYOR MU?
     * =========================================================
     */

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


    /*
     * =========================================================
     * İKİ YOLUN KESİŞİM KONTROLÜ
     * =========================================================
     */

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


        /*
         * Birinci çizginin bütün parçaları
         */
        for (
            int i = 0;
            i < lineA.size - 1;
            i++
        ) {

            Vector2 a1 =
                lineA.get(i);

            Vector2 a2 =
                lineA.get(i + 1);


            /*
             * İkinci çizginin bütün parçaları
             */
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


    /*
     * =========================================================
     * İKİ DOĞRU PARÇASI KESİŞİYOR MU?
     * =========================================================
     */

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


        /*
         * Normal kesişme
         */
        if (
            ((o1 > 0 && o2 < 0)
                || (o1 < 0 && o2 > 0))

                &&

                ((o3 > 0 && o4 < 0)
                    || (o3 < 0 && o4 > 0))
        ) {

            return true;
        }


        /*
         * Aynı doğru üzerinde özel durumlar
         */
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


    /*
     * =========================================================
     * ORIENTATION
     * =========================================================
     */

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


    /*
     * =========================================================
     * NOKTA DOĞRU PARÇASI ÜZERİNDE Mİ?
     * =========================================================
     */

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


    /*
     * =========================================================
     * NOKTALARI KOPYALA
     * =========================================================
     */

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


    /*
     * =========================================================
     * RESET
     * =========================================================
     */

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


    /*
     * =========================================================
     * GAME FAILED
     * =========================================================
     */

    public boolean isGameFailed() {

        return gameFailed;
    }


    public void clearGameFailed() {

        gameFailed = false;
    }


    /*
     * =========================================================
     * GAME COMPLETED
     * =========================================================
     */

    public boolean isGameCompleted() {

        return gameCompleted;
    }


    /*
     * =========================================================
     * DRAWING
     * =========================================================
     */

    public boolean isDrawing() {

        return drawing;
    }


    /*
     * =========================================================
     * ACTIVE LINE POINTS
     * =========================================================
     */

    public Array<Vector2> getActiveLinePoints() {

        return activeLinePoints;
    }


    /*
     * =========================================================
     * ACTIVE COLOR
     * =========================================================
     */

    public Color getActiveColor() {

        return activeColor;
    }


    /*
     * =========================================================
     * CIRCLES
     * =========================================================
     */

    public Array<ColorCircle> getCircles() {

        return circles;
    }


    /*
     * =========================================================
     * COMPLETED LINES
     * =========================================================
     */

    public Array<ColorLine> getCompletedLines() {

        return completedLines;
    }


    /*
     * =========================================================
     * COMPLETED CONNECTION COUNT
     * =========================================================
     */

    public int getCompletedConnectionCount() {

        return completedConnectionCount;
    }


    /*
     * =========================================================
     * COLOR LINE
     * =========================================================
     */

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
