package com.elma.braingames.puzzles.tangram.layout;

import com.elma.braingames.puzzles.tangram.model.TangramBoard;
import com.elma.braingames.puzzles.tangram.model.TangramPiece;


public class TangramLayout {


    private float width;
    private float height;
    private float targetCenterX;
    private float targetCenterY;

    private float targetSize;

    private float pieceSize;

    private float piecesAreaTop;
    private float piecesAreaBottom;

    public void update(
        float width,
        float height
    ) {

        this.width =
            width;

        this.height =
            height;

        targetCenterX =
            width / 2f;

        targetCenterY =
            height * 0.61f;

        targetSize =
            Math.min(
                width * 0.52f,
                height * 0.36f
            );

        pieceSize =
            Math.min(
                width * 0.48f,
                height * 0.305f
            );

        piecesAreaBottom =
            height * 0.08f;

        piecesAreaTop =
            height * 0.30f;
    }

    private void applyTarget(
        TangramPiece piece
    ) {

        float cx =
            targetCenterX;

        float cy =
            targetCenterY;

        float s =
            targetSize;


        switch (
            piece.getId()
        ) {

            case 0:

                piece.setTarget(
                    cx - s * 0.25f,
                    cy + s * 0.165f,
                    225f
                );

                break;

            case 1:

                piece.setTarget(
                    cx - s * 0.04f,
                    cy - s * 0.055f,
                    180f
                );

                break;

            case 2:

                piece.setTarget(
                    cx - s * 0.475f,
                    cy + s * 0.475f,
                    45f
                );

                break;

            case 3:

                piece.setTarget(
                    cx + s * 0.52f,
                    cy + s * 0.33f,
                    315f
                );

                break;

            case 4:

                piece.setTarget(
                    cx + s * 0.18f,
                    cy + s * 0.33f,
                    135f
                );

                break;

            case 5:

                piece.setTarget(
                    cx + s * 0.35f,
                    cy + s * 0.16f,
                    45f
                );

                break;

            case 6:

                piece.setTarget(
                    cx - s * 0.33f,
                    cy + s * 0.688f,
                    135f
                );

                break;
        }
    }

    public void updateTargets(
        TangramBoard board
    ) {

        if (board == null) {
            return;
        }

        for (
            TangramPiece piece :
            board.getPieces()
        ) {

            applyTarget(piece);
        }
    }


    private float getStartX(
        int index
    ) {

        float left =
            width * 0.16f;

        float centerLeft =
            width * 0.34f;

        float center =
            width * 0.50f;

        float centerRight =
            width * 0.66f;

        float right =
            width * 0.84f;


        switch (
            index
        ) {

            case 0:
                return left;

            case 1:
                return centerLeft;

            case 2:
                return centerRight;

            case 3:
                return right;

            case 4:
                return centerLeft;

            case 5:
                return center;

            case 6:
                return centerRight;

            default:
                return center;
        }
    }


    private float getStartY(
        int index
    ) {

        if (index <= 3) {

            return height * 0.22f;
        }

        return height * 0.08f;
    }

    public void applyToBoard(
        TangramBoard board
    ) {

        if (
            board == null
        ) {

            return;
        }


        for (
            TangramPiece piece :
            board.getPieces()
        ) {

            int id =
                piece.getId();

            piece.setPosition(
                getStartX(id),
                getStartY(id)
            );

            piece.setRotation(
                0f
            );

            applyTarget(
                piece
            );

            piece.setPlaced(
                false
            );
        }
    }

    public float getWidth() {

        return width;
    }


    public float getHeight() {

        return height;
    }


    public float getTargetCenterX() {

        return targetCenterX;
    }


    public float getTargetCenterY() {

        return targetCenterY;
    }


    public float getTargetSize() {

        return targetSize;
    }


    public float getPieceSize() {

        return pieceSize;
    }


    public float getPiecesAreaTop() {

        return piecesAreaTop;
    }


    public float getPiecesAreaBottom() {

        return piecesAreaBottom;
    }

    public float getPieceWidth(
        TangramPiece.PieceType type
    ) {

        switch (
            type
        ) {

            case LARGE_TRIANGLE_1:
            case LARGE_TRIANGLE_2:

                return pieceSize * 1.65f;


            case MEDIUM_TRIANGLE:

                return pieceSize * 1.20f;


            case SMALL_TRIANGLE_1:
            case SMALL_TRIANGLE_2:

                return pieceSize * 0.90f;


            case SQUARE:

                return pieceSize * 0.90f;


            case PARALLELOGRAM:

                return pieceSize * 1.12f;


            default:

                return pieceSize;
        }
    }


    public float getPieceHeight(
        TangramPiece.PieceType type
    ) {

        switch (
            type
        ) {

            case LARGE_TRIANGLE_1:
            case LARGE_TRIANGLE_2:

                return pieceSize * 1.35f;


            case MEDIUM_TRIANGLE:

                return pieceSize * 1.10f;


            case SMALL_TRIANGLE_1:
            case SMALL_TRIANGLE_2:

                return pieceSize * 0.82f;


            case SQUARE:

                return pieceSize * 0.90f;


            case PARALLELOGRAM:

                return pieceSize * 0.70f;


            default:

                return pieceSize;
        }
    }

    public float getInitialX(
        int index
    ) {

        return getStartX(
            index
        );
    }

    public float getInitialY(
        int index
    ) {

        return getStartY(
            index
        );
    }
}
